from linked_lists.linked_queue import LinkedQueue
import math

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
    

''' recursive merge sort linked list '''
def mergesort_linkedlist(L):
    if L.is_empty(): return L
    tail = [L._head._e] # should be max
    def _helper(head):
        if head is None or head._next is None: 
            tail[0] = head._e if head is not None and head._e > tail[0] else tail[0]
            return head
        middle = _getMiddle(head)
        second_half = middle._next
        middle._next = None
        return _merge_ll(_helper(head), _helper(second_half))
    
    s = L._size
    head = _helper(L._head)
    L.clean()
    L._size = s; L._head = head; L._tail = tail

def _getMiddle(head):
    if head is None: return head
    slow = fast = head
    while (fast._next is not None and fast._next._next is not None):
        slow = slow._next
        fast = fast._next._next
    return slow    


def _merge_ll(a,b):
    dummyHead = LinkedQueue._Node(None,None)
    curr = dummyHead
    while a is not None and b is not None:
        if a._e < b._e:
            curr._next = a; a = a._next
        else:
            curr._next = b; b = b._next 
        curr = curr._next
    curr._next = a if a is not None else b
    return dummyHead._next


''' iterative merge sort for arrays '''
def mergesort_iter(S):
    ''' Sort the elements of list using merge sort bottom-up iteration '''
    n = len(S)
    logn = math.ceil(math.log(n,2))
    src, dest = S, [None] * n
    for i in (2**k for k in range(logn)):
        for j in range(0,n,2*i):
            _merge_iter(src,dest,j,i)
        src, dest = dest, src
    if S is not src:
        S[0:n] = src[0:n]

def _merge_iter(src,result,start,inc):
    end1 = start + inc
    end2 = min(start+2*inc, len(src))
    x,y,z = start, start+inc, start
    while x < end1 and y < end2:
        if src[x] < src[y]:
            result[z] = src[x]; x+=1
        else:
            result[z] = src[y]; y+=1
        z += 1
    if x < end1:
        result[z:end2] = src[x:end1]
    elif y < end2:
        result[z:end2] = src[y:end2]
