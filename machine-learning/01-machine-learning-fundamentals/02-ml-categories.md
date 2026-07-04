<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Machine Learning Categories](#machine-learning-categories)
  - [1. Supervised Learning](#1-supervised-learning)
    - [Key Idea](#key-idea)
    - [Example](#example)
    - [Common Algorithms](#common-algorithms)
  - [2. Unsupervised Learning](#2-unsupervised-learning)
    - [Key Idea](#key-idea-1)
    - [Example](#example-1)
    - [Common Algorithm](#common-algorithm)
  - [3. Reinforcement Learning](#3-reinforcement-learning)
    - [Key Idea](#key-idea-2)
    - [Examples](#examples)
    - [Common Algorithm](#common-algorithm-1)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Machine Learning Categories

* Supervised Learning
* Unsupervised Learning
* Reinforcement Learning

---

## 1. Supervised Learning

learning patterns through connecting the relationship between variables and known outcomes and working with labeled datasets

or learning the relationship between input variables (X) and known outputs (y).

The dataset is **labeled**, meaning the correct answers are provided during training. (deciphers patterns)

![super](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-supervisor-learning.png)

### Key Idea

The model learns patterns from labeled data and applies them to new, unseen data.

### Example

Predicting the market price of a used car based on features like:

* Mileage
* Age
* Brand

### Common Algorithms

* Regression analysis
* Decision trees
* K-nearest neighbors (KNN)
* Neural networks
* Support vector machines (SVM)

---

## 2. Unsupervised Learning

In unsupervised learning, data is **not labeled**. The model must discover hidden patterns and structures on its own.

![similar](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-supervisor-learning1.png)

### Key Idea

The algorithm identifies similarities and groups data accordingly.

### Example

Analyzing customer behavior to discover different types of customers.

For example:

* Small and medium-sized businesses (SMEs)
* Large enterprises

### Common Algorithm

**K-means clustering**

* Groups data points based on similarity
* Helps reveal patterns that were previously unknown

---

## 3. Reinforcement Learning

Reinforcement learning is based on **learning through interaction and feedback**.

The model improves by receiving rewards or penalties based on its actions.

### Key Idea

Instead of labeled data, the system learns from **trial and error**.

### Examples

* Video games
* Self-driving cars (avoiding crashes = reward)
* Chess-playing agents

### Common Algorithm

**Q-learning**

* Learns optimal actions within an environment
* Uses states, actions, and rewards


```text
Computer Science
├── Artificial Intelligence
├── Data Science
├── Software Engineering
├── Networks
└── Databases

Computer Science
├── Artificial Intelligence
│   ├── Machine Learning
│   │   └── Deep Learning
│   │       ├── CNNs
│   │       └── Transformers
│   ├── NLP
│   ├── Computer Vision
│   └── Robotics
└── Data Science

AI
└── Machine Learning
    └── Deep Learning
        ├── CNNs  → images
        ├── Transformers → text/LLMs
        └── Diffusion → image generation
       
# More details
Computer Science
└── Artificial Intelligence
        ├── Machine Learning (ML)
        │   ├── Deep Learning
        │   │   ├── CNN (Convolutional Neural Networks)
        │   │   │   ├── Image Classification
        │   │   │   ├── Object Detection
        │   │   │   ├── Medical Imaging
        │   │   │   ├── Facial Recognition
        │   │   │   └── Image Segmentation
        │   │   │
        │   │   ├── RNNs / LSTMs
        │   │   ├── Transformers (test/LLM usage via ChatOpenAI)
        │   │   │       └── LLM Applications
        │   │   │            ├── RAG (Retrieval-Augmented Generation)  ← YOUR PROJECT
        │   │   │            │   ├── Document Loader (TextLoader)
        │   │   │            │   ├── Chunking (RecursiveCharacterTextSplitter)
        │   │   │            │   ├── Embeddings (HuggingFaceEmbeddings)
        │   │   │            │   ├── Vector DB (Chroma)
        │   │   │            │   └── Retriever + Prompt + LLM Chain (LangChain)
        │   │   └── Diffusion Models → image generation
        │   │
        │   ├── Supervised Learning
        │   ├── Unsupervised Learning
        │   └── Reinforcement Learning
        │
        ├── Natural Language Processing (NLP)
        │   ├── Text Classification
        │   ├── Translation
        │   ├── Summarization
        │   ├── Question Answering
        │   └── Information Retrieval
        │
        ├── Computer Vision
        │   ├── Image Classification
        │   ├── Object Detection
        │   ├── Segmentation
        │   └── Facial Recognition
        │
        └── Robotics

Large Language Models (LLMs)
└── Built using:
  ├── Deep Learning
  ├── Transformers
  └── NLP

RAG (Retrieval-Augmented Generation)
└── An application architecture/pattern on top of LLMs


```


```text

NLP
└── Transformers
    └── LLMs
        └── RAG applications
```

