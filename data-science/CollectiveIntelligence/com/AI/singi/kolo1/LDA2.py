import numpy as np
import matplotlib.pyplot as plt
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
#Generisanje podataka
# KLASA_1

mean = [0,0]

cov = [ [1,-.5], [-.5, 1] ]
       
x1, y1 = np.random.multivariate_normal(mean, cov, 500).T


x1_test , y1_test = np.random.multivariate_normal(mean, cov, 100).T
print x1_test.shape
#Generisanje podataka
# KLASA_2

mean = [1.5, 1.5]
cov = [ [1,-.8], [-.8,1] ]

x2, y2 = np.random.multivariate_normal(mean, cov, 500).T
x2_test, y2_test = np.random.multivariate_normal(mean,cov,100).T

#plt.scatter(x1,y1)
#plt.scatter(x2,y2,c=u'r')
#plt.scatter(x1_test, y1_test, c=u'g')
#plt.scatter(x2_test, y2_test,c=u'y')
#plt.show()

X1 = np.hstack([x1,x2])
Y2 = np.hstack([y1,y2])
XX = np.vstack([X1,Y2])

XX = XX.T

X1_test = np.hstack([x1_test, x2_test]);
Y2_test = np.hstack([y1_test, y2_test]);
XX_test = np.vstack([X1_test, Y2_test]);
#.T transponuje matricu. Svaki prvi element vektora ce se mapirati sa svakim prvim elementom sledeceg vektora u matrici
#i od tog rezultat ce se praviti novi vektor
# primer: [ [1,2,3], [4,5,6] ] ovo je mat dimen 2x3 kada se transponuje dobice se nova mat [ [1,4], [2,5] , [3,6] ] dimen 3x2
XX_test = XX_test.T


y = np.ones(1000)
y[500:] = 2
clf = LinearDiscriminantAnalysis()

clf.fit(XX, y)
y_predicted = clf.predict(XX_test)
#plt.stem(y_predicted)
#plt.show()


x = np.arange(-5,5,.4)
y = np.arange(-5,5,.4)

for xi in x:
    for yi in y:
        #vector
        temp = np.array([xi, yi])
        #transform it to 1x2 mat
        temp = temp.reshape(1,-1)
        temp = clf.predict(temp)
        if (temp==1):
            break
            plt.scatter(xi, yi, c=u'k')
        else:
            plt.scatter(xi,yi,c=u'r')



           # from 0 to 500(exclusive)
plt.scatter(XX[:500,0], XX[:500,1], c=u'b')
            #from 500(inclusice to end, n-1)
plt.scatter(XX[500:,0], XX[500:,1], c=u'r')
#plt.show()
w = clf.coef_[0]
a = -w[0] / w[1]

xx = np.linspace(-5,5)
yy = a * xx - (clf.intercept_[0]) /w[1]
plt.plot(xx, yy, 'k-')
plt.show()



