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
    
    #R-7.7
    def rotate(self):
        if len(self) > 1:
            oldhead = self._head
            self._head = oldhead._next
            self._tail._next = oldhead
            oldhead._next = None
            self._tail = oldhead
            
    def __iter__(self):
        walk = self._head
        while walk is not None:
            yield walk._e
            walk = walk._next
            
    def __str__(self):
        return str([e for e in self])
    
    
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
    
    def last(self):
        ''' inspects element at the tail of the queue '''
        if self.is_empty():
            raise ValueError('empty')
        return self._tail._element
    
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

class CircularPositionalList(CircularQueue):
    
    class _Cursor:
        __slots_ = '_e', '_container'
        
        def __init__(self, element, container):
            self._container = container
            self._e = element
        
        def __eq__(self, other):
            if not isinstance(other, CircularPositionalList._Cursor): return False
            if not self._e is other._e: return False
            return True
        
        def __ne__(self,other):
            return not (self == other)

        def element(self):
            return self._e._element

    def _validate(self, cursor):
        if not cursor._container is self:
            raise ValueError('cursor not from the same container')
        return cursor._e

    def _make_cursor(self, e):
        c = self._Cursor(e, self)
        return c

    def first(self):
        ''' get first node from list and wrap it in cursor '''
        if super().is_empty():
            raise ValueError('empty')
        return self._make_cursor(self._tail._next)
    
    def last(self):
        if super().is_empty():
            raise ValueError('empty')
        return self._make_cursor(self._tail)
    
    def after(self, cursor):
        node = self._validate(cursor)
        return self._make_cursor(node._next)
    
    def elements(self,k,cursor=None):
        if cursor is None:
            cursor = self.first()
        while k>0:
            yield cursor.element()
            cursor = self.after(cursor)
            k-=1
    
class LinkedListUtils:
    
    #R-7.2
    @staticmethod
    def concat_two_lists(start_node1, start_node2):
        ''' Concatenates two nodes such that stores start_node1 and all successors of itself then does same for start_node2 ''' 
        ans = LinkedQueue()
        current = start_node1
        while current is not None:
            ans.enqueue(current)
            current = current._next
        current = start_node2
        while current is not None:
            ans.enqueue(current)
            current = current._next
        return ans
    
    #R-7.3
    @staticmethod
    def recursive_size(linked_list):
        first = linked_list._head
        def _recursive_size(node):
            if node is None:
                return 0
            return _recursive_size(node._next)+1
        return _recursive_size(first)

    #R-7.5
    @staticmethod
    def circular_list_size(cl):
        walk = cl._tail._next
        ans = 1
        while walk is not cl._tail:
            ans+=1
            walk = walk._next
        return ans
    
    @staticmethod
    def reverse_bugged(L):
        curr = L._head
        prev = None
        n = None
        while curr is not None:
            n = curr._next
            curr._next = prev
            n._next = prev
            curr = n
    
    #C-7.29
    @staticmethod
    def reverse(L):
        ''' reverses single linked list in linear time using constant space ''' 
        curr = L._head
        prev = None
        nxt = None
        while curr is not None:
            nxt = curr._next
            curr._next = prev
            prev = curr
            curr = nxt
        L._head = prev
        
if __name__ == '__main__':
    b = None
    c = None
    c = b
    l = LinkedQueue()
    l.enqueue(1)
    l.enqueue(2)
    l.enqueue(3)
    print(LinkedListUtils.recursive_size(l))
    print(l)

    LinkedListUtils.reverse(l)
    print(l)
    
    cl = CircularQueue()
    for i in range(10):
        cl.enqueue(10)
    print(LinkedListUtils.circular_list_size(cl))
    
    
    
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
        
    pos = CircularPositionalList()
    for i in range(10):
        pos.enqueue(i+1)
    
    for e in pos.elements(20):
        print(e)