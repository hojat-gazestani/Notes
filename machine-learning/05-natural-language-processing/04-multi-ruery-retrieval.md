# Multi-Query Retrieval

---

Step 1. Create the LLM


```python
llm = ChatOpenAI(model="AliBaba/Qwen3.6-27B")
```

the `llm` is an object what konws how to call the API

---

Step 2. Create the prompt

```python
template = """
You are an AI language model assistant...
Original question: {question}
"""

multi_query_prompt = ChatPromptTemplate.from_template(template)
```

Here is the template that will use the `question` to create a prompt 

```python
question = "What is RAG?"
```

Prompt will become 

```python
You are an AI language model assistant.

Generate five different versions...

Original question:

What is RAG?
```

---

Step 3. Download the document

```python
loader = WebBaseLoader(...)
blog_docs = loader.load()
```

Here we will have the wkipedia page content 

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
**Notice:** We an only on `document`

```python
[
    Document(
        page_content="....very long text....",
        metadata={...}
    )
]
```
---

Step 4. Split the document

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

Step 6. Build the vector database


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

Step 7. Create a retriever

```python
retriever = vectorstore.as_retriever()
```

Here is the retriver object

---

Step 8. Build the Multi-query chain

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

Step 9. User asks a question

```text
What is RAG?
```

The prompt becomes

```text
Generate five versions of

"What is RAG?"
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

Step 10. Parse

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

Step 11. Retriever

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

Step 12. Remove duplicates

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

Step 13. Generation

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


Overall Flow

```texgt
Wikipedia
     │
     ▼
Load
     │
     ▼
Split
     │
     ▼
Embeddings
     │
     ▼
Chroma
     │
     ▼
Retriever
     │
     ▼
Generate 5 Queries
     │
     ▼
Search each query
     │
     ▼
Unique documents
     │
     ▼
Prompt
     │
     ▼
LLM
     │
     ▼
Final Answer
```


