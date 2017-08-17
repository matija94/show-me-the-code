   
            
class LinkedQueue:
    class _Node:
        __slots_ = '_e', '_next' #streamline memory usage
        
        def __init__(self,e,next):
            self._e = e
            self._next = next 
    
    def __init__(self):
        self._head = None
        self._tail = None
        self._size = 0
    
    def __len__(self):
        return self._size
    
    def is_empty(self):
        return self._size==0
    
    def enqueue(self, e):
        ''' Adds element to the back of the queue '''
        new_node = self._Node(e,None)
        if self.is_empty():
            self._head = new_node
        else:
            self._tail._next = new_node
        self._tail = new_node
        self._size+=1
        
    def dequeue(self):
        ''' removes and retrieves head of the queue'''
        ret = self.top()
        self._head = self._head._next
        self._size-=1
        if self.is_empty():
            self._tail = None
        return ret
    
    def top(self):
        ''' inspects head of the queue'''
        if self.is_empty():
            raise ValueError('empty')
        return self._head._e

class CircularQueue:
    ''' Circular queue, maintains pointers to the next elements, such that tail points to the front instead of having pointer to null address
        This structure is useful in scenarios where every item from collection of items needs to be processed regularly.
        
        For instance imagine OS having to feed each process with CPU time. It could use this circular structure
        That is called round-robing scheduler
    '''
    class _Node:
        __slots_ = '_e', '_next' #streamline memory usage
        
        def __init__(self,e,next):
            self._element = e
            self._next = next 

    def __init__(self):
        self._tail = None
        self._size = 0
        
    def __len__(self):
        return self._size
    
    def is_empty(self):
        return self._size == 0
    
    def first(self):
        ''' inspects element at the front of the queue ''' 
        if self.is_empty():
            raise ValueError('empty')
        head = self._tail._next
        return head._element
    
    def enqueue(self, e):
        ''' adds element to the back of the queue ''' 
        newest = self._Node(e,None)
        if self.is_empty():
            newest._next = newest # initialize circular manner
        else:
            newest._next = self._tail._next # newest next points to head
            self._tail._next = newest # tail points to newest
        self._tail = newest # newest is the new tail
        self._size+=1
        
    def dequeue(self):
        ''' removes element from front of the queue and retrieves to the caller ''' 
        if self.is_empty():
            raise ValueError('empty')
        old_head = self._tail._next
        self._tail._next = old_head._next
        self._size-=1
        if self._size==0:
            self._tail = None
        return old_head._element
    
    def rotate(self):
        ''' puts the front of the queue to the back
            same as calling dequeue on non empty queue and then calling enqueue for the dequeued element
            que.enqueue(que.dequeue)
            
            This function escapes overhead that would enqueue on dequeued element make
        ''' 
        if self._size > 1:
            self._tail = self._tail._next
    
if __name__ == '__main__':
    q = LinkedQueue()
    q.enqueue('Matija')
    q.enqueue('Lukovic')
    while not q.is_empty():
        print(q.dequeue())
        
    
    cq = CircularQueue()
    cq.enqueue('matija')
    cq.enqueue('lukovic')
    
    for i in range(3):
        print(' hi i am ' + cq.first())
        cq.rotate()