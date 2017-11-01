import prio_queue

def heapsort(L):
    '''
    Heap sort implementation of the algorithm
    time complexity : O(nlogn)
    space complexity = O(1)
    '''
    prio_queue.heapsort.heap_sort(L)

def mergesort(L):
    '''
    Merge sort implementation
    time complexity: O(nlogn)  
    '''
    def _merge(S1,S2,S):
        '''
        helper function for mergesort
        merges two sorted sequences S1 and S2 into one sorted sequence S
        '''
        i = j = 0
        while i + j < len(S):
            if j == len(S2) or (i < len(S1) and S1[i] < S2[j]):
                S[i+j] = S1[i]
                i+=1
            else:
                S[i+j] = S2[j]
                j+=1


    n = len(L)
    if n<2:
        return  # list is already sorted
    mid = n//2
    # halve the list into two equally sequences
    S1 = L[0:mid]
    S2 = L[mid:n]
    
    # use now recursion to divide elements into small chunks
    mergesort(S1)
    mergesort(S2)
    
    #merge sorted chunks now
    _merge(S1, S2, L)