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

#### 2.2 Activation
#### 2.3 Normalization Layer 
##### 2.3.1 Batch Normalization
##### 2.3.2 Layer Normalization
#### 2.4 Pooling Layer
Help consolidate extracted feature from the images.
Progressively reduce the spatial dimensions of the representation or output feature map, thereby minimizing the number of parameters and computations required in the network.

1[pooling]()
#### 2.5 Dropout Layer 

## 3. Flattening

## 4. Dense (Fully Connected) Layer

## 5. Output Layer
### 5.1 Softmax 
### 5.2 Sigmoid

## Sources
[medium Nandi](https://medium.com/@sushmita2310/understanding-convolutional-neural-networks-cnns-for-beginners-e85ad21fe432)
[geeksforgeeks](https://www.geeksforgeeks.org/machine-learning/introduction-convolution-neural-network/)
