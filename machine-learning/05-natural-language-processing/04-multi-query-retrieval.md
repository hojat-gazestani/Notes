<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Multi-Query Retrieval](#multi-query-retrieval)
  - [Step 1. Create the LLM](#step-1-create-the-llm)
  - [Step 2. Create the prompt](#step-2-create-the-prompt)
  - [Step 3. Download the document](#step-3-download-the-document)
  - [Step 4. Split the document](#step-4-split-the-document)
  - [Step 6. Build the vector database](#step-6-build-the-vector-database)
  - [Step 7. Create a retriever](#step-7-create-a-retriever)
  - [Step 8. Build the Multi-query chain](#step-8-build-the-multi-query-chain)
  - [Step 9. User asks a question](#step-9-user-asks-a-question)
  - [Step 10. Parse](#step-10-parse)
  - [Step 11. Retriever](#step-11-retriever)
  - [Step 12. Remove duplicates](#step-12-remove-duplicates)
  - [Step 13. Generation](#step-13-generation)
  - [Key Takeaways](#key-takeaways)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Multi-Query Retrieval

Multi-Query Retrieval is a retrieval strategy that improves document retrieval by asking an LLM to generate several alternative versions of the user's question.

Instead of searching the vector database once, we search it multiple times using different phrasings of the same question. The retrieved documents are merged, duplicates are removed, and the final set of documents is used to generate the answer.

Flow

```text
User Question
    │
    ▼
Generate Multiple Queries
    │
    ▼
Retrieve Documents for Each Query
    │
    ▼
Remove Duplicates
    │
    ▼
Generate Final Answer
```

It has two main phases:

* Indexing Phase (offline)
* Query Phase (online)

```text
Indexing Phase (offline)

1. Load documents
2. Split documents
3. Create embeddings
4. Build Chroma
5. Create retriever

-------------------------

Query Phase (online)

6. User asks a question
7. Generate multiple queries
8. Retrieve documents
9. Remove duplicates
10. Generate answer
```

---


## Step 1. Create the LLM


```python
llm = ChatOpenAI(model="AliBaba/Qwen3.6-27B")
```

the `llm` is an object what knows how to call the API

---

## Step 2. Create the prompt

```python
template = """
You are an AI language model assistant...
Original question: {question}
"""

multi_query_prompt = ChatPromptTemplate.from_template(template)
```

Here is the template that will use the `question` to create a prompt 

```python
question = "How do I build a RAG system?"
```

Prompt will become 

```python
You are an AI language model assistant.

Generate five different versions...

Original question:

How do I build a RAG system?"
```

---

## Step 3. Download the document

```python
loader = WebBaseLoader(...)
blog_docs = loader.load()
```

Here we will have the Wikipedia page content 

```text
Wikipedia
      │
      ▼
HTML
      │
      ▼
Document
```

The `blog_docs` is:
**Notice:** We currently have only one `document`

```python
[
    Document(
        page_content="....very long text....",
        metadata={...}
    )
]
```
---

## Step 4. Split the document

```python
splits = text_splitter.split_documents(blog_docs)
```

Suppose the Wikipedia article is

```text
9000 characters
```

Your splitter says

```text
chunk_size = 300
chunk_overlap = 50
```

So it becomes something like

```text
Document 1
Document 2
Document 3
...
Document 35
```

Now instead of

```text
1 large document
```

you have

```text
35 small documents
```

This is important because embedding models work much better on small chunks.

Step 5. Embed every chunk

```python
embeddings = OpenAIEmbeddings(...)
```

No API call yet, Only an object.

---

## Step 6. Build the vector database


```python
vectorstore = Chroma.from_documents(
    documents=splits,
    embedding=embeddings,
)
```

This is the expensive step.
For every chunk

```text
Chunk 1
Chunk 2
Chunk 3
...
```

LangChain does

```text
embedding(chunk)
```

which sends requests to

```text
http://llm.local:3100/v1/embeddings
```

The server returns vectors like

```text
Chunk 1

↓

[0.13, -0.91, 0.04, ...]
```

Those vectors are stored in Chroma.

```text
Text
↓

Embedding

↓

Vector DB
```

---

## Step 7. Create a retriever

```python
retriever = vectorstore.as_retriever()
```

Here is the retriver object

---

## Step 8. Build the Multi-query chain

```python
generate_multi_queries()
```

It returns


```python
multi_query_prompt
|
llm
|
StrOutputParser()
|
(lambda x: x.split("\n"))
```

This is called an LCEL chain.

like a Unix pipe

```text
Prompt

↓

LLM

↓

Parser

↓

Split into list
```

Nothing executes yet.

---

## Step 9. User asks a question

User asks

```text
"How do I build a RAG system?"
```

The document might instead contain

```text



The prompt becomes

```text
Generate five versions of

"How do I build a RAG system?"
```

The LLM answers

```text
What is retrieval augmented generation?

How does retrieval augmented generation work?

Explain the RAG architecture.

Describe retrieval augmented generation.

What are the components of RAG?
```

---

## Step 10. Parse

```python
StrOutputParser()
```

takes

```python
AIMessage(...)
```

and returns

```text
string
```

Then

```python
lambda x: x.split("\n")
```

turns it into

```python
[
 "What is retrieval augmented generation?",
 "Explain RAG...",
 ...
]
```

---

## Step 11. Retriever

is the interesting part.

```python
retriever.map()
```

Without `.map()`

```text
Retriever

↓

4 documents
```

With `.map()`

every generated query is searched.

```text
Query 1

↓

Retriever

↓

4 docs


Query 2

↓

Retriever

↓

4 docs


Query 3

↓

Retriever

↓

4 docs
```

If you generated

```text
5 queries
```

you may retrieve

```text
20 documents
```

---

## Step 12. Remove duplicates

Different queries often retrieve the same document because they are semantically similar.

Therefore, we merge the retrieved result and remove duplicates before parsing the documents to the LLM.

```python
get_unique_union()
```

receives

```python
[
 [doc1 doc2 doc3 doc4],
 [doc3 doc8 doc1 doc5],
 [doc2 doc9 doc10 doc4],
 ...
]
```

Then

```python
set(...)
```

removes duplicates. Maybe you end up with

```text
14 unique docs
```

---

## Step 13. Generation

The final step:

```text
User Question

↓

Retrieve Docs

↓

Prompt

↓

LLM

↓

Answer
```

The prompt will look like

```text
Context

<document 1>

<document 2>

...

Question

What is RAG?
```

Then the LLM generates the final answer.


Code's diagram

```texgt
Wikipedia
      │
      ▼
WebBaseLoader
      │
      ▼
Document
      │
      ▼
RecursiveCharacterTextSplitter
      │
      ▼
Chunks
      │
      ▼
OpenAIEmbeddings
      │
      ▼
Chroma
      │
      ▼
Retriever
      │
      ▼
──────────────────────────────────────

Question

      │
      ▼
ChatPromptTemplate
      │
      ▼
LLM
      │
      ▼
5 Alternative Questions
      │
      ▼
retriever.map()
      │
      ▼
20 Retrieved Documents
      │
      ▼
get_unique_union()
      │
      ▼
14 Unique Documents
      │
      ▼
RAG Prompt
      │
      ▼
LLM
      │
      ▼
Answer
```

## Key Takeaways

- Multi-Query Retrieval improves recall by searching with multiple versions of the user's question.
- The document collection is indexed only once.
- Each user question generates several alternative queries.
- Each query performs an independent similarity search.
- Retrieved documents are merged and duplicates removed.
- The final document set is sent to the LLM to generate the answer.
