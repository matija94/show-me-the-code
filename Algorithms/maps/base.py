from collections import MutableMapping
from random import randrange
class MapBase(MutableMapping):
    
    class _Item:
        __slots_ = '_key', '_value'
        
        def __init__(self,key,value):
            self._key = key
            self._value = value
            
        def __eq__(self, other):
            return self._key == other._key
        
        def _ne__(self, other):
            return not (self == other)
        
        def __lt__(self,other):
            return self._key < other._key
        

class HashMapBase(MapBase):
    ''' abstract base class for map using hash-tables with MAD compression '''
    
    def __init__(self,cap=11,p=109345121):
        self._table = cap * [None]
        self._n = 0
        self._prime = p
        self._scale = randrange(p-1) + 1
        self._shift = randrange(p)
        
    def _hash_function(self, k):
        return (hash(k)* self._scale + self._shift) % self._prime % self._table
    
    def __len__(self):
        return self._n
    
    def __getitem__(self, key):
        j = self._hash_function(key)
        return self._bucket_getitem(j,key)
    
    def __setitem__(self, key, value):
        j = self._hash_function(key)
        self._bucket_setitem(j,key,value)
        if self._n > len(self._table) // 2: # keep load factor <= 0.5
            self._resize(2*len(self._table)-1)
    
    def __delitem__(self, key):
        j = self._hash_function(key)
        self._bucket_delitem(j,key)
        self._n -= 1
        
    def _resize(self, c):
        old = list(self.items())
        self._table = c * [None]
        self._n = 0
        for (k, v) in old:
            self[k] = v
            
    ''' ABSTRACT METHODS '''
    
    def _bucket_getitem(self,j,k):
        ''' gets the item from the bucket at index j with key k '''
        return NotImplementedError()

    def _bucket_setitem(self,j,k,v):
        ''' sets the item in the bucket at index j with value v associated with key k.
        If Key k already exists old value will be replace with new value v
        Implementation of this method is responsible for incrementing size of the elements
        '''
        return NotImplementedError()
    
    def _bucket_delitem(self,j,k):
        ''' deletes the item in the bucket at index j with key k '''
        return NotImplementedError()

    def __iter__(self):
        ''' Iterator over keys '''
        return NotImplementedError()
    