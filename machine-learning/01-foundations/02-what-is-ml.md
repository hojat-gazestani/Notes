# What is Machine Learning?

## Related Fields

![categ](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/01-categories.png)

### Computer Science

encompasses everything related to the design, development, and use of computers.

### Data Science

methods and systems to extract knowledge and insights from data using computational methods, statistics, and algorithms.

### Artificial Intelligence (AI)
ability of machines to perform intelligent and cognitive tasks.
AI is driving the development of machines capable of simulating cognitive abilities.

Artificial intelligence is the ability of machines to perform tasks that typically require human intelligence.

---

#### AI Subfields

Artificial intelligence includes several important subfields:

* Search and planning
* Reasoning and knowledge representation
* Perception
* Natural Language Processing (NLP)
* Machine Learning

---

## Machine Learning

Subset of AI that enables systems to learn from data and improve performance without being explicitly programmed.

Instead of hardcoding rules, machine learning models identify patterns in data and use them to make predictions or decisions.

![categ1](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/01-categories1.png)




## Deep Learning
+ Subset of ML which make the computation of multi-layer neural network feasible.
+ ML techniques that teaches computers to process data in way that is inspired by the human brain.

![AI](https://github.com/hojat-gazestani/Notes/blob/MachineLearning/machine-learning/pic/01-Artificial-intelligence.png)

####  ML Subfields
+ Deep Learning (subset of ML using neural networks with many layers)
  + CNN (Convolutional Neural Network, a specific architecture of deep learning)
    + Image classification – “Is this a cat or a dog?”
    + Object detection – “Find all cars in this photo”
    + Medical imaging – “Detect pneumonia in X-rays”
    + Facial recognition
    + Image segmentation – “Outline every pedestrian”
  + Large Language Models (LLMs) like GPT, Llama, Claude
    + RAG (an architecture pattern that enhances LLMs)
    + Natural Language Processing (NLP) – core subfield for text understanding/generation

#### Retrieval-Augmented Generation
Retrieval – searching for relevant information from an external knowledge source (documents, databases, websites)

Generation – using an LLM to produce an answer grounded in the retrieved information

##### Why RAG matters

| Problem without RAG | Solution with RAG |
|---------------------|-------------------|
| LLM's knowledge is frozen at training time | Can access live or private data |
| LLM "hallucinates" fake facts | Answers are grounded in retrieved documents |
| Can't cite sources | Can show where the answer came from |
| No access to your internal company docs | Can query your own knowledge base |

##### Simple example
Without RAG – You ask an LLM: "What did my team discuss in yesterday's meeting?"
→ LLM has no idea (wasn't trained on your private meeting notes)

With RAG – Same question:

System retrieves relevant meeting notes from your company's database

LLM generates an answer based only on those retrieved notes

Answer is accurate and can cite the specific document

```text

User Question: "What is the return policy?"
         │
         ▼
   [Retrieval Step]
         │
         ├──→ Search vector database / documents
         ├──→ Find top-k most relevant passages
         │
         ▼
   [Augmentation Step]
         │
         └──→ Combine: Question + Retrieved Passages → Prompt
         │
         ▼
   [Generation Step]
         │
         └──→ LLM answers based ONLY on those passages
         │
         ▼
   "Our return policy allows 30 days..." (citing source doc)
```

##### Common use cases

**Chat with your PDFs/Documents:** customer support, legal, HR

**Enterprise Q&A:** answer questions using internal wikis, Slack, emails

**Medical assistants:** retrieve from latest research papers

**Coding assistants:** retrieve from your private codebase

#### LangChain

Its core philosophy is built around chains—sequences of operations that can be linked together. You can think of a chain as a specific workflow, such as "retrieve relevant documents, then pass them to an LLM to generate an answer" (which is exactly what RAG does) .

Beyond RAG, LangChain allows you to build agents. While a chain follows a fixed sequence, an agent uses the LLM to decide what actions to take next (e.g., "Should I search a database, perform a calculation, or just answer directly?") .

One of LangChain's biggest strengths is its massive ecosystem of integrations . It provides standard interfaces for hundreds of different components, allowing you to easily swap them in and out of your application.
