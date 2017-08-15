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
    
if __name__ == '__main__':
    q = LinkedQueue()
    q.enqueue('Matija')
    q.enqueue('Lukovic')
    while not q.is_empty():
        print(q.dequeue())