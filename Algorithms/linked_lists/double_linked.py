class _DoublyLinkedBase:
    
    ''' maintains header and trailer nodes which will always be empty
        This is important to make removing and adding new elements on respectively size of one element and zero size easier, without any if conditions
        Any element that is added or removed will be having its previous and next because of these special empty nodes we construct
    '''
    
    class _Node:
        __slots__='_element', '_prev', '_next' #streamline memory
        
        def __init__(self,element,prev,next):
            self._element = element
            self._prev = prev
            self._next = next
        
    
    def __init__(self):
        self._size = 0
        self._header = self._Node(None,None,None)
        self._trailer = self._Node(None,None,None)
        self._header._next = self._trailer
        self._trailer._prev = self._header

    def __len__(self):
        return self._size
    
    def is_empty(self):
        return self._size == 0
    
    def _insert_between(self, e, predecessor, successor):
        newest = self._Node(e,predecessor, successor)
        predecessor._next = newest
        successor._prev = newest
        self._size+=1
        return newest
    
    def _delete_node(self, node):
        predecessor = node._prev
        successor = node._next
        predecessor._next = successor
        successor._prev = predecessor
        self._size-=1
        element = node._element
        node. prev = node. next = node. element = None # help gc
        return element
    
class LinkedDeque(_DoublyLinkedBase):
    
    def first(self):
        if self.is_empty():
            raise ValueError('empty')
        return self._header._next._element
    
    def last(self):
        if self.is_empty():
            raise ValueError('empty')
        return self._trailer._prev._element
    
    def insert_first(self, e):
        self._insert_between(e, self._header, self._header._next)

    def insert_last(self, e):
        self._insert_between(e, self._trailer._prev, self._trailer)
        
    def delete_first(self):
        if self.is_empty():
            raise ValueError('empty')
        return self._delete_node(self._header._next)
    
    def delete_last(self):
        if self.is_empty():
            raise ValueError('empty')
        return self._delete_node(self._trailer._prev)

class PositionalList(_DoublyLinkedBase):
    
    class Position:
        ''' an abstraction representing the location of single element ''' 
        def __init__(self, container, node):
            self._container = container
            self._node = node

        def element(self):
            return self._node._element
        
        def __eq__(self, other):
            return type(other) is type(self) and other._node is self._node

        def __ne__(self, other):
            return not (self == other)
        
    
    def _validate(self,p):
        return NotImplemented    
    