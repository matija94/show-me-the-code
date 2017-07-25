import os

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

if __name__ == '__main__':
    #test ruler drawer
    rd = RulerDrawer(1,3)
    rd.draw()
    
    #test recursive binary search
    print(rbs([1,2,3,4,5], 5))
    
    #test disk_usage
    disk_usage('/home/matija/Desktop/productImageHierarchy')
