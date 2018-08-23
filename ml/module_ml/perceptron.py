import numpy as np


class pcn:
    """ A basic Perceptron"""

    def __init__(self, inputs, targets):
        """ Constructor """
        # Set up network size
        if np.ndim(inputs) > 1:
            self.nIn = np.shape(inputs)[1]
        else:
            self.nIn = 1

        if np.ndim(targets) > 1:
            self.nOut = np.shape(targets)[1]
        else:
            self.nOut = 1

        self.nData = np.shape(inputs)[0]

        # Initialise network
        self.weights = np.random.rand(self.nIn + 1, self.nOut) * 0.1 - 0.05

    def pcntrain(self, inputs, targets, eta, nIterations):
        """ Train the thing """
        # Add the inputs that match the bias node
        inputs = np.concatenate((inputs, -np.ones((self.nData, 1))), axis=1)

        print("Iteration 0\nWeights: ", self.weights)

        for n in range(nIterations):
            self.activations = self.pcnfwd(inputs)
            print("Outputs: ", self.activations)
            self.weights -= eta * np.dot(np.transpose(inputs), self.activations - targets)
            if n < (nIterations-1):
                print("Iteration {}\nWeights: ".format(str(n+1)), self.weights)

    def pcnfwd(self, inputs):
        """ Run the network forward """
        # Compute activations
        activations = np.dot(inputs, self.weights)

        # Threshold the activations
        return np.where(activations > 0, 1, 0)

    def confmat(self, inputs, targets):
        """Confusion matrix"""

        # Add the inputs that match the bias node
        inputs = np.concatenate((inputs, -np.ones((self.nData, 1))), axis=1)

        outputs = np.dot(inputs, self.weights)

        nClasses = np.shape(targets)[1]

        if nClasses == 1:
            nClasses = 2
            outputs = np.where(outputs > 0, 1, 0)
        else:
            # 1-of-N encoding
            outputs = np.argmax(outputs, 1)
            targets = np.argmax(targets, 1)

        cm = np.zeros((nClasses, nClasses))
        for i in range(nClasses):
            for j in range(nClasses):
                cm[i, j] = np.sum(np.where(outputs == i, 1, 0) * np.where(targets == j, 1, 0))

        print(cm)
        print(np.trace(cm) / np.sum(cm))


def logic():
    """ Run AND and XOR logic functions"""

    # XOR
    inputs = np.array([
        [0, 0],
        [0, 1],
        [1, 0],
        [1, 1]
    ])
    targets = np.array([
        [0],
        [1],
        [1],
        [1]
    ])

    p = pcn(inputs, targets)
    p.pcntrain(inputs, targets, 0.25, 5)
    p.confmat(inputs, targets)


if __name__ == '__main__':
    logic()
    a = np.array([
        [1, 2],
        [3, 4],
        [5, 6],
        [7, 8]
    ])  # 4x2

    b = np.array([
        [1, 2, 3, 4],
        [5, 6, 7, 8]
    ])  # 2x4

    ab = np.dot(a, b)  # 4x4
    print(ab)

    c = np.array([
        [3, 4],
        [2, 16]
    ])

    id = np.array([
        [1, 0],
        [0, 1]
    ])

    c_inv = np.linalg.inv(c)
    print(c_inv)
    
    assert np.dot(c_inv, c).all() == id.all()
