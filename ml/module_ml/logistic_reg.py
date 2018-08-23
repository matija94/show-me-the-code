'''

hypothesis
    h(O) = 1/(1+e^(-O^Tx))

cost function
    cost(h(x), y) = -log(h(x)) if y == 1 this function will penalize learning algorithm by big margin if h(x) == 0
    cost(h(x), y) = -log(1-h(x)) if y == 0 this function will penalize learning algorithm by big margin if h(x) == 1

    'more compact way for cost writing cost function for both cases'
    cost(h(x), y) = -y*log(h(x)) - (1-y)*log(1-h(x))
    J(O) = (1/2*m) * sum(cost(h(x), y))

fit parameters with gradient descent
    Oj = Oj - learning_rate * partial_derivative(J(O))
    partial_derivative = 1/m * sum((h(xi) - yi) * xij)

'''

import numpy as np


class LogisticRegression:
    def __init__(self, learning_rate=0.001, iters=10000, fit_intercept=True):
        self.learning_rate = learning_rate
        self.iters = iters
        self.fit_intercept = fit_intercept

    def __add_intercept(self, X):
        intercept = np.ones((X.shape[0], 1))
        return np.concatenate((intercept, X), axis=1)

    def __sigmoid(self, z):
        """
        sigmoid function
        :param z: function parameter z
        :return:
        """
        return 1 / (1 + np.exp(-z))

    def __cost(self, h, y):
        """
        Cost function
        :param h: hypothesis value
        :param y: actual value
        :return:
        """
        return (-y * np.log(h) - (1 - y) * np.log(1 - h))/(2*y.size)

    def fit(self, X, y):
        if self.fit_intercept:
            X = self.__add_intercept(X)

        self.theta = np.zeros(X.shape[1])

        for i in range(self.iters):
            z = np.dot(X, self.theta)
            h = self.__sigmoid(z)
            for j in range(len(self.theta)):
                gradient = np.dot(h-y, X[:, j])
                self.theta[j] = self.theta[j] - (self.learning_rate/y.size) * gradient

        print(self.__cost(h, y))

    def predict_prob(self, X):
        if self.fit_intercept:
            X = self.__add_intercept(X)

        return self.__sigmoid(np.dot(X, self.theta))

    def predict(self, X, threshold=0.5):
        return self.predict_prob(X) >= threshold


if __name__ == '__main__':
    from sklearn import datasets

    iris = datasets.load_iris()
    X = iris.data[:, :2]
    y = (iris.target != 0) * 1

    model = LogisticRegression()
    model.fit(X, y)
