

def find_brute(T,P):
    '''
    returns lowest index of T at which P begins (or else -1)
    '''
    
    n,m = len(T), len(P)
    for i in range(n-m+1):
        k = 0
        while k < m and T[i+k] == P[k]:
            k+=1
        if k == m:
            return i
    return -1


def find_boyer_moore(T,P):
    '''
    alings pattern with text by matching up letter from text to last appearance of it in pattern and aligning. If there is no match for the letter in the pattern then
    len(pattern) letters are skipped in text. This is improved technique over brute force
    '''
    n, m = len(T), len(P)
    if m == 0: return 0
    last = {}
    for k in range(len(P)):
        last[P[k]] = k
    i = m-1 # align index for T
    k = m-1 # align index for P
    while i < n:
        if T[i] == P[k]:
            if k == 0:
                return i
            else:
                i-=1
                k-=1
        else:
            j = last.get(T[i], -1)
            i += m - min(k,j+1)
            k = m-1
    return -1
    

def find_kmp(T,P):
    '''
    '''
    n ,m = len(T), len(P)
    if m == 0: return 0
    fail = compute_kmp_fail(P)
    j = 0
    k = 0
    while j < n:
        if T[j] == P[k]:
            if k == m-1:
                return j-m+1
            k+=1
            j+=1
        elif k > 0:
            k = fail[k-1]
        else:
            j+=1
    return -1
    

def compute_kmp_fail(P):
    m = len(P)
    fail = [0] * m
    j = 1
    k = 0
    while j < m:
        if P[j] == P[k]:
            fail[j] = k + 1
            j+=1
            k+=1
        elif k > 0:
            k = fail[k-1]
        else:
            j+=1
    return fail
    
    
if __name__ == '__main__':
    i = find_kmp('matiramati...', 'matiramatilu')
    print(i)
    