from linked_lists.double_linked import PositionalList


#P-7.44
class TextEditor:
    ''' simple text editor '''
    
    class _Cursor:
        def __init__(self):
            self._pos = None
            self._index = 0
    
    def __init__(self):
        self._data = PositionalList()
        self._cursor = self._Cursor()
        
        
    def _update_cursor(self,pos,index):
        self._cursor._pos = pos
        self._cursor._index = index
    
    def insert(self,c):
        if self._data.is_empty():
            self._cursor._pos = self._data.add_first(c)
        else:
            pos = self._data.add_after(c, self._cursor._pos)
            self._update_cursor(pos, self._cursor._index+1)
            
    def left(self):
        if self._cursor._index == 0:
            return
        pos = self._data.before(self._cursor._pos)
        self._update_cursor(pos, self._cursor._index-1)
    
    def right(self):
        if self._cursor._index == (len(self._data)-1):
            return
        pos = self._data.after(self._cursor._pos)
        self._update_cursor(pos, self._cursor._index+1)
        
    def delete(self):
        if self._cursor._index == (len(self._data)-1):
            return
        self._data.delete(self._data.after(self._cursor._pos))
    
    def __str__(self):
        l = str(self._data)
        under_line = [' '] * len(l)
        under_line[self._cursor._index] = '_'
        ans = '\n'.join((l,''.join(under_line)))
        return ans
    
if __name__ == '__main__':
    te = TextEditor()
    te.insert('m')
    te.insert('a')
    te.insert('t')
    te.insert('i')
    te.insert('j')
    te.insert('a')
    print(te)
    
    te.left()
    te.delete()
    print(te)        