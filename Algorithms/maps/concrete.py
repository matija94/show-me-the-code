from maps.base import MapBase, HashMapBase
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
                
                