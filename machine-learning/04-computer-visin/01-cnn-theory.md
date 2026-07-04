<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Convolutional Neural Networks (CNNs)](#convolutional-neural-networks-cnns)
  - [CNN Processing Pipeline](#cnn-processing-pipeline)
  - [1. Input Layer](#1-input-layer)
  - [2. Convolutional Block](#2-convolutional-block)
    - [2.1 Convolution operation](#21-convolution-operation)
      - [2.1.1 Filters / kernels](#211-filters--kernels)
      - [2.1.2 Stride](#212-stride)
      - [2.1.3 Padding](#213-padding)
      - [2.2 Activation](#22-activation)
      - [2.3 Normalization Layer](#23-normalization-layer)
      - [2.4 Pooling Layer](#24-pooling-layer)
        - [Types of Pooling](#types-of-pooling)
      - [2.5 Dropout Layer](#25-dropout-layer)
  - [3. Flattening](#3-flattening)
  - [4. Dense (Fully Connected) Layer](#4-dense-fully-connected-layer)
  - [5. Output Layer](#5-output-layer)
  - [Sources](#sources)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Convolutional Neural Networks (CNNs)

![CNN](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/01-header.webp)

## CNN Processing Pipeline

Use CNN to process structured grid data (e.g images) to capture patterns (edge, texture, shape) in the data.

```text
CNN
├── Input
├── Feature Extraction
│   ├── Convolution
│   │   ├── Filter / Kernel
│   │   ├── Stride
│   │   └── padding
│   ├── Activation / ReLU
│   ├── Normalization
│   │   ├─ Batch
│   │   └─ Layer
│   ├── Pooling
│   │   ├─ Max
│   │   └─ Average
│   └── Dropout
├── Flatten
├── Fully Connected Layers
├── Dense
└── Output
    ├─ Softmax
    └─ Sigmoid
```

1. **Input layer**
    + Image enters the network
    + Example: `128 × 128 × 3`
2. **Convolutional Block(s)**
    Each block contain
    + **Convolution Operation**
        + Filters / kernels
        + **Stride**
        + **Padding**
    + **Activation**
        + Usually **ReLU**
    + **Normalization Layer** (optional but common)
        + Batch Normalization
        + Layer Normalization
    + **Pooling**
        + Max Pooling / Average Pooling
    + **Dropout Layer** (optional, training only)
3. **Flatten layer**
    Converts 2D feature maps into a 1D vector
6. **Dense (Fully Connected) Layer(s)**
    Learns high-level patterns for classification
7. **Output Layer**
    + Usually:
        + **Softmax:** multi-class classification
        + **Sigmoid:** binary classification


## 1. Input Layer
3D volume (width × height × depth).
Stores pixel values of the image (e.g., 32 × 32 × 3 for RGB images).

Original image

![origianl](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/10-original-image.png)

## 2. Convolutional Block
### 2.1 Convolution operation
Extract features (edge, texture, shape) from the input data,
Learnable filters (kernel) slides over image and compute the dot product between the filter weights and corresponding image patches, producing feature maps.

+ Uses small filters - kernel (e.g., 2×2, 3×3, 5×5) to scan the input image.

The below gif shows how convolution Kernel slides over the images.

![kernel](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/03-kernel-slide.gif)

#### 2.1.1 Filters / kernels

Kernel is a small filter (3x 3, 5x 5 matrix) in deep network (like VGG, ResNet) because they preserve spatial locality and require fewer parameter.

The 3x 3, 5x 5 balance computation and feature extraction.

![Convolutional](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/02-kernel.webp)

+ Input: Your image, big grid of numbers (6x 6)
+ Kernel: Green convolution kernel, the smaller grid (3x 3)
+ Output: Result after convolution, the smaller grid (4x 4)

While kernel are sliding over the input image, for each position:

1. Multiply: Each number in the kernel is multiply by the number it's covering in the input image.
2. Sum Up: Add all these multiplied values together.
3. Store result: This sum becomes a single number in the output matrix.

**Example**
Above 3×3 filter applied to a 5×5 input image. The convolution operation is enhanced by applying transformations.

```python
import numpy as np
from scipy.signal import convolve2d

# Example 5x5 image
image = np.array([[1, 1, 1, 0, 1],
                  [0, 1, 0, 1, 1],
                  [1, 0, 1, 1, 0],
                  [1, 1, 0, 0, 1],
                  [0, 1, 0, 1, 0]])
# 3x3 kernel
kernel = np.array([[1, 0, 1],
                   [1, 0, 1],
                   [0, 1, 0]])
# Applying convolution
feature_map = convolve2d(image, kernel, mode='valid')
print(feature_map)
```

#### 2.1.2 Stride
The numbers of pixels/blocks the kernel moves(slides) each time during convolution
![stride](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/05-stride.webp)

How does Stride Affect the Output?
+ **Stride = 1** (Default): Moves one pixel at a time → Larger output.
+ **Stride > 1:** Moves multiple pixels at a time → Reduces the output size, leading to downsampling. The below images show how stride = 2 works.

![stide_affect](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/06-stride-affect.gif)

The illustration below shows how 3 different kernels were applied to 3 different RGB channels of a colored image with stride 1 and how the output is calculated.

![diff](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/07-stride-deff-rgb.gif)

#### 2.1.3 Padding
Adding extra pixel (typiclly zeros) around the input image to preserve spatial dimensions after applying convolution.

+ Learn from entire dataset without bias towards the center of the images.
+ Useful in retaining edge futures by ensuring the convolutional kernel proccesses the outermost pixel of the image, preventing the loss of broder information during convolution.

![padding](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/04-padding.gif)

Why is Padding needed?
+ Preserve important edge features
+ Control the output size
+ Prevent information loss


image after convoltion

![convolution](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/11-after-convolution.png)

---

#### 2.2 Activation

Introduce non-linearing to the model, enabling it to learn complex patterns.

**ReLU**

Applies simple transaction: **f(x)=max(0,x)**. This means negetive values are set to zero.

image after ReLU

![ReLU](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/12-after-ReLU.png)

---

#### 2.3 Normalization Layer 

Scaling the data in such a way that it mean become Zero and standard deviation become 1

Improve train speed and staiblity

![normalization](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/18-normalization.png)

- Batch Normalization
- Layer Normalization

---

#### 2.4 Pooling Layer

Help consolidate extracted feature from the images.

Progressively reduce the spatial dimensions of the representation or output feature map, thereby minimizing the number of parameters and computations required in the network.

![pooling](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/14-pooling.webp)

> Introduce translational invariance, allowing the CNN to recognize features even if the input is shifted.

> Translation invariance means small input shifts do not strongly affect pooled outputs.

##### Types of Pooling

1. **Max pooling:** Select maximum value from each pooling region

```text
[1 3]
[2 9]  →  9
```

2. **Average pooling:** Computes the average value within each pooling region.

```text
[1 3]
[2 9]  →  3.75
```

![diff-pooling](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/08-diff-pooling.gif)

---

#### 2.5 Dropout Layer 

To prevent overfitting, it randomly deactivates a portion of neurons druing training,

> Forces the network to learn more generalized patterns rather than memorizing specific detail from training data, improving its ability to handle new, unseen inputs.

![dropout](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/17-droupout.png)

---

## 3. Flattening

Transforms a multi-dimensional feature map into a 1D vector.

It preserves important features as the flattened layer does not modify values.

![flatten](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/15-flattend.png)

---

## 4. Dense (Fully Connected) Layer

Proccess the pooled layers and  key extracted features from the image, to general final predictions.

Applies a weighted sum followed by an activation function to produce the output.

![dense](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/16-dense.webp)

---

## 5. Output Layer

Convert final scores into probabilities using the Softmax or Sigmoid activation function.

- Softmax: It converts the raw output values (logits) from the dense layer into probabilities, ensuring they are sum to 1. This helps in determining the likelihood of each class, making it suitable for classification tasks.
- Sigmoid

## Sources
[medium Nandi](https://medium.com/@sushmita2310/understanding-convolutional-neural-networks-cnns-for-beginners-e85ad21fe432)
[geeksforgeeks](https://www.geeksforgeeks.org/machine-learning/introduction-convolution-neural-network/)
