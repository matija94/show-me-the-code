class __Bucket:
    def __init__(self,size):
        self._table = [None] * size
        
    def __getitem__(self,key):
        return self._table[key]   
    
    def __setitem__(self,key,value):
        seq = self._table[key]
        if seq is None:
            seq = []
            self._table[key] = seq
        seq.append(value)
    
    def __iter__(self):
        for seq in self._table:
            if seq is not None:
                for e in seq:
                    yield e


class __Entry:
    def __init__(self,key,val):
        self._key=key
        self._val=val    

def bucksetsort(S):
    '''
    This is specific sorting method which has O(n) time complexity. Therefore it is appliable only on sequences whose elements have integer keys which are in range (0,N-1)
    '''
    bucket = __Bucket(len(S))
    for e in S:
        #assume e is just integer, usually it would be entry(key-value pair)
        k = e._key
        v = e._val
        bucket[k] = v
    
    del S[:]

    for e in bucket:
        S.append(e)
        

if __name__ == '__main__':
    S = [__Entry(1,'nicee'),__Entry(1,'hey'),__Entry(0,'awesome'),__Entry(2,'lucky')]
    bucksetsort(S)
    print(S)
    
    