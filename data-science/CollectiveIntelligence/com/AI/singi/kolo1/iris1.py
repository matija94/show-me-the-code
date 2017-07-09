import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn import datasets

iris = datasets.load_iris()

X = iris.data
y =iris.target

kmeans = KMeans(n_clusters=3).fit(X)

output = kmeans.labels_

plt.scatter(X[y==0,0], X[y==0,1])
plt.scatter(X[y==1,0], X[y==1,1])
plt.scatter(X[y==2,0], X[y==2,1])
plt.show()


plt.scatter(X[output==0,0], X[output==0,1])
plt.scatter(X[output==1,0], X[output==1,1], c=u'k')
plt.scatter(X[output==2,0], X[output==2,1], c=u'g')
