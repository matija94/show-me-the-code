'''

hypothesis
    O^T * x = O0X0 + O1X1 + ... + OmXm
    h(O) = O^T * x

cost function
    cost(h(x), y) = (h(x) - y)^2
    J(O) = (1/2*m) * sum(cost(h(x), y))

fit parameters with gradient descent
    Oj = Oj - learning_rate * partial_derivative(J(O))
    partial_derivative = 1/m * sum((h(xi) - yi) * xij)

'''


class LinearRegression:
    def __init__(self, learning_rate=0.001, iters=10000, fit_intercept=True, theta=None):
        self.learning_rate = learning_rate
        self.iters = iters
        self.fit_intercept = fit_intercept
        self.theta = theta

    def __add_intercept(self, X):
        intercept = np.ones((X.shape[0], 1))
        return np.concatenate((intercept, X), axis=1)

    def __cost(self, h, y):
        return np.sum((h - y) * (h - y)) / (2*y.size)

    def fit(self, X, y):
        if self.fit_intercept:
            X = self.__add_intercept(X)

        if self.theta is None:
            self.theta = np.zeros(X.shape[1])

        for i in range(self.iters):
            h = np.dot(X, self.theta)
            cost = self.__cost(h, y)
            for j in range(len(self.theta)):
                gradient = np.dot(h-y, X[:, j])
                self.theta[j] = self.theta[j] - (self.learning_rate / y.size * gradient)
        print(cost)


if __name__ == '__main__':
    import numpy as np
    hours_studied = np.array([[2], [4], [6], [8], [10], [13], [13.5], [14], [16], [19], [25]])
    grades = np.array([1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5])

    print("GRADIENT DESCENT")
    model = LinearRegression()
    model.fit(hours_studied, grades)

    '''
    gradients, cost = linear_regression(hours_studied, grades)
    print(gradients)
    print(cost)

    predict = lambda x: gradients[1]*x + gradients[0]
    print(predict(21))

    '''

    '''
    NORMAL EQUATION METHOD
    theta = (X^T * X)^-1 * X^T * y
    '''

    print("NORMAL EQ")
    hours_studied_ones = np.concatenate((np.ones((len(hours_studied), 1)), hours_studied), axis=1)
    theta = np.dot(np.dot(np.linalg.inv(np.dot(np.transpose(hours_studied_ones), hours_studied_ones)), np.transpose(hours_studied_ones)), grades)
    model = LinearRegression(theta=theta, iters=1)
    model.fit(hours_studied, grades)

'''
SHORT SELLING
ABC share 54$

borrow 5 abc shares and sell them for 270$

ABC share 45$

buy 5 abc shares for 225$ and give them back to creditor

profit = 270$ - 45$ = 25$
'''