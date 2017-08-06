import os
import time
from macpath import abspath

def factoriel(n):
    if n == 0:
        return 1
    return n*factoriel(n-1)

class RulerDrawer:
    
    def __init__(self,inches, ticks_lenght):
        self.inches = inches
        self.ticks = ticks_lenght
        
    def draw(self):
        self._draw_line(self.ticks, '0')
        for j in range(1, self.inches+1):
            self._draw_interval(self.ticks-1)
            self._draw_line(self.ticks, str(j))
            
    def _draw_line(self, ticks, inch=''):
        line = '-' * ticks
        if inch:
            line = inch + ' ' + line
        print(line)
        
    def _draw_interval(self,central_ticks):
        print('hit ' + str(central_ticks))
        if central_ticks > 0:
            self._draw_interval(central_ticks-1)
            self._draw_line(central_ticks)
            self._draw_interval(central_ticks-1)
            


def rbs(arr, target):
    '''
        Implementation of recursive binary search which running time is O(log base 2 n)
    '''
    return _rbs(arr,target,0,len(arr)-1)

def _rbs(arr,target,lo,hi):
    if lo>hi:
        return -1
    mid = lo + (hi-lo)//2 # calc median this way to not exceed max int range in case of lo and hi are big numbers
    if arr[mid] == target : return mid
    elif arr[mid] < target: return _rbs(arr,target,mid+1,hi)
    elif arr[mid] > target: return _rbs(arr,target,lo,mid-1)
    
    
    
def disk_usage(path):
    '''
        Calculates total disk usage of file recursively(if it is a directory and has any childs)
        Otherwise just returns the size of the single file
    '''
    total = os.path.getsize(path)
    
    if os.path.isdir(path):
        for filename in os.listdir(path):
            abspath = os.path.join(path, filename)
            total = total + disk_usage(abspath)
    
    print('%d\t%s' %(total, path))
    return total


#bad recursion - solves problem of unique elements(distinct) in exponential time(2^n)-1  
def unique3(S,start,stop):
    if stop-start <= 1: return True
    elif not unique3(S, start, stop-1): return False
    elif not unique3(S, start+1, stop): return False
    print('comapre ' + str(start) + ' ' + str(stop-1) )
    return S[start] != S[stop-1]
    
#C-4.11    
def uniqueQuadratic(S):
    '''
        Unique elements implemented in O(n^2) time
        using recursion instead of iteration
    '''
    def _uniqueQuadratic(S,k,j):
        if j >= len(S):
            return True     
        if j == len(S)-1 and k<len(S)-2:
            if not _uniqueQuadratic(S, k+1, k+2): return False
            
        if j<len(S)-1 and not _uniqueQuadratic(S, k, j+1): return False
        return S[k] != S[j]
    return _uniqueQuadratic(S, 0, 1)
    


#C-4.12
def product(m,n):
    '''
     Finds the product of m and n
     Runs in O(min(m,n))
    '''
    def _helper(m,n):
        if (n==1):
            return m
        return m + _helper(m,n-1)
    if m>n:
        return _helper(m,n)
    else:
        return _helper(n,m)

def reverse(S,start,stop):
    '''
        Reverses elements in the list so that a[0] becomes a[n-1] a[1] becomes a[n-1-1] etc
        There are 1 + n/2 calls to the function
    '''
    if start < stop-1:
        S[start], S[stop-1] = S[stop-1], S[start]
        reverse(S, start+1, stop-1)

def binary_sum(S,start,stop):
    '''
        O(log2n) space complexity
        O(n) time complexity
    '''
    if start>=stop==0:
        return 0
    
    if start==stop-1:
        return S[start]
        
    mid = (start+stop)//2
    return binary_sum(S, start, mid) + binary_sum(S, mid, stop)
    
#C-49
def min_max(S,start):
    '''
        Find min and max of the list in O(n) time
    '''
    def _min_max(n1,n2):
        if isinstance(n2, tuple):
            n2 = n2 + (n1,)
            return (min(n2), max(n2))
        return (min(n1,n2), max(n1,n2))
    if start==len(S):
        return S[start-1]
    return _min_max(S[start], min_max(S, start+1))

        
        
def faster_sum(S,start,end):
    '''
        *** IS ITERATIVE ***
        uses O(1) space
        uses O(n/2) time
    ''' 
    res = 0
    while(True):
        if start-end==1:
            return res + S[start] + S[end]
        elif start==end:
            return res + S[start]
        res += S[start] + S[end]
        start+=1
        end-=1
#C-4.17
def isPalindrome(S):
    '''
        Checks if given string is palindrome
    '''
    if len(S) % 2 == 0: return False
    def _isPalindrome(S,start,end):
        if start==end:
            return True
        if S[start] != S[end]:
            return False
        return _isPalindrome(S,start+1,end-1)
    return _isPalindrome(S, 0, len(S)-1)

#C-4.19
def even_before_odds(S):
    '''
     Reverses list such that for every index of even number any index of odd_number is greater
     This function will not sort numbers
     
     Example
      input : [3,4,11,1,12,7,14,8]
      output : [8,4,14,12,1,7,11,3]
    '''
    def _helper(S,k,j):
        if j > k:
            if S[k] % 2 == 1 and S[j] % 2 == 0:
                S[k],S[j] = S[j], S[k]
                _helper(S,k+1,j-1)
            elif S[k] % 2 == 1:
                _helper(S,k,j-1)
            elif S[j] % 2 == 0:
                _helper(S,k+1,j)
                
    _helper(S, 0, len(S)-1)
#P-4.27
def walk(dirr):
    '''
     Non generator impl of os.walk function
    '''
    dirpath = []
    subdirs = []
    files = []
    def _walk(dirr):
        dirpath.append(dirr)
        for subdir in os.listdir(dirr):
            abspath = os.path.join(dirr, subdir)
            if os.path.isdir(abspath):
                subdirs.append(subdir)
                _walk(abspath)
            else:
                files.append(subdir)
        
    _walk(dirr)
    return dirpath, subdirs, files
 
#P-4.23 
def listfiles(dirr, target_file_name=None):
    '''
     Generates all names of files under the specified directory
     If target_file_name is supplied function will generate all absolute paths of files which names are same as target argument
    '''
    for subdir in os.listdir(dirr):
        abs = os.path.join(dirr,subdir)
        if os.path.isdir(abs):
            for f in listfiles(abs, target_file_name):
                yield f
        elif target_file_name == subdir:
            yield abs
        elif target_file_name == None:
            yield abs


def slower_power(a,b):
    if b==0:
        return 1
    return a*slower_power(a,b-1)

def faster_power(a,b):
    if b == 1:
        return a
    part = faster_power(a,b//2)
    res = part*part
    if b%2 == 1:
        res = res * a
    return res

       
if __name__ == '__main__':
    #test ruler drawer
    #rd = RulerDrawer(1,3)
    #rd.draw()
    t = time.time()
    print(slower_power(2,560))
    print(str(time.time()-t))
    
    t = time.time()
    print(faster_power(2, 1024))
    print(str(time.time()-t))
    #test recursive binary search
    #print(rbs([1,2,3,4], 5))
 
    #test reverse list
    #S = [1,2,3,4,5]
    #reverse(S, 0, 5)
    #print(S)
        
    #test disk_usage
    #isk_usage('/home/matija/Desktop/productImageHierarchy')
    
    #print(binary_sum([1,2,3,4,5], 0, 5))
    #print(faster_sum([1,2,3,4,5], 0, 4))
    
    #print(min_max([11,1,2,4,5,6], 0))
    
    #print(uniqueQuadratic([1,2,3,4,5,6,6]))
    
    #print(product(6, 10))
    #print(isPalindrome('racecar'))
    #dirpath, subdirs, files = walk('/home/matija/Desktop/walk_test')
    #print(dirpath)
    #print(subdirs)
    #print(files)
    
    #i = 0
    #for files in listfiles('/home/matija/Desktop', 'f1'):
        #if (i==25): break
        #print(files)
        #i = i+1
        
    #S = [4,3,5,2,7,6,10]
    #even_before_odds(S)
    #print(S)
    print(product(2, 100000000))
    
    
    
    for i in range(5,1,-1):
        print(i)