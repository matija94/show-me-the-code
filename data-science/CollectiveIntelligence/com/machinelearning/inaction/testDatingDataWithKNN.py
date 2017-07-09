import knn as knn



def datingClassTest():
    hoRatio = 0.10
    datingDataMat, datingLabels = knn.file2matix('/home/matija/Projects/personal_projects/show-me-the-code/data-science/CollectiveIntelligence/dataSets/datingTestSet1.txt')
    normMat, ranges, minVals = knn.autoNorm(datingDataMat)
    m = normMat.shape[0]
    #vectors to test knn clf
    numTestVecs = int(m*hoRatio)
    errorCount = 0.0
    for i in range(numTestVecs):
        classifierResult = knn.classify0(normMat[i,:], normMat[numTestVecs:m,:]\
                                         ,datingLabels, 3)
        print "the classifier came back with: %d, the real answer is: %d"\
                    %(classifierResult, datingLabels[i])
                    
        if (classifierResult != datingLabels[i]) : errorCount+=1.0
    
    print "the total error rate is: %f" %(errorCount/float(numTestVecs))
        
 
 
datingClassTest()   