import numpy as np

randomA = np.random.rand(4,4)
randomAMat = np.mat(randomA)

print randomA

print randomAMat

x = np.array([[1,2], [3,4]])

xMat = np.mat(x)

print xMat
invXMat = xMat.I
print invXMat

print xMat*invXMat


x = np.mat([[1,2], [3,4]])
b = np.mat([[3,4], [5,6]])
xbMat= x*b
print xbMat
print xbMat.T


x = np.asarray(x)
b = np.asarray(b)

xbArr= x*b
print xbArr
print xbArr.T