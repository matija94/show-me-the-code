from prio_queue.structures import MinPriorityQueue

def plist_sort(L):
    '''
    Sorts positional list of comparable elements in non-decreasing order(if there are duplicates) or in increasing order if there are no duplicate
    This function uses additional space proportional to positional list L
    Time complexity O(nlogn)
    '''
    n = len(L)
    p = MinPriorityQueue()
    for i in range(n):
        item = L.delete(L.first())
        p.add(item, item)
    while not p.is_empty():
        key,val = p.remove_min()
        L.add_last(val)

def heap_sort(L):
    '''
    sorts list inplace(without using additional space) in increasing order
    Time complexity O(nlogn)
    '''
    def _upheap(i,l):
        parent = (i-1)//2
        if parent >= 0 and l[i] > l[parent]:
            l[i],l[parent] = l[parent],l[i]
            _upheap(parent, l)
    for i in range(len(L)): # make the whole array to 'hold max-heap property'
        _upheap(i, L)
    def _downheap(i,l,n):
        big = (2*i)+1
        if big < n:
            right_child = (2*i)+2
            if right_child < n and l[right_child] > l[big]:
                big = right_child
            if l[big] > l[i]:
                l[i],l[big] = l[big],l[i]
            _downheap(big, l, n)
    for i in range(len(L)): # in each iteration add head of the max-heap to the end of the array
        n = len(L)-(i+1) # last index in the heap
        L[0],L[len(L)-(i+1)]=L[len(L)-(i+1)],L[0] # swap head of the max-heap with the last element from the array
        _downheap(0, L, n) # downheap swapped element in the remaining heap space to maintain max-heap property



if __name__ == '__main__':
    L = [15,13,11,4,6,22,13,1,8,25,17,14]
    heap_sort(L)
    print(L)
        
            