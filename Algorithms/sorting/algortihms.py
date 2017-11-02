import prio_queue
from linked_lists.linked_queue import LinkedQueue

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
    

def _mergesort_linkedlist(L):
    
    def _merge(LL1,LL2,LL):
        
        while not LL1.is_empty() and not LL2.is_empty():
            if LL1.top() < LL2.top():
                LL.enqueue(LL1.dequeue())
            else:
                LL.enqueue(LL2.dequeue())
        while not LL1.is_empty():
            LL.enqueue(LL1.dequeue())
        while not LL2.is_empty():
            LL.enqueue(LL2.dequeue())
                
    n = len(L)
    if n < 2:
        return
    mid = n//2
    LL1 = LinkedQueue()
    LL2 = LinkedQueue()
    while len(L) > mid:
        LL1.enqueue(L.dequeue())
    while not L.is_empty():
        LL2.enqueue(L.dequeue())

    _mergesort_linkedlist(LL1)
    _mergesort_linkedlist(LL2)
    _merge(LL1, LL2, L)


if __name__ == '__main__':
    
    ll = LinkedQueue()
    ll.enqueue(4)
    ll.enqueue(3)
    ll.enqueue(2)
    ll.enqueue(1)

    _mergesort_linkedlist(ll)

    print(ll)