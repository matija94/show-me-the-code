def insertsort_binary(L):
    '''
    If we consider comparison to be more expensive than simple swap
    then we could use binary search on sorted part of array to find index i for new key at k and then shifting all positions [i:k-1] to the right
    '''
    for i in range(1,len(L)):
        for j in range(i-1,-1,-1):
            if L[i] >= L[j]: break
            z = __find_index(L, 0, j, L[i])
            __insert(L, L[i], z, i)
    return L

def __find_index(L,lo,hi,t):
    if hi < lo: return lo
    mid = (lo + hi)//2
    if L[mid] > t: return __find_index(L, lo, mid-1, t)
    elif L[mid] < t: return __find_index(L, mid+1, hi, t)

def __insert(L,t,i,j):
    L[j] = None
    while L[i] is not None:
        temp = L[i]
        L[i] = t
        t = temp
        i+=1
    L[i] = t
    
if __name__ == '__main__':
    L = [3,4,1,5,2,6]
    insertsort_binary(L)
    print(L)
