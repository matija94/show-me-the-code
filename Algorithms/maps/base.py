from _collections_abc import MutableMapping
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