class LinkedStack:

    class _Node:
        
        __slots_ = '_element', '_next' #streamline memory usage
        
        def __init__(self, element, next):
            self._element = element
            self._next = next
        

    def __init__(self):
        self._head = None
        self._size = 0
    
    def __len__(self):
        return self._size
    
    def is_empty(self):
        return self._size == 0
        
    def push(self,e):
        self._head = self._Node(e, self._head)
        self._size+=1
        
    def top(self):
        ''' inspects top element of the stack'''
        if self.is_empty():
            raise ValueError('empty')
        return self._head._element
    
    def pop(self):
        ''' removes and retrieves head of the stack '''
        ret = self.top()
        self._head = self._head._next
        self._size-=1
        return ret
    
if __name__ == '__main__':
    s = LinkedStack()
    s.push('matija')
    s.push('lukovic')
    print(s.top())
    while not s.is_empty():
        print(s.pop())