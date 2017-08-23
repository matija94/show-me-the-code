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
        node._prev = node._next = node._element = None # help gc and mark as deprecated
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
            self._container = container # container is reference to the structure which node belongs to 
            self._node = node

        def element(self):
            ''' returns element under this position ''' 
            return self._node._element
        
        def __eq__(self, other):
            ''' equals method '''
            return type(other) is type(self) and other._node is self._node

        def __ne__(self, other):
            ''' not equals '''
            return not (self == other)
        
    
    def _validate(self,p):
        ''' private function to unwrap position and determine if argument is position which references valid node from the positional list object''' 
        if not isinstance(p, self.Position):
            raise TypeError('p must be proper Position type')
        if p._container is not self:
            raise ValueError('p does not belong to this container')
        if p._node._next is None and p._node._prev is None: # convetion for deprecated nodes
            raise ValueError('p is no longer valid')
        return p._node
    
    def _make_position(self, node):
        ''' private function to wrap node into position if the node is not sentinel node ( header or trailer )'''
        if node is self._header or node is self._trailer:
            return None
        return self.Position(self, node)
    
    def first(self):
        ''' inspects first element
            returns position 
        '''
        return self._make_position(self._header._next)
    
    def last(self):
        ''' inspects last element
            returns position
        '''
        return self._make_position(self._trailer._prev)
    
    def before(self, p):
        ''' returns position before argument position
            If argument position is first then returns None
        '''
        node = self._validate(p)
        return self._make_position(node._prev)
    
    def after(self,p):
        ''' returns position after argument position
            If arg pos is last then returns None
        '''
        node = self._validate(p)
        return self._make_position(node._next)
    
    def __iter__(self):
        ''' iterator ''' 
        cursor = self.first()
        while cursor is not None:
            yield cursor.element()
            cursor = self.after(cursor)

    def _insert_between(self, e, predecessor, successor):
        ''' overriden function. Instead of node returns position ''' 
        node = super()._insert_between(e, predecessor, successor)
        return self._make_position(node)
    
    def add_first(self, e):
        ''' insert element at the front of the list and return position '''
        return self._insert_between(e, self._header, self._header._next)
    
    def add_last(self, e):
        ''' insert element at the end of the list and return position '''
        return self._insert_between(e, self._trailer._prev, self._trailer)
    
    def add_before(self, e, p):
        ''' inserts element before arg position and returns position of the element'''
        node = self._validate(p)
        return self._insert_between(e, node._prev, node)
    
    def add_after(self, e, p):
        ''' inserts element after arg position and returns position of the element '''
        node = self._validate(p)
        return self._insert_between(e, node, node._next)
    
    def delete(self, p):
        ''' deletes element at the given position in the list '''
        node = self._validate(p)
        return self._delete_node(node)
    
    def replace(self, p, e):
        ''' Replace element at position p with element e 
        
            Returns the element formerly at position p
        '''
        node = self._validate(p)
        old_value = node._element
        node._element = e
        return old_value

class FavoritesList:
    ''' List of elements ordered from most frequently accessed to least '''
    
    class _Item:
        __slots__ = '_value', '_count'
        
        def __init__(self,e):
            self._value = e
            self._count = 0
            
    def __init__(self):
        self._data = PositionalList()
            
    def __len__(self):
        return len(self._data)
    
    def is_empty(self):
        return len(self._data) == 0
    
    def _find_position(self, e):
        ''' search for element and return its position or None if not found ''' 
        walk = self._data.first()
        while walk is not None and walk.element()._value != e:
            walk = self._data.after(walk)
        return walk
    
    def _move_up(self, p):
        ''' Move item at Position p earlier in the list based on access count '''
        if p != self._data.first():
            cnt = p.element()._count
            walk = self._data.before(p)
            if cnt > walk.element()._count:
                while (walk != self._data.first() and 
                       cnt > self._data.before(walk).element()._count):
                    walk = self._data.before(walk)
                item = self._data.delete(p)
                self._data.add_before(item, walk)
                #self._data.add_before(walk, self._data.delete(p))

    def access(self, e):
        p = self._find_position(e)
        if p is None:
            p = self._data.add_last(self._Item(e))
        p.element()._count+=1
        self._move_up(p)
        
    def remove(self, e):
        p = self._find_position(e)
        if p is not None:
            self._data.delete(p)

    def top(self, k):
        if not 1 <= k <= len(self):
            raise ValueError('illegal value for k')
        walk = self._data.first()
        for j in range(k):
            item = walk.element()
            yield item._value
            walk = self._data.after(walk)
            
class FavoritestListMTF(FavoritesList):
    ''' list of elements ordered with move-to-front heuristic '''
    
    def _move_up(self, p): #override move up to implement move to front instead of shifting to proper index based on cnt
        if p != self._data.first():
            self._data.add_first(self._data.delete(p))
    
    def top(self, k): # override top because list is no more sorted
        if not 1 <= k and k <= len(self):
            raise ValueError('illegal value for k')
        
        temp = PositionalList()
        for item in self._data:
            temp.add_last(item)
        
        for j in range(k): 
            highPos = temp.first()
            walk = temp.after(highPos)
            while walk is not None:
                if walk.element()._count > highPos.element()._count:
                    highPos = walk
                walk = temp.after(walk)
            item = temp.delete(highPos)
            yield item._value
            
class PositionalListUtils:
    
    @staticmethod
    def insertion_sort(L):
        if len(L) > 1:
            marker = L.first()
            while marker != L.last():
                pivot = L.after(marker)
                value = pivot.element()
                if value > marker.element():
                    marker = pivot
                else:
                    walk = marker
                    while walk != L.first() and L.before(walk).element() > value:
                        walk = L.before(walk)
                    L.delete(pivot)
                    L.add_before(walk, value)
                
if __name__ == '__main__':
    fl = FavoritesList()
    fl.access('matija')
    fl.access('igor')
    fl.access('mix')
    
    fl.access('matija')
    fl.access('mix')
    
    for e in fl.top(2):
        print(e)
    
    