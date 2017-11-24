class PrintTable:
    '''
    prints N x M matrix 
    '''

    def print_table(self, L):
        rows = []
        height = len(L)
        width = len(L[0])
        for i in range(height+1):
            row = []
            for j in range(width+1):
                if i == 0 and j == 0:
                    row.append('  ')
                elif j == 0:
                    row.extend(['|',str(i-1)])
                elif i == 0:
                    row.extend(['|',str(j-1)])
                else:
                    row.extend(['|',str(L[i-1][j-1])])
            intra_row = ['_']*(width*2+1)
            rows.append(row)
            rows.append(intra_row)
        print('\n'.join([''.join(row) for row in rows]))

class FindMinInSortedRotatedSeq:
    
    '''
    A sorted array has been rotated so elements could appear for instance in order 4 5 6 7 8 1 2 3
    Find minimum element.
    '''
    
    def min(self, S):
        lo = 0
        hi = len(S)-1
        if hi == -1:
            raise ValueError('empty seq')
        elif hi == lo:
            return S[hi]
        return self.__min(lo,hi,S,[S[hi]])
    
    def __min(self,lo,hi,S,current_min):
        mid = (hi+lo)//2
        if hi <= lo:
            return current_min[0]
        elif S[mid] > S[hi]:
            current_min[0] = S[hi] if S[hi]<current_min[0] else current_min[0]
            return self.__min(mid+1, hi, S,current_min)
        elif S[mid] < S[hi]:
            current_min[0] = S[mid] if S[mid]<current_min[0] else current_min[0]
            return self.__min(lo, mid-1, S,current_min)



class StringPermutations:
    
    def perm(self,S):
        if len(S) == 0: 
            return
        res = [S[0]]
        return self.__perm(res, S,1)
    
    def __perm(self,res,S,i):
        perms = []
        if i==len(S):
            return res
        while len(res) > 0:
            word = res.pop()
            perms.extend(self.__add(word,S[i]))
        return self.__perm(perms,S,i+1)
        
    def __add(self,word,ch):
        perms = []
        orig = word
        for i in range(len(word)):
            perms.append(word[0:i] + ch + word[i:])
            word = orig
        perms.append(word+ch)
        return perms
    
if __name__ == '__main__':
    t = FindMinInSortedRotatedSeq()
    res = t.min([5,4,3,1,2])
    print(res)


    t = StringPermutations()
    print(t.perm('matija'))
    
    
    L = [[2,3],[1,4]]
    t = PrintTable()
    t.print_table(L)