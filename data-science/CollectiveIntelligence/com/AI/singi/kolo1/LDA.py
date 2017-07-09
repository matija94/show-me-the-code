import numpy as np
import matplotlib.pyplot as plt
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis

#training set with two features
x = np.array([[-1,-1], [-2,-1], [-3,-2], [1,1], [2,1], [3,2]])

#vektor y predstavlja elemete klase matrice x
#prva tri vektora matrice x oznacena su sa 1 sto znaci da imaju negativne vrednosti
#poslednja tri vektora mat x oznacena su sa 2 sto znaci da imaju pozitivne vrednosti

#Labels
y = np.array([1,1,1,2,2,2])
#plt.scatter(x[:,0], x[:,1])
#plt.show()

clf = LinearDiscriminantAnalysis()

clf.fit(x,y)

x1_test = np.array([-2,-3])

#pretvara vektor u matricu 1x2 
x1_test = x1_test.reshape(1,-1)

#pogadja kojoj klasi pripada dati podatak(u nasem slucaju  matrica sa jednim vektorom duzine 2)
print clf.predict(x1_test)

x2_test = np.array([[3,3]])
print clf.predict(x2_test)

x3_test = np.array([[-2,-2],[-2,-2]])
x3_test_predicted=clf.predict(x3_test)
#plt.stem(x3_test_predicted)
#plt.show()

#plot training set
plt.scatter(x[:,0], x[:,1])

#plot test set
plt.scatter(x1_test[:,0], x1_test[:,1], c='red')
plt.scatter(x2_test[:,0], x2_test[:,1], c='green')
plt.scatter(x3_test[:,0], x3_test[:,1], c='black')
plt.show()