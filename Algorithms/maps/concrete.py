from maps.base import MapBase, HashMapBase
from trees.concrete import LinkedBinaryTree
from abc import abstractmethod
class UnsortedTableMap(MapBase):
    
    def __init__(self):
        self._table = []
        
    def __getitem__(self, key):
        for item in self._table:
            if item._key == key:
                return item._value
        raise KeyError('Key error: ' + repr(key))
    
    def __setitem__(self, key, value):
        for item in self._table:
            if item._key == key:
                item._value = value
                return
        self._table.append(self._Item(key, value))
        
    def __delitem__(self, key):
        for i in range(len(self._table)):
            if self._table[i]._key == key:
                self._table.pop(i)
                return
        raise KeyError('Key Error: ' + repr(key))
    
    def __len__(self):
        return len(self._table)
    
    def __iter__(self):
        for item in self._table:
            yield item._key

    def setdefault(self, key, default=None):
        '''
        IF key already exists in the map returns it's mapping, otherwise inserts new mapping in the map with k=default
        '''
        for item in self._table:
            if item._key == key:
                return item._value
        self._table.append(self._Item(key,default))
        
class ChainHashMap(HashMapBase):
    ''' hash map implementation with separate chaining for collision resolution '''
    
    def _bucket_getitem(self, j, k):
        bucket = self._table[j]
        if bucket is None:
            raise KeyError('Key Error: ' + repr(k))
        return bucket[k]
    
    def _bucket_setitem(self, j, k, v):
        bucket = self._table[j]
        if bucket is None:
            bucket = UnsortedTableMap()
        oldsize = len(bucket)
        bucket[k] = v
        if len(bucket) > oldsize:
            self._n += 1
    
    def _bucket_delitem(self, j, k):
        bucket = self._table[j]
        if bucket is None:
            raise KeyError('Key Error: ' + repr(k))
        del bucket[k]
        
    def __iter__(self):
        for bucket in self._table:
            if bucket is not None:
                for key in bucket:
                    yield key
                    
                    
class ProbeHashMap(HashMapBase):
    ''' hash map implementation with linear probing for collision resolution '''
    
    _AVAIL = object() # sentinel marks locations of previous deletion
    
    def _is_available(self, j):
        return self._table[j] is None or self._table[j] is ProbeHashMap._AVAIL
    
    def _find_slot(self,j,k):
        firstAvail = None
        while True:
            if self._is_available(j):
                if firstAvail is None:
                    firstAvail = j
                if self._table[j] is None:
                    return (False, firstAvail)
            elif k == self._table[j]._key:
                return (True, j)
            j = j + 1 % len(self._table)
            
    def _bucket_getitem(self, j, k):
        found,i = self._find_slot(j, k)
        if not found:
            raise KeyError('Key Error : ' + repr(k))
        return self._table[i]._value
    
    def _bucket_setitem(self, j, k, v):
        found, i = self._find_slot(j, k)
        if not found:
            self._table[i] = self._Item(k,v)
            self._n += 1
        else:
            self._table[i]._value = v
            
    def _bucket_delitem(self, j, k):
        found,i = self._find_slot(j, k)
        if not found:
            raise KeyError('Key Error : ' + repr(k))
        self._table[i] = ProbeHashMap._AVAIL
        
    def __iter__(self):
        for i in range(len(self._table)):
            if not self._is_available(i):
                yield self._table[i]._key
                

class SortedTableMap(MapBase):
    
    ''' map representation using sorted table '''
    
    ''' ### private methods ### '''
    def _find_index(self,k,low,high):
        '''
        Return index of the leftmost item with key greater than or equal to k.
        
        Return high + 1 if no such item qualifies.
        That is, j will be returned such that:
        all items of slice table[low:j] have key < k
        all items of slice table[j:high+1] have key >= k
        '''
        if high < low:
            return high+1
        mid = (low + high) // 2
        if k == self._table[mid]._key:
            return mid
        elif k < self._table[mid]._key:
            return self._find_index(k, low, mid-1)
        else:
            return self._find_index(k, mid+1, high)
    
    ''' ### public methods ### '''
    def __init__(self):
        self._table = []
        
    def __len__(self):
        return len(self._table)
    
    def __getitem__(self, key):
        j = self._find_index(key, 0, len(self._table)-1)
        if j == len(self._table) or self._table[j]._key != key:
            raise KeyError('Key Error: ' + repr(key))
        return self._table[j]._value
    
    def __setitem__(self, key, value):
        j = self._find_index(key, 0, len(self._table)-1)
        if j == len(self._table) or self._table[j]._key != key:
            self._table.insert(j, self._Item(key,value))
        else:
            self._table[j]._value = value
            
    def __delitem__(self, key):
        j = self._find_index(key, 0, len(self._table)-1)
        if j == len(self._table) or self._table[j]._key != key:
            raise KeyError('Key Error: ' + repr(key))
        self._table.pop(j)
        
    def __iter__(self):
        for item in self._table:
            yield item._key
            
    def __reversed__(self):
        for item in reversed(self._table):
            yield item._key
            
    def find_min(self):
        if len(self._table) > 0:
            return (self._table[0]._key, self._table[0]._value)
        else:
            return None
    
    def find_max(self):
        if len(self._table>0):
            return (self._self._table[-1]._key, self._table[-1]._value)
        else:
            return None
    
    def find_ge(self,k):
        '''
        Find least key grater than or equal to k
        Returns (key, value) pair
        '''
        j = self._find_index(k, 0, len(self._table)-1)
        if j >= len(self._table):
            return None
        return (self._table[j]._key, self._table[j]._value)
    
    def find_le(self,k):
        '''
        Find least key lower than k
        Returns (key,value) pair
        '''
        if len(self._table) == 0: return None
        j = self._find_index(k, 0, len(self._table)-1)
        if j>= len(self._table):
            return (self._table[len(self._table)-1]._key, self._table[len(self._table)-1]._value)
        elif j==0 and self._table[j]._key <= k:
            return(self._table[j]._key, self._table[j]._value)
        if j > 0 :
            if self._table[j]._key > k:
                return(self._table[j-1]._key, self._table[j-1]._value)
            else:
                return(self._table[j]._key, self._table[j]._value)
        else:
            return None
        
    def find_lt(self,k):
        '''
        Finds greatest key strictly less than k
        Returns (key,value) pair
        '''
        j = self._find_index(k, 0, len(self._table)-1)
        if j > 0:
            return (self._table[j-1]._key, self._table[j-1]._value)
        else:
            return None
    
    def find_gt(self, k):
        '''Return (key,value) pair with least key strictly greater than k.'''
        j = self._find_index(k, 0, len(self._table)-1)
        if j >= len(self._table):
            return None
        elif k == self._table[j]._key and j+1 < len(self._table):
            return (self._table[j+1]._key, self._table[j+1]._value)
        elif k < self._table[j]._key:
            return (self._table[j]._key, self._table[j]._value)
        else:
            return None
    def find_range(self, start, stop):
        '''
        Iterate all (key,value) pairs such that start <= key < stop.
        If start is None, iteration begins with minimum key of map.
        If stop is None, iteration continues through the maximum key of map.
        '''
        if start is None:
            j = 0
        else:
            j = self._find_index(start, 0, len(self._table)-1)
        while j < len(self._table) and (stop is None or self._table[j]._key < stop):
            yield (self._table[j]._key, self._table[j]._value)
            j+=1

class TreeMap(LinkedBinaryTree, MapBase):
    ''' sorted map implementation using binary tree '''
    
    class Position(LinkedBinaryTree.Position):
        def key(self):
            return self.element()._key
        def value(self):
            return self.element()._value
    
    ''' private functions used by subclasses of tree map which rebalance the tree making it's height to be function of log N'''
    def _relink(self,parent,child,make_left_child):
        if make_left_child:
            parent._left = child
        else:
            parent._right = child
        if child is not None:
            child._parent = parent

    def _rotate(self,p):
        ''' rotate position p above it's parent 
        5            11
         \          /  \
         11   =>   5   21
          \
          21
        '''
        x = p._node
        y = x._parent
        z = y._parent
        if z is None:
            self._root = x
            x._parent = None
        else:
            self._relink(z, x, y == z._left)
        if x == y._left:
            self._relink(y, x._right, True)
            self._relink(x, y, False)
        else:
            self._relink(y, x._left, False)
            self._relink(x, y, True)
    
    
    def _restructure(self,x):
        ''' perform trinode restructure of Position x with parent/grandparent 
            This method decides whether to call single rotate or double
        '''
        y = self.parent(x)
        z = self.parent(y)
        if(x==self.right(y)) == (y==self.right(z)): # single rotation check
            self._rotate(y)
            return y
        else:
            self._rotate(x)
            self._rotate(x)
            return x
    
    ''' private functions used by tree map concrete impl '''    
    def _subtree_search(self, p, k):
        ''' returns position of p's subtree having key k, or last node searched '''
        if k == p.key():
            return p
        elif k < p.key():
            if self.left(p) is not None:
                return self._subtree_search(self.left(p), k) # search left subtree
        else:
            if self.right(p) is not None:
                return self._subtree_search(self.right(p), k) # search right subtree 
        return p # unsuccessful search
    
    def _subtree_first_position(self,p):
        ''' returns first positon of the binary tree. This is node that would be traversed first by in-order dfs ''' 
        walk = p
        while self.left(walk) is not None:
            walk = self.left(walk)
        return walk
    
    def _subtree_last_position(self,p):
        ''' returns last position of the binary tree. This is node that would be traversed last by in-order dfs'''
        walk = p
        while self.right(walk) is not None:
            walk = self.right(walk)
        return walk
    
    def first(self):
        ''' returns the first position in the tree or none if tree is empty'''
        return self._subtree_first_position(self.root()) if len(self) > 0 else None
    
    def last(self):
        ''' returns last position in the tree or none if tree is empty '''
        return self._subtree_last_position(self.root()) if len(self)>0 else None
    
    def before(self,p):
        ''' return the position just before p in the natural order.
        
        return none if p is the first position 
        '''
        
        self._validate(p)
        if self.left(p):
            return self._subtree_last_position(self.left(p)) # has left subtree, so find biggest element in this subtree
        else: # go upward, look for first parent that is visited by its right child
            walk = p
            above = self.parent(walk)
            while above is not None and walk == self.left(above):
                walk = above
                above = self.parent(above)
            return above
    
    def after(self,p):
        '''
        return the position just after p in the natural order
        
        return none if p is last position
        '''
        self._validate(p)
        if self.right(p):
            return self._subtree_first_position(self.right(p))
        else: # go upward, look for first parent that is visited by its left child
            walk = p
            above = self.parent(walk)
            while above is not None and walk == self.right(above):
                walk = above
                above = self.parent(above)
            return above
        
    def find_position(self,k):
        ''' return position with key k or else neighbour '''
        
        if self.is_empty(): return None
        else :
            p = self._subtree_search(self.root(), k)
            self._rebalance_access(p)
            return p
        
    def find_min(self):
        ''' return minimum (key,value) pair or None if tree is empty '''
        if self.is_empty():return None
        else:
            p = self.first()
            return (p.key(), p.value())
        
    
    def find_ge(self, k):
        '''
        return (key,value) with least key greater than or equal to k
        '''
        if self.is_empty(): return None
        else:
            p = self.find_position(k)
            if p.key() < k:
                p = self.after(p)
            return (p.key(),p.value()) if p is not None else None        
    
    def find_le(self,k):
        ''' 
        return (key,value) pair with least key lower than or equal to k
        '''
        if self.is_empty(): return None
        else:
            p = self.find_position(k)
            if p.key() > k:
                p = self.before(p)
            return (p.key(),p.value())if p is not None else None
        
    def find_range(self,start,stop):
        '''
        iterate all (key,value)pairs such that start<=key<=stop
        
        if start is none, iteration begins with min key of the map
        if stop is none, iteration continues to the max key of the map
        '''
        if not self.is_empty():
            if start is None:
                p = self.first()
            else:
                p = self.find_position(start)
                if p.key() < start: p = self.after(p)
            while p is not None and (stop is None or p.key() < stop):
                yield (p.key(), p.value())
                p = self.after(p)

    def __getitem__(self, key):
        if self.is_empty():
            raise KeyError('Key Error: ' + repr(key))
        else:
            p = self._subtree_search(self.root(), key)
            self._rebalance_access(p)
            if key != p.key():
                raise KeyError('Key Error: ' + repr(key))
            return p.value()
    
    def __setitem__(self, key, value):
        if self.is_empty():
            self.add_root(self._Item(key,value))
        else:
            p = self._subtree_search(self.root(), key)
            if p.key() == key:
                p.element()._value = value
                self._rebalance_access(p)
                return
            else:
                item = self._Item(key,value)
                if p.key() < key:
                    leaf = self.add_right(p,item)
                else:
                    leaf = self.add_left(p,item)
                self._rebalance_insert(leaf)

    def __iter__(self):
        p = self.first()
        while p is not None:
            yield p.key()
            p = self.after(p)
            
    def delete(self, p):
        self._validate(p)
        if self.left(p) and self.right(p):
            replacement = self._subtree_last_position(self.left(p))
            self.replace(p, replacement.element())
            p = replacement
        #now p has most one child
        parent = self.parent(p)
        super().delete(p)
        self._rebalance_delete(parent)
        
    def __delitem__(self, key):
        if not self.is_empty():
            p = self._subtree_search(self.root(), key)
            if p.key() == key:
                self.delete(p)
                return
            self._rebalance_access(p)
        raise KeyError('Key Error: ' + repr(key))

    ''' abstract methods for rebalancing strategy '''
    def _rebalance_access(self,p):
        ''' called after searching the key in the map
            this method should bring frequently searched keys closer to the root
        '''
        pass
    
    def _rebalance_insert(self,p):
        '''
        called when new key is inserted in the map
        '''
        pass
    
    def _rebalance_delete(self,p):
        '''
        called on the parent of the deleted key in the map
        '''
        pass


class AVLTreeMap(TreeMap):
    ''' sorted map implementation using AVLTree '''
    
    class _Node(TreeMap._Node):
        ''' node class maintaining height value for balancing '''
        __slots__='_height'
        
        def __init__(self,element, parent=None,left=None,right=None):
            super().__init__(element, parent, left, right)
            self._height = 0
            
        def left_height(self):
            return self._left._height if self._left is not None else 0
        
        def right_height(self):
            return self._right._height if self._right is not None else 0
        
        
    def _recompute_height(self,p):
        p._node._height = 1 + max(p._node.left_height(), p._node.right_height())
    
    def _is_balanced(self,p):
        return abs(p._node.left_height() - p._node.right_height()) <= 1
    
    def _tall_child(self,p,favorLeft=False):
        if p._node.left_height() + (1 if favorLeft else 0) > p._node.right_height():
            return self.left(p)
        else:
            return self.right(p)
        
    def _tall_grandchild(self,p):
        child = self._tall_child(p)
        
        alingment = self.left(p) == child
        return self._tall_child(child, alingment)
    
    def _rebalance(self,p):
        while p is not None:
            old_height = p._node._height
            if not self._is_balanced(p):
                p = self._restructure(self._tall_grandchild(p))
                self._recompute_height(self.left(p))
                self._recompute_height(self.right(p))
            self._recompute_height(p)    
            if p._node._height == old_height:
                p = None
            else:
                p = self.parent(p)
                
    def _rebalance_delete(self, p):
        self._rebalance(p)
    
    def _rebalance_insert(self, p):
        self._rebalance(p)


class SplayTreeMap(TreeMap):

    def _splay(self,p):
        y = self.parent(p)
        z = self.parent(y)
        if (y == self.left(z)) == (p == self.left(y)): #all nodes are on same side
            self._rotate(y)
            self._rotate(p)
        elif z is None:
            self._rotate(p)
        else:#zig zag
            self._rotate(p)
            self._rotate(p)

    def _rebalance_insert(self, p):
        self._splay(p)
        
    def _rebalance_access(self, p):
        self._splay(p)
    
    def _rebalance_delete(self, p):
        if p is not None:
            self._splay(p)
            
if __name__ == '__main__':
    t = AVLTreeMap()
    t[10] = 'Matija'
    t[7] = 'seven'
    # triggers single rotation on insertion
    t[5] = 'five'
    t[8] = 'eight'
    # triggers double rotation on insertion
    t[9] = 'nine'
    t[6] = 'six'
    t[11] = 'eleven'
    #triggers single rotation on deletion
    del t[6]
    
    for e,v in t.items():
        print(e,v)
