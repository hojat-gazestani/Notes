# Convolutional Neural Networks (CNNs)

![CNN](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_vuja0HL-oTjAZVPW.webp)


Use CNN to process structured grid data (e.g images) to capture patterns (edge, texture, shape) in the data.

*Key Components*
+ Convolutional layer
+ padding
+ stride
+ max pooling

## Input Layer
3D volume (width × height × depth).
Stores pixel values of the image (e.g., 32 × 32 × 3 for RGB images).

## Convolutional Layer

Extract features (edge, texture, shape) from the input data,
Learnable filters (kernel) slides over image and compute the dot product between the filter weights and corresponding image patches, producing feature maps.

+ Uses small filters - kernel (e.g., 2×2, 3×3, 5×5) to scan the input image.

![kernel](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_9y3bJ3EXof_KKTsj.gif)



![Convolutional](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/1_SkVkik3WAPJ2aqFJZ_el4w.webp)

+ Input
+ Kernel:
+ Output:


### Padding

![padding](https://github.com/hojat-gazestani/Notes/blob/main/machine-learning/pic/02-CNN-theory/0_lLW_v6lWDC5C_txJ.gif)

#### Types of Padding

### Understanding Stride

#### How does Stride Affect the Output?

## Activation Layer




## Pooling Layer


## Flattening

## Dense (Fully Connected) Layer

## Softmax Activation Function Layer (Output Layer)













Sources
[medium Nandi](https://medium.com/@sushmita2310/understanding-convolutional-neural-networks-cnns-for-beginners-e85ad21fe432)
[geeksforgeeks](https://www.geeksforgeeks.org/machine-learning/introduction-convolution-neural-network/)
