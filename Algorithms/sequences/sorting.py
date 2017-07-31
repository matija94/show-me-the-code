import time

def insert_sort(A):
    '''
     Sort list of comparable elements in increasing order
     O(n^2) worst case - which is reversed sequence
    '''
    for i in range(1, len(A)):
        curr = A[i]
        j=i
        while j>0 and A[j-1] > curr:
            A[j] = A[j-1]
            j-=1
        A[j] = curr
    
if __name__ == '__main__':

    s = [x for x in range(10000,0,-1)]
    start = time.time()
    insert_sort(s)
    total = time.time() - start

    print("Sorting took {0} seconds".format(total))
    print(s)