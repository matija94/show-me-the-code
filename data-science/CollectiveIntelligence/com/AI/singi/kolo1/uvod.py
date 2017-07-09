import numpy as np
import matplotlib.pyplot as plt

a = np.array([1,2,3])
b = np.array([4,5,6])

a = np.vstack([a,b])

print a
#svaki Element se mnozi sa dva
print(a.T)

#ispisuje duzinu niza, posto je niz ima samo jednu dimenziju tj. predstavljen je kao vektor
#ndim od a ce biti 1
print(a.ndim)
print a.shape

#od vektora mozemo da napravimo matricu sledecom funckijom
print(a.reshape(3,2))

#mozemo napraviti matricu i dati joj sve pocetne vrednosti 0
b = np.zeros((3,4))
print (b)

x = np.arange(10)
originalSquared = [(sqrtx,np.sqrt(sqrtx)) for sqrtx in x]
print originalSquared

t = np.arange(1000, dtype=float)
t = t/1000

#x = np.cos(2*np.pi*3*t)
#plt.plot(t,x)
#plt.show()

plt.scatter([1,2], [-1,-2],c=u'r')
plt.show()

a = np.array([[1,2]])
print a.shape
a = np.array([1,2]).reshape(1,-1)
print a.shape

a = np.array([ [1,2,3], [4,5,6] ])
print a
print a.T             



a = np.array([1,2,3])
b = np.array([4,5,6])
c = np.array([7,8,9])

abc = np.vstack([a,b,c])
print abc[0:,0]

