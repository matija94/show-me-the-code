import ctypes
import random

class DynamicArray:
    
    def __init__(self, cap=1):
        self._n = 0
        self._capacity = cap
        self._A = self._make_array(self._capacity)
        
    def _make_array(self,cap):
        return (ctypes.py_object * cap)()
    
    def __len__(self):
        return self._n
    
    def __getitem__(self, k):
        if not 0 <= k < self._n:
            raise IndexError('invalid index')
        return self._A[k]
    
    def __setitem__(self,k,j):
        if not 0 <= k < self._capacity:
            raise IndexError('invalid index')
        if self._n <= k: # increment n (number of elements) if new element is added, instead of writing on index < current N
            self._n+=1
        self._A[k] = j

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

    #C-5.16 exercise
    def pop(self):
        '''
         Removes last element from the array and returns it to the caller
         In case current number of elements is less than capacity/4, array will be halved
        '''
        if self._n==0:
            raise ValueError('array is empty')
        ret = self._A[self._n-1]
        self._n-=1
        if self._n < self._capacity//4:
            self._resize(self._capacity//2)
        return ret
    
    #C-5.14 exercise    
    def shuffle(self, inplace=False):
        '''
         Shuffles array in randomized manner
         Uses O(n) time
         Uses O(n) space, more precisely 2*n space, n for visited set and n for shuffled set. 
        '''
        ret = self._make_array(self._n)
        r = random.Random()
        visited = [None] * self._n
        for i in range(self._n):
            while True:
                index = r.randrange(0,self._n)
                if index not in visited:
                    visited[i] = index
                    ret[i] = self._A[index]
                    break
        if inplace:
            self._A = ret
        else:
            arr = DynamicArray(self._n)
            for i in range(self._n):
                arr[i] = ret[i]
            return arr   
            
    def _resize(self, cap):
        b = self._make_array(cap)
        for i in range(self._n):
            b[i] = self._A[i]
        self._A = b
        self._capacity = cap
        
    def __str__(self):
        return '[' + ', '.join([str(self._A[i]) for i in range(self._n)]) + ']'

if __name__ == '__main__':
    arr = DynamicArray()
    for i in range(12):
        arr.append(i+1)
    arr.insert(3, 15)
    print(arr)
    arr.remove(15)
    print(arr)
    
    print(sum(arr))
    
    #R-5.12 exercise
    data_set = [[1] * 3 for i in range(3)]
    print( sum( [sum(data_set[i]) for i in range(3)] ) )
    
    #test shuffle impl
    shuffled = arr.shuffle()
    print(arr)
    print(shuffled)
    print(len(shuffled))
    
    #test pop impl
    current_cap = shuffled._capacity
    while True:
        shuffled.pop()
        print(shuffled)
        if shuffled._capacity != current_cap:
            print('Array resized! Old size: {0} Current number of elements: {1} New size: {2}'.format(current_cap, len(shuffled), shuffled._capacity))
            break
    