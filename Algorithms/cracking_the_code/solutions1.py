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

class KaratsubaMultiplication:
    
    def product(self,a,b):
        if a<10 or b<10:
            return a*b
        a = str(a)
        b = str(b)
        part_a = a[:len(a)//2] #if len(a) > 1 else a
        part_b = a[len(a)//2:] #if len(a) > 1 else 0
        part_c = b[:len(b)//2] #if len(b) > 1 else b
        part_d = b[len(b)//2:] #if len(b) > 1 else 0
        ac = self.product(int(part_a), int(part_c))
        bd = self.product(int(part_b), int(part_d))
        ad_bc = self.product(int(part_a)+int(part_b), int(part_c)+int(part_d)) - bd - ac
        #if len(a) != len(b):
            #return 10**1*ac+10**(len(a)//2)*ad_bc+bd
        #else:
        return 10**len(a)*ac+10**(len(a)//2)*ad_bc+bd

class BinaryToDecimal:
    
    base=2
    
    def convert(self,binary_str):
        n = len(binary_str)
        value = 0
        for i in range(n-1,-1,-1):
            digit = int(binary_str[i])
            if digit == 1:
                exp = n-i-1
                value += BinaryToDecimal.base**exp
        return value



if __name__ == '__main__':
    t = FindMinInSortedRotatedSeq()
    res = t.min([5,4,3,1,2])
    print(res)


    t = StringPermutations()
    print(t.perm('matija'))
    
    
    L = [[2,3],[1,4]]
    t = PrintTable()
    t.print_table(L)
    
    t = KaratsubaMultiplication()
    print(t.product(12,12))

    bintodec = BinaryToDecimal()
    print(bintodec.convert('1010'))
