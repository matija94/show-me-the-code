from sklearn.cluster import KMeans
import numpy as np
import matplotlib.pyplot as plt


X = np.array([ [1,2], [1,4], [1,0], [4,2], [4,4], [4,0] ], dtype=float)

x = KMeans(n_clusters=2).fit(X)

print x.cluster_centers_
print x.labels_==1

print X[x.labels_==1,0]
print X[x.labels_==1,1]
plt.scatter(X[x.labels_==1,0], X[x.labels_==1,1])
plt.scatter(X[x.labels_==0,0], X[x.labels_==0,1], c=u'r')
plt.show()

#2ndExample
X = np.zeros([200,2])

X[:100,0]  = np.random.randint(1,2,100) + np.random.random(100)*2
X[100:,0]  = np.random.randint(1,2,100) - np.random.random(100)*6

X[:,1] = np.random.randint(1,2,200) + np.random.random(200)

plt.scatter(X[:,0], X[:,1])
plt.show()

x = KMeans(n_clusters=2).fit(X)
plt.scatter(X[x.labels_==1,0], X[x.labels_==1,1])
plt.scatter(X[x.labels_==0,0], X[x.labels_==0,1], c=u'r')
plt.show()

#3rd example
#let's make 4 clusters out of randomly generated X data set 

x = KMeans(n_clusters=4).fit(X)

plt.scatter(X[x.labels_==1,0], X[x.labels_==1,1])
plt.scatter(X[x.labels_==0,0], X[x.labels_==0,1], c=u'r')
plt.scatter(X[x.labels_==2,0], X[x.labels_==2,1], c=u'y')
plt.scatter(X[x.labels_==3,0], X[x.labels_==3,1], c=u'g')
plt.show()

