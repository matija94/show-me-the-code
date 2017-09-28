from linked_lists.double_linked import PositionalList
from trees.concrete import ArrayBinaryTree
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

class HeapPriorityQueue(PriorityQueueBase):
    '''
    TODO : finish impl of min heap prio queue using arraybinarytree
    '''
    def __init__(self):
        self._data = ArrayBinaryTree()
        
    def __len__(self):
        return len(self._data)
    
    def _swap(self,pos1,pos2):
        i = pos1._index
        j = pos2._index
        self._data._nodes[i], self._data._nodes[j] = self._data._nodes[j], self._data._nodes[i]
    
    def _upheap(self,pos):
        parent = self._data.parent(pos)
        if parent is not None and pos.element() < parent.element():
            self._swap(pos, parent)
            self._upheap(parent)
    
    def _downheap(self,pos):
        left = self._data.left(pos)
        if left is not None:
            small = left
            right = self._data.right(pos)
            if right is None and right.element() < small:
                small = right
            if small.element() < pos.element():
                self._swap(small, pos)
                self._downheap(small)
    '''
    #### MISSING PUBLIC FUNCTIONS ####
    '''

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
    
    def __init__(self):
        self._data = []
        
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
        e = self.min()
        self._data[0] = self._data.pop()
        self._downheap(0)
        return e
        
        
if __name__ == '__main__':
    mh = MinPriorityQueue()
    mh.add(3, 'Matija')
    mh.add(1,'Ckilim')
    mh.add(0, 'MlTech')
    
    key,value = mh.remove_min()
    print(value)
            
        
         
    