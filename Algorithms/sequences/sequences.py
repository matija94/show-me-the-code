import ctypes

class DynamicArray:
    
    def __init__(self):
        self._n = 0
        self._capacity = 1
        self._A = self._make_array(self._capacity)
        
    def _make_array(self,cap):
        return (ctypes.py_object * cap)()
    
    def __len__(self):
        return self._n
    
    def __getitem__(self, k):
        if not 0 <= k < self._n:
            raise IndexError('invalid index')
        return self._A[k]
    
    def append(self,obj):
        '''
         Appends obj at the end of the list
         Worst case is O(n) because of array resizing, but overall amortized complexity time is O(1)
        '''
        if self._n == self._capacity:
            self._resize(2* self._capacity)
        self._A[self._n] = obj
        self._n+=1
    
    def insert(self,k,val):
        '''
         Inserts value at the kth index position
         Because the function will shift right all values from indexes(k,n] time complexity is O(n-k)
        '''
        if not 0 <= k < self._n:
            raise IndexError('invalid index')
        if self._n == self._capacity:
            self._resize(2*self._capacity)
        self._shift_right(k)
        self._A[k] = val
        self._n+=1
        
    def _shift_right(self, k):
        for i in range(self._n,k,-1):
            self._A[i] = self._A[i-1]
    
    def _shift_left(self,k):
        for i in range(k+1,self._n):
            self._A[i-1] = self._A[i]
            
        self._A[self._n-1] = None
        
    def remove(self, value):
        '''
            Removes first occurrence of specified value.
            Raises ValueError if not found
            
            This function will shift all elements after removed one to the left so there will not be gap
            Therefore function always takes O(n) time ( until it hits the element, it it does then needs to iterate n - k to shift)
            If the element is not found function will run in O(n) as well of course
        '''
        for i in range(self._n):
            if self._A[i] == value:
                self._shift_left(i)
                self._n-=1
                return
        raise ValueError(str(value) + " doesn't exist")
        
    def _resize(self, cap):
        b = self._make_array(cap)
        for i in range(self._n):
            b[i] = self._A[i]
        self._A = b
        self._capacity = cap
        
    def __str__(self):
        ret = [self._A._objects[str(i)] for i in range(self._n)]
        return str(ret)

if __name__ == '__main__':
    arr = DynamicArray()
    for i in range(5):
        arr.append(i+1)
    arr.insert(3, 15)
    print(arr)
    arr.remove(15)
    print(arr)