class ArrayQueue:
    '''
    Queue implementation.
    The implementation for the queue uses an underlying array to store the data
    
    Default capacity is 10
    '''
    
    DEFAULT_CAP = 10 # DEFAULT CAPACITY OF THE QUEUE
    
    def __init__(self):
        self._data = [None] * ArrayQueue.DEFAULT_CAP
        self._size = 0
        self._front = 0
        
    def is_empty(self):
        return self._size == 0
    
    def __len__(self):
        return self._size
    
    def _resize(self, cap):
        '''
        Private function to resize underlying array which is used to store data in the queue
        '''
        b = [None] * cap
        for i in range(self._size):
            b[i] = self._data[self._front]
            self._front = (self._front+1) % self._size
        self._data = b
        self._front = 0
    
    def enqueue(self, val):
        '''
        Appends element to the end of the queue
        '''
        if self._size == len(self._data):
            self._resize(self._size*2)
        avail = (self._front + self._size) % len(self._data)
        self._data[avail] = val
        self._size+=1
        
    def dequeue(self):
        '''
        Removes and retrieves head of the queue
        
        Raises error if queue is empty
        '''
        if self._size == 0:
            raise ValueError('Queue is empty!')
        ret = self._data[self._front]
        self._data[self._front] = None
        self._front = (self._front+1)%len(self._data)
        self._size-=1
        if 0 < self._size < len(self._data)//4:
            self._resize(len(self._data)//2)
        return ret
    
    def first(self):
        '''
        Inspects first element in queue.
        Does not remove the head of the queue
        '''
        if self._size == 0:
            raise ValueError('Queue is empty!')
        return self._data[self._front]
    
    
    
    
    
