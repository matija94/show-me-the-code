from cracking_the_code import solutions1
def matrix_chain(d):
    '''
    d is list of n+1 numbers such that size of kth  matrix is d[k]-by-d[k+1]
    
    Return n-by-n table such that N[i][j] represents minimum number of multiplications needed to compute the product of Ai through Aj
    '''
    
    n = len(d) - 1 # number of matrices
    N = [[0]*n for i in range(n)] # initialize n-by-n result to zero
    for b in range (1,n): # number of products in subchain
        for i in range(n-b): # start of subchain
            j = i + b # end of subchain
            N[i][j] = min(N[i][k] + N[i][k+1] + d[i]*d[k+1]*d[j+1] for k in range(i,j))
    return N

def memoized_fb(n):
    '''
    reuses solved fibonacci numbers already so reduces time complexity
    So only those non solved fibonacci numbers will cost us time and that is O(n).
    
    compute(n-1) is called each time until hitting <= 2
    compute(n-2) is already computed, thus it cost us O(1) -- to pull it out of solved dict  
    '''
    solved = {}
    def compute(n):
        if n in solved: return solved[n]
        elif n <= 2: f=1
        else: f = compute(n-1) + compute(n-2)
        solved[n] = f
        return f
    return compute(n)


def bottom_up_memoized_fb(n):
    '''
    it is the same algorithm as standard memoized_fb although it uses constant space( saved just previous two values), since it has no function calls inside
    '''
    minus_one = 0; minus_two = 0
    for k in range(1,n+1):
        if k<=2: f = 1
        else: f = minus_one + minus_two
        minus_one = f-1
        minus_two = f-2
    return f

def lcs(X,Y):
    '''
    longest common subsequence for X and Y
    
    return table such that L[j][k] is lenght of LCS for X[0:j] and Y[0:k]
    '''
    
    n, m = len(X), len(Y)
    L = [[0]*(m+1) for k in range(n+1)]
    for j in range(n):
        for k in range(m):
            if X[j] == Y[k]:
                L[j+1][k+1] = L[j][k] + 1
            else:
                L[j+1][k+1] = max(L[j][k+1], L[j+1][k])
    return L

def lcs_str(X,Y,L):
    ''' return the longest common substring of X and Y given LCS table L '''
    solution = []
    j = len(X)
    k = len(Y)
    while L[j][k] > 0:
        if X[j-1] == Y[k-1]:
            solution.append(X[j-1])
            j-=1
            k-=1
        elif L[j-1][k] >= L[j][k-1]:
            j-=1
        else:
            k-=1
    return ''.join(reversed(solution))
    
if __name__ == '__main__':
    L = lcs('mama','mana')
    p = solutions1.PrintTable()
    p.print_table(L)
    print(lcs_str('mama', 'mana', L))