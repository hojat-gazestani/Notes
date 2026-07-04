<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [**LangChain Expression Language (LCEL)** pipeline](#langchain-expression-language-lcel-pipeline)
  - [Creating a chain object.](#creating-a-chain-object)
    - [Step1: Creating a dictionary for the prompt.](#step1-creating-a-dictionary-for-the-prompt)
    - [Step 2: prompt template](#step-2-prompt-template)
    - [Step 3: LLM Model](#step-3-llm-model)
    - [Step 4: Converts the model output into plain text.](#step-4-converts-the-model-output-into-plain-text)
    - [Final Step](#final-step)
  - [Equivalent Plain Python](#equivalent-plain-python)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# **LangChain Expression Language (LCEL)** pipeline

```python
def generate_response(retriever, query):
    """Generate a response to a user query."""

    chain = (
        {"context": retriever, "question": RunnablePassthrough()}
        | chat_prompt_template
        | model
        | StrOutputParser()
    )
    return chain.invoke(query)
```

RAG pipeline High-Level Flow

```text
User Query
    ↓
Retriever
    ↓
Prompt Template
    ↓
LLM
    ↓
Output Parser
    ↓
Answer
```

## Creating a chain object.

```python
chain = (
```

Think of it like:
```python
pipeline = step1 | step2 | step3 | step4
```

```text
chain = prompt (dictionary) | prompt template | LLM Model | Converts the model output into plain text.
```

### Step1: Creating a dictionary for the prompt.

```python
{
    "context": retriever,
    "question": RunnablePassthrough()
}
```

When:

```python
chain.invoke("What is a Kubernetes Pod?")
```

runs, LangChain builds:

```python
{
    "context": ???,
    "question": "What is a Kubernetes Pod?"
}
```

**What does Retriever do?**

Suppose your vector database contains:

```text
Chunk 1:
A Pod is the smallest deployable unit in Kubernetes.

Chunk 2:
Pods may contain multiple containers.
```

The retriever receives:

```text
"What is a Kubernetes Pod?"
```

and returns:

```text
A Pod is the smallest deployable unit in Kubernetes.
Pods may contain multiple containers.
```

So:

```python
"context": retriever
```

means:

```text
Query
 ↓
Retriever
 ↓
Relevant Documents
 ↓
context
```

**What is RunnablePassthrough()?**

`"question": RunnablePassthrough()`

Simply forwards the original input unchanged.

Input:

`"What is a Kubernetes Pod?"`

Output:

`"What is a Kubernetes Pod?"`

So now LangChain creates:

```python
{
    "context": retrieved_documents,
    "question": "What is a Kubernetes Pod?"
}
```
### Step 2: prompt template

```python
| chat_prompt_template
```

Suppose your prompt template looks like:

```python
chat_prompt_template = ChatPromptTemplate.from_template("""
Answer using the provided context.

Context:
{context}

Question:
{question}
""")
```

LangChain fills the placeholders.

Result:

```text
Answer using the provided context.

Context:
A Pod is the smallest deployable unit in Kubernetes.
Pods may contain multiple containers.

Question:
What is a Kubernetes Pod?
```

### Step 3: LLM Model

```python
| model
```

This is the LLM.

For example:
```python
ChatOpenAI()
```

**What does the model return?**

Usually not a plain string.

Something like:

```python
AIMessage(
    content="A Pod is the smallest deployable unit in Kubernetes..."
)
```

### Step 4: Converts the model output into plain text.

```python
| StrOutputParser()
```

Input:

```python
AIMessage(
    content="A Pod is the smallest deployable unit..."
)
```

Output:

```text
"A Pod is the smallest deployable unit..."
```

### Final Step

```python
return chain.invoke(query)
```

If:

```python
query = "What is a Kubernetes Pod?"
```

then internally:

```text
Query
 ↓
Retriever
 ↓
Relevant Chunks
 ↓
Prompt Template
 ↓
LLM
 ↓
Text Output
```

and returns:

```text
A Pod is the smallest deployable unit in Kubernetes. It can contain one or more containers and is scheduled onto a node.
```

---

## Equivalent Plain Python

The same pipeline without LangChain abstractions would look roughly like:

> LangChain is mostly a collection of abstractions around code you could write yourself.

```python
def generate_response(retriever, query):

    context = retriever.invoke(query)

    prompt = f"""
    Answer using the provided context.

    Context:
    {context}

    Question:
    {query}
    """

    response = model.invoke(prompt)

    return response.content
```

The benefit is that once workflows become more complex (multiple retrievers, tools, branching logic, memory, agents), the LCEL pipeline syntax stays relatively clean.


```text
RunnablePassthrough
    = keep original input

Retriever
    = query → relevant documents

PromptTemplate
    = documents + question → prompt

Model
    = prompt → AIMessage

StrOutputParser
    = AIMessage → string
```
