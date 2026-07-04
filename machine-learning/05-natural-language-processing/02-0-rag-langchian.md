<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Retrieval-Augmented Generation (RAG)](#retrieval-augmented-generation-rag)
  - [LangChain](#langchain)
    - [Step 1: PDF](#step-1-pdf)
    - [Step 2: Chunking](#step-2-chunking)
    - [Step 3: Embedding](#step-3-embedding)
    - [Step 4: Vector Database](#step-4-vector-database)
    - [Step 5: User Question](#step-5-user-question)
    - [Step 6: Similarity Search](#step-6-similarity-search)
    - [Step 7: Retrieval](#step-7-retrieval)
    - [Step 8: Build the Prompt](#step-8-build-the-prompt)
    - [Step 9: LLM Generates Answer](#step-9-llm-generates-answer)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Retrieval-Augmented Generation (RAG)

## LangChain

```text
Offline (Indexing)

PDF, TXT
 ↓
Text Extraction
 ↓
Chunking
 ↓
Embedding
 ↓
Vector DB
 ↓
Retrieval
 ↓
LLM
 ↓
Answer DB


Online (Question Answering)

Question
 ↓
Embedding
 ↓
Vector Search
 ↓
Relevant Chunks
 ↓
Prompt
 ↓
LLM
 ↓
Answer
```

### Step 1: PDF
`Kubernetes Handbook.pdf`
```text
A Pod is the smallest deployable unit in Kubernetes.

Pods can contain one or more containers.

Pods are scheduled onto Nodes.
```

```python
text = extract_text(pdf)
```

### Step 2: Chunking

LLMs and embedding models work better with smaller pieces.

Instead of storing:

```text
100-page document
```

we split it.

```text
Chunk 1:
A Pod is the smallest deployable unit in Kubernetes.

Chunk 2:
Pods can contain one or more containers.

Chunk 3:
Pods are scheduled onto Nodes.
```

Typical chunk sizes:

| Size        | Usage             |
| ----------- | ----------------- |
| 200 tokens  | Precise retrieval |
| 500 tokens  | Common            |
| 1000 tokens | Large context     |

### Step 3: Embedding

Now we convert each chunk into numbers.

`"A Pod is the smallest deployable unit"`

becomes:

`[0.12, -0.93, 0.44, 1.22, ...]`

These numbers are called an embedding vector.

**What is an embedding?**

An embedding is a mathematical representation of meaning.

`Dog` ->   `[0.4, 0.8, -0.1, ...]`

`Puppy` -> `[0.39, 0.79, -0.08, ...]` 

**Visualization**

```text
            Animal

              Cat
               ●

 Dog ●

 Puppy ●
```

Similar concepts cluster together.

This comes from transformer models learning semantic relationships.

### Step 4: Vector Database

Now we store:

```text
Chunk
+
Embedding
```

| Chunk                | Vector      |
| -------------------- | ----------- |
| Pod definition       | [0.12, ...] |
| Container definition | [0.45, ...] |
| Node definition      | [0.77, ...] |


### Step 5: User Question

User asks:

`What is a Pod?`

We do NOT search the PDF directly.

Instead:

```text
Question
 ↓
Embedding Model
 ↓
Vector
```

Question becomes:

`[0.11, -0.91, 0.41, ...]`

### Step 6: Similarity Search

The vector database compares:

`Question Vector`

against all stored vectors.

Mathematically it often uses:

* Cosine Similarity
* Euclidean Distance
* Dot Product

Most commonly:

`Cosine Similarity`

**Conceptual Example**

Question:

`What is a Pod?`

Vector:

`[0.11, ...]`

Database:

```text
Pod definition      similarity = 0.95
Container definition similarity = 0.72
Node definition      similarity = 0.55
```

Top result:

`Pod definition`

### Step 7: Retrieval

The system retrieves:

`A Pod is the smallest deployable unit in Kubernetes.`

Maybe the top 5 chunks.

Example:

```text
Chunk 1
Chunk 2
Chunk 3
Chunk 4
Chunk 5
```

### Step 8: Build the Prompt

Now we create a prompt.

```text
Context:

A Pod is the smallest deployable unit in Kubernetes.

Pods can contain one or more containers.

Question:

What is a Pod?
```

### Step 9: LLM Generates Answer

Now the LLM receives:

```text
Question
+
Relevant Context
```

instead of searching its memory.

Result:

```text
A Pod is the smallest deployable unit in Kubernetes.
It can contain one or more containers and is scheduled onto a node.
```


