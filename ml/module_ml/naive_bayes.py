from sklearn.naive_bayes import GaussianNB
import numpy as np

x = np.array([
    [1, 2],
    [3, 4],
    [8, 7],
    [9, 10],
    [11, 13]
])

y = np.array([1, 2, 1, 2, 1])

model = GaussianNB()

model.fit(x, y)

predicted = model.predict([
    [1, 2],
    [1, 12]
])

print(predicted)
