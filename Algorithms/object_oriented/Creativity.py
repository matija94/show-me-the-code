'''
Created on Jul 22, 2017

@author: matija
'''

from object_oriented.base import Sequence as seq
from collections import Iterable

#C-2.25 impl
class Vector:
    
    def __init__(self, d):
        if isinstance(d, int):
            self._coords = [0] * d
        else:
            try:
                self._coords = [x for x in d]
            except TypeError:
                raise TypeError('invalid parameter type')
            
    
    def __len__(self):
        return len(self._coords)
    
    def __add__(self, v):
        res = Vector(self._coords)
        if isinstance(v, Vector):
            if len(res) != len(v):
                raise ArithmeticError('vector dimensions must agree')
            res._coords = [x+y for x,y in zip(res._coords, v._coords)]
        elif isinstance(v, int):
            res._coords = [x+v for x in res._coords]
        else:
            raise TypeError('invalid parameter type')
        
        return res
    
    def __mul__(self, v):
        res = Vector(self._coords)
        
        if isinstance(v, Vector):
            if len(res) != len(v):
                raise ArithmeticError('vector dimensions must agree')
            res._coords = [x*y for x,y in zip(res._coords, v._coords)]
        elif isinstance(v, int):
            res._coords = [x*v for x in res._coords]
        else:
            raise TypeError('invalid parameter type')
    
        return res
    
    def __getitem__(self, i):
        if i < 0:
            i = len(self)+i
        
        if not 0 <= i and i < len(self):
            raise IndexError('index out of bounds') 

        return self._coords[i]
    
    def __str__(self):
        return str(self._coords)

class reversedSeq(seq):
    '''
        Implemented as iterator instead of generator
        Takes iterable as the constructor argument so we can reverse iterate string for example
    '''
    def __init__(self, source):
        if not isinstance(source, Iterable):
            raise TypeError('invalid parameter type')

        self._iter = source
        
    def __len__(self):
        return len(self._iter)

    def __getitem__(self, j):
        j = len(self._iter) - j - 1
        
        if not 0 <= j and j < len(self):
            raise StopIteration
        
        return self._iter[j]
        
if __name__ == '__main__':
    #test C-2.25
    v1 = Vector([1,2,3])
    print(v1[0:3])
    print(v1[-1])
    v2 = Vector([3,3,3])
    print(v1+v2)
    print(v1+5)
    print(v1*2)
    print(v1*v2)
    
    
    
    #test C-2.26
    for j in reversedSeq([1,2,3,4,5]):
        print(j)
    
    rseq = iter(reversedSeq('matija'))
    while True:
        try:
            print(next(rseq))
        except StopIteration:
            print('No more values')
            break
    
