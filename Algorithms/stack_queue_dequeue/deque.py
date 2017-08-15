#P.6-32 exercise
class ArrayDequeue:
    '''
    Complete implementation of deque structure, using array.
    We could extended ArrayQueue class and use it's already defined functions instead of rewriting them under different name, but the idea of the exercise was to give the whole impl
    '''
    DEFAULT_SIZE = 10
        
    def __init__(self):
        self._front = 0
        self._size = 0
        self._data = [None] * ArrayDequeue.DEFAULT_SIZE
        
    def _resize(self, cap):
        b = [None] * cap
        for i in range(self._size):
            b[i] = self._data[self._front]
            self._front = (self._front+1)%self._size
        self._front = 0
        self._data = b
        
    def __len__(self):
        return self._size
    
    def _last(self):
        if self.is_empty():
            raise ValueError('empty')
        last_index = (self._front+self._size-1)%len(self._data)
        return (last_index, self._data[last_index])

    def __str__(self):
        index = self._front
        res = []
        for i in range(self._size):
            res.append(str(self._data[index]))
            index = (index+1)%len(self._data)
        return ' '.join(res)
        
    def is_empty(self):
        ''' tells wether queue is empty '''
        return self._size == 0
        
    def add_last(self, val):
        ''' adds element to the tail of the queue
            This function is the same as the origin enqueue of the standard queue class
        '''
        if self._size == len(self._data):
            self._resize(self._size*2)
        avail = (self._front + self._size) % len(self._data)
        self._data[avail] = val
        self._size+=1
    
    def add_first(self,val):
        ''' adds element to the front of the queue '''
        if self._size == len(self._data):
            self._resize(self._size*2)
        self._front = (self._front-1)%len(self._data)
        self._data[self._front] = val
        self._size+=1
    
    def delete_last(self):
        ''' removes and retrieves last element in the queue'''
        last_index, last = self._last()
        self._data[last_index] = None
        self._size-=1
        if self._size < (len(self._data)//4):
            self._resize(len(self._data)//2)
        return last
    
    def delete_first(self):
        ''' removes and retrieves first element in the queue '''
        ret = self.first()
        self._data[self._front] = None
        self._front = (self._front+1)%len(self._data)
        self._size-=1
        if self._size < (len(self._data)//4):
            self._resize(self._size//2)
        return ret
    
    def first(self):
        '''inspects first element in the queue'''
        if self.is_empty():
            raise ValueError('empty')
        return self._data[self._front]
    
    def last(self):
        ''' inspects last element in the queue '''
        return self._last()[1]
    
    
if __name__ == '__main__':
    '''TEST'''
    deq = ArrayDequeue()
    deq.add_last(5)
    print(deq)
    
    deq.add_first(3)
    print(deq)
    
    deq.add_first(7)
    print(deq)
    
    print(deq.first())
    
    print(deq.delete_last())
    print(deq)
    
    print(len(deq))
    
    print(deq.delete_last())
    print(deq)
    print(deq.delete_last())
    print(deq)
    
    deq.add_first(6)
    print(deq)
    
    print(deq.last())
    
    deq.add_first(8)
    print(deq)
    
    print(deq.is_empty())
    print(deq.last())