from linked_lists.double_linked import PositionalList

class PriorityQueueBase:
    
    ''' abstract base class for a priority queue '''
    
    class _Item:
        __slots_ = '_key', '_value'
        
        def __init__(self,k,v):
            self._key = k
            self._value = v
            
        def __lt__(self, other):
            return self._key < other._key
    
    def is_empty(self):
        return len(self) == 0


class UnsortedPriorityQueue(PriorityQueueBase):
    
    ''' a min-oriented priority queue implemented with unsorted list '''
    
    def _find_min(self):
        ''' private function. Finds position holding smallest element in the queue'''
        if self.is_empty():
            raise ValueError('priority queue is empty')
        small = self._data.first()
        walk = self._data.after(small)
        while walk is not None:
            if walk.element() < small.element():
                small = walk
            walk = self._data.after(walk)
        return small
        
    def __init__(self):
        self._data = PositionalList()
        
    def __len__(self):
        return len(self._data)
    
    def add(self, key, value):
        ''' adds a key-value pair '''
        self._data.add_last(self._Item(key,value))
    
    def min(self):
        ''' inspect min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._find_min()
        item = p.element()
        return (item._key, item._value)
    
    def remove_min(self):
        ''' remove and return min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._find_min()
        item = self._data.delete(p)
        return (item._key, item._value)
    
class SortedPriorityQueue(PriorityQueueBase):
    
    def __init__(self):
        self._data = PositionalList()
        
    def __len__(self):
        return len(self._data)
    
    def add(self, key, value):
        ''' add a key-value pair '''
        newest = self._Item(key,value)
        walk = self._data.last()
        while walk is not None and newest < walk.element():
            walk = self._data.before(walk)
        if walk is None:
            self._data.add_first(newest)
        else:
            self._data.add_after(newest, walk)
    
    def min(self):
        ''' inspects minimum key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data.first().element()
        return (item._key, item._value)
    
    def remove_min(self):
        ''' removes and returns min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._data.first()
        item = self._data.delete(p)
        return (item._key, item._value)

class MaxPriorityQueue(PriorityQueueBase):

    def _left(self, j):
        return 2 * j + 1

    def _right(self, j):
        return 2 * j + 2

    def _parent(self, j):
        return (j - 1) // 2

    def _swap(self, i, j):
        self._data[i], self._data[j] = self._data[j], self._data[i]

    def _upheap(self, j):
        parent = self._parent(j)
        if parent >= 0 and self._data[j] > self._data[parent]:
            self._swap(j, parent)
            self._upheap(parent)

    def _downheap(self, j):
        left = self._left(j)
        if left < len(self._data):
            big = left
            right = self._right(j)
            if right < len(self._data) and self._data[right] > self._data[left]:
                big = right
            if self._data[big] > self._data[j]:
                self._swap(big, j)
                self._downheap(big)

    def __len__(self):
        return len(self._data)

    def __init__(self, contents=()):
        self._data = [self._Item(k, v) for k, v in contents]
        if len(self._data) > 1:
            self._heapify()

    def _heapify(self):
        '''
        private function. Finds first non-leaf element and performs down heap for each element from first non-leaf to root
        '''
        first_non_leaf = self._parent(len(self._data) - 1)
        for i in range(first_non_leaf, -1, -1):
            self._downheap(i)

    def add(self, key, value):
        ''' adds key-value pair'''
        self._data.append(self._Item(key, value))
        self._upheap(len(self._data) - 1)

    def max(self):
        ''' inspects head of the queue
            Head of the queue is the minimum element heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data[0]
        return (item._key, item._value)

    def remove_min(self):
        '''
        removes and returns head of the queue
        head of the queue is the minimum element in the heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        self._swap(0, len(self._data) - 1)
        item = self._data.pop()
        self._downheap(0)
        return (item._key, item._value)

class MinPriorityQueue(PriorityQueueBase):
    
    def _left(self,j):
        return 2*j+1
    
    def _right(self,j):
        return 2*j+2
    
    def _parent(self,j):
        return (j-1)//2
    
    def _swap(self,i,j):
        self._data[i], self._data[j] = self._data[j], self._data[i]
        
    def _upheap(self,j):
        parent = self._parent(j)
        if parent >= 0 and self._data[j] < self._data[parent]:
            self._swap(j, parent)
            self._upheap(parent)
            
    def _downheap(self,j):
        left = self._left(j)
        if left < len(self._data):
            small = left
            right = self._right(j)
            if right < len(self._data) and self._data[right] < self._data[left]:
                small = right
            if self._data[small] < self._data[j]:
                self._swap(small, j)
                self._downheap(small)
    
    def __len__(self):
        return len(self._data)
    
    def __init__(self, contents=()):
        self._data = [self._Item(k,v) for k,v in contents]
        if len(self._data) > 1:
            self._heapify()
    
    def _heapify(self):
        '''
        private function. Finds first non-leaf element and performs down heap for each element from first non-leaf to root
        '''
        first_non_leaf = self._parent(len(self._data)-1)
        for i in range(first_non_leaf,-1,-1):
            self._downheap(i)
        
    def add(self, key, value):
        ''' adds key-value pair'''
        self._data.append(self._Item(key,value))
        self._upheap(len(self._data)-1)
        
    def min(self):
        ''' inspects head of the queue
            Head of the queue is the minimum element heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data[0]
        return (item._key, item._value)
    
    def remove_min(self):
        '''
        removes and returns head of the queue
        head of the queue is the minimum element in the heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        self._swap(0,len(self._data)-1)
        item = self._data.pop()
        self._downheap(0)
        return (item._key, item._value)

class AdaptableMinPriorityQueue(MinPriorityQueue):
    
    class Locator(MinPriorityQueue._Item):
        __slots_ = '_index'
        
        def __init__(self,k,v,j):
            super().__init__(k, v)
            self._index = j
            
    def _swap(self, i, j):
        super()._swap(i, j)
        self._data[i]._index = i #reset locator index, post-swap
        self._data[j]._index = j #reset locator index, post-swap
        
    def _validate_loc(self,loc):
        if not type(loc) is self.Locator:
            raise TypeError('not locator')
        j = loc._index
        if not (0 <= j < len(self._data) and self._data[j] is loc):
            raise ValueError('invalid locator')
        return j
        
    def _bubble(self,j):
        if j > 0 and self._data[j] < self._data[self._parent(j)]:
            self._upheap(j)
        else:
            self._downheap(j)
    
    def add(self,key,value):
        ''' Add a key,value pair and returns Locator for new entry '''
        token = self.Locator(key, value, len(self._data))
        self._data.append(token)
        self._upheap(len(self._data)-1)
        return token
    
    def update(self,loc,newkey,newvalue):
        ''' Updates key and value for the entry identified by Locator loc '''
        j = self._validate_loc(loc)
        loc._key = newkey
        loc._value = newvalue
        self._bubble(j)
        
    def remove(self,loc):
        ''' Remove and return (k,v) pair identified by Locator loc '''
        j = self._validate_loc(loc)
        if j == len(self._data)-1:
            self._data.pop()
        else:
            self._swap(j, len(self._data)-1)
            self._data.pop()
            self._bubble(j)
        return (loc._key, loc._value)


class AdaptableMaxPriorityQueue(MaxPriorityQueue):
    class Locator(MaxPriorityQueue._Item):
        __slots_ = '_index'

        def __init__(self, k, v, j):
            super().__init__(k, v)
            self._index = j

    def _swap(self, i, j):
        super()._swap(i, j)
        self._data[i]._index = i  # reset locator index, post-swap
        self._data[j]._index = j  # reset locator index, post-swap

    def _validate_loc(self, loc):
        if not type(loc) is self.Locator:
            raise TypeError('not locator')
        j = loc._index
        if not (0 <= j < len(self._data) and self._data[j] is loc):
            raise ValueError('invalid locator')
        return j

    def _bubble(self, j):
        if j > 0 and self._data[j] > self._data[self._parent(j)]:
            self._upheap(j)
        else:
            self._downheap(j)

    def add(self, key, value):
        ''' Add a key,value pair and returns Locator for new entry '''
        token = self.Locator(key, value, len(self._data))
        self._data.append(token)
        self._upheap(len(self._data) - 1)
        return token

    def update(self, loc, newkey, newvalue):
        ''' Updates key and value for the entry identified by Locator loc '''
        j = self._validate_loc(loc)
        loc._key = newkey
        loc._value = newvalue
        self._bubble(j)

    def remove(self, loc):
        ''' Remove and return (k,v) pair identified by Locator loc '''
        j = self._validate_loc(loc)
        if j == len(self._data) - 1:
            self._data.pop()
        else:
            self._swap(j, len(self._data) - 1)
            self._data.pop()
            self._bubble(j)
        return (loc._key, loc._value)


if __name__ == '__main__':
    mh = MaxPriorityQueue(((9,'f'), (7,'s'), (5,'z'), (3,'t'), (1,'m')))
    mh.add(3, 'Matija')
    mh.add(1,'Ckilim')
    mh.add(15, 'MlTech')
    
    key,value = mh.remove_min()
    print(value)
            
        
         
    