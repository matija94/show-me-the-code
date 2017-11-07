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
    