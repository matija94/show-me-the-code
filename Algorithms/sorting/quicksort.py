from linked_lists.linked_queue import LinkedQueue
def quick_sort_queue(S):
    n = len(S)
    if n < 2:
        return
    L = LinkedQueue()
    E = LinkedQueue()
    G = LinkedQueue()
    
    pivot = S.top()
    while not S.is_empty():
        if S.top() < pivot:
            L.enqueue(S.dequeue())
        elif S.top() > pivot:
            G.enqueue(S.dequeue())
        else:
            E.enqueue(S.dequeue)
    
    quick_sort_queue(L)
    quick_sort_queue(G)
    
    while not L.is_empty():
        S.enqueue(L.dequeue())
    while not E.is_empty():
        S.enqueue(E.dequeue())
    while not G.is_empty():
        S.enqueue(G.dequeue())
    
    
def inplace_quick_sort(S,a,b):
    ''' sort the list from S[a] to S[b] inclusive using the quick-sort algorithm '''
    
    if a >= b: return
    pivot = S[b] #last element of range is pivot
    left = a
    right = b-1
    while left <= right:
        #scan until reaching value equal or larger than pivot(or right marker)
        while left <= right and S[left] < pivot:
            left+=1
        #scan until reaching value equal or smaller than pivot (or left marker)
        while left <= right and S[right] > pivot:
            right-=1
        if left <= right:
            S[left],S[right] = S[right],S[left]
            left,right = left+1,right-1
    
    # put pivot into it's final place
    S[left],S[b] = S[b],S[left]
    #make recursive calls 
    inplace_quick_sort(S, a, left-1)
    inplace_quick_sort(S, left+1, b)
    
    
    
if __name__ == '__main__':
    l = [1,2,3]
    inplace_quick_sort(l, 0, 2)
