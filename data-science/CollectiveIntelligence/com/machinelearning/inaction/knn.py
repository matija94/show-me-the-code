'''
Created on Apr 4, 2017
kNN: k Nearest Neighbors

Input:      inX: vector to compare to existing dataset (1xN)
            dataSet: size m data set of known vectors (NxM)
            labels: data set labels (1xM vector)
            k: number of neighbors to use for comparison (should be an odd number)
            
Output:     the most popular class label

@author: matija
k nearest neighbors
'''
import numpy as np
import operator
import matplotlib.pyplot as plt

def createDataSet():
    '''
    for mocking purposes
    '''
    group = np.array([ [1.0,1.1], [1.0,1.0], [0,0], [0,0.1] ])
    labels = ['A', 'A', 'B', 'B']
    return group,labels


def file2matix(filename):
    fr = open(filename)
    numberOfLines = len(fr.readlines())
    returnMat = np.zeros((numberOfLines,3))
    classLabelVector = []
    fr = open(filename)
    index = 0
    for line in fr.readlines():
        line = line.strip()
        listFromLine = line.split('\t')
        returnMat[index,:] = listFromLine[0:3]
        classLabelVector.append(int(listFromLine[-1]))
        index+=1
    return returnMat,classLabelVector
    

def autoNorm(dataSet):
    '''
    if the data has values that lie in diff ranges autoNorm will normalize the data
    so each feature is treated 'equally' ( from 0 to 1)
       
    uses function below to normalize the values between 0 and 1
                              ranges
    newVal = (oldVal-minVal)/(max-min)
    '''
    #min vals from each col in mat
    minVals = dataSet.min(0)
    #max vals from each col in mat
    maxVals = dataSet.max(0)
    ranges = maxVals - minVals
    normDataSet = np.zeros(np.shape(dataSet))
    m = dataSet.shape[0]
    normDataSet = dataSet - np.tile(minVals, (m,1))
    normDataSet = normDataSet/np.tile(ranges, (m,1))
    return normDataSet, ranges, minVals

def classify0(inX, dataSet, labels, k):
    #value of rows in dataSet
    dataSetSize = dataSet.shape[0]
    #make new mat with same dim as dataSet and values from inX
    # and subtract it from dataSet mat
    diffMat = np.tile(inX, (dataSetSize,1)) - dataSet
    #square mat
    sqDiffMat = diffMat**2
    #sum mat vectors into vector, using axis 1(means sum elems from same rows), axis 0
    # would sum elements from same columns
    sqDistances = sqDiffMat.sum(axis=1)
    #square root every element in vector sqDistances
    distances = sqDistances**0.5 #eq to np.sqrt(sqDistances)
    
    ### CODE ABOVE WAS USING EUCLIDEAN DISTANCE FORMULA
    
    #sort distance indicies in increasing manner, resulting set will be indicies of
    #sorted set
    sortedDistIndicies = distances.argsort()
    classCount={}
    for i in range(k):
        voteIlabel = labels[sortedDistIndicies[i]]
        classCount[voteIlabel] = classCount.get(voteIlabel,0) + 1
    #compute list of tuples(label,classCount) in reversed order=>largest to smallest
    sortedClassCount = sorted(classCount.iteritems(),
                              key=operator.itemgetter(1), reverse=True)
    
    return sortedClassCount[0][0]

