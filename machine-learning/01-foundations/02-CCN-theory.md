# Convolutional Neural Networks (CNNs)

![CNN](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_vuja0HL-oTjAZVPW.webp)


Use CNN to process structured grid data (e.g images) to capture patterns (edge, texture, shape) in the data.

**Steps**
1. Input Layer
2. Convolutional Layer
3. Activation Layer
4. Pooling Layer
5. Flattening
6. Fully Connected Layer
7. Output Layer


**Convolution Operation**
+ Padding
+ Stride
+ Pooling
+ Activation Function (ReLU)
+ Normalization Layer (Batch Normalization or Layer Normalization)
+ Dropout Layer (Optional, During Training Only)
+ Flatten Layer
+ Dense (Fully Connected) Layer
+ Softmax Activation Function Layer


## 1. Input Layer
3D volume (width × height × depth).
Stores pixel values of the image (e.g., 32 × 32 × 3 for RGB images).

## 2. Convolutional Layer

Extract features (edge, texture, shape) from the input data,
Learnable filters (kernel) slides over image and compute the dot product between the filter weights and corresponding image patches, producing feature maps.

+ Uses small filters - kernel (e.g., 2×2, 3×3, 5×5) to scan the input image.

The below gif shows how convolution Kernel slides over the images.

![kernel](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_9y3bJ3EXof_KKTsj.gif)

Kernel is a small filter (3x 3, 5x 5 matrix) in deep network (like VGG, ResNet) because they preserve spatial locality and require fewer parameter.

The 3x 3, 5x 5 balance computation and feature extraction.

![Convolutional](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/1_SkVkik3WAPJ2aqFJZ_el4w.webp)

+ Input: Your image, big grid of numbers (6x 6)
+ Kernel: Green convolution kernel, the smaller grid (3x 3)
+ Output: Result after convolution, the smaller grid (4x 4)


### Padding

![padding](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_lLW_v6lWDC5C_txJ.gif)

#### Types of Padding

### Understanding Stride

#### How does Stride Affect the Output?

## 3. Activation Layer




## 4. Pooling Layer


## 5. Flattening

## 6. Dense (Fully Connected) Layer

## 7. Softmax Activation Function Layer (Output Layer)













Sources
[medium Nandi](https://medium.com/@sushmita2310/understanding-convolutional-neural-networks-cnns-for-beginners-e85ad21fe432)
[geeksforgeeks](https://www.geeksforgeeks.org/machine-learning/introduction-convolution-neural-network/)
