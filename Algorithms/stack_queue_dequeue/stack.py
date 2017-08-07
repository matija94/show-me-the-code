class Empty(Exception):
    pass

class ArrayStack:
    
    def __init__(self):
        self._data = []
        
    def push(self, val):
        '''
        Puts element on top of the stack
        '''
        self._data.append(val)
        
    def pop(self):
        '''
         Removes and retrieves element from top of the stack
        
        Raises error if stack is empty
        '''
        if self.is_empty():
            raise Empty('Stack is empty')
        return self._data.pop()
    
    def top(self):
        '''
        'Inspects' the top element.
        Retrieves element from top of the stack, without element being removed.
        
        Raises error if stack is empty
        '''
        if self.is_empty():
            raise Empty('Stack is empty')
        return self._data[-1]

    def __len__(self):
        return len(self._data)
    
    def is_empty(self):
        return len(self._data)==0


class StackUsage:
    
    @staticmethod
    def reverse_file(path, inplace=True):
        '''
            Reverses file such that first line becomes last line, second line becomes one before last etc..
            Using stack to implement reverse. While collecting lines from file and push to the stack new lines('\n') are being stripped out of line
            This is being done since we have to write new lines when writing back to the file. This is needed because usually '\n' is omitted from the last line in the file
        '''
        stack = ArrayStack()
        with open(path, 'r') as infile:
            for line in infile:
                stack.push(line.rstrip('\n'))   
        with open(path, 'w') as outfile:
            while not stack.is_empty():
                outfile.write(stack.pop()+'\n')
                
    @staticmethod            
    def parentheses_test(expression):
        '''
            Checks if given expression has matching parentheses
            '{([' and their right sides are supported.
            This functionality is implemented using stack 
        '''
        if len(expression) % 2 != 0:
            return False
        left = '([{'
        right = ')]}'
        stack = ArrayStack()
        for ch in expression:
            if ch in left:
                stack.push(ch)
            elif ch in right:
                if stack.is_empty():
                    return False
                if right.index(ch) != left.index(stack.pop()):
                    return False
        return stack.is_empty()
    
    @staticmethod
    def valid_html(html):
        '''
            Checks if the specified html is valid.
            Note that each <tag> needs to have it's closing </tag>.
            Cases like <br> are omitted from this implementation and therefore caller will get false as result
        '''
        stack = ArrayStack()
        k = html.find('<')
        while k != -1:
            j = html.find('>', k+1)
            if j == -1:
                return False # indicates html which doesn't have valid tags
            tag = html[k+1:j]
            if tag[0] == '/':
                if stack.is_empty():
                    return False # missing opening tag
                tag = tag[1:] # get proper name for closing tag
                if tag != stack.pop():
                    return False
            else:
                stack.push(tag)
            k = html.find('<',j+1)
        return stack.is_empty()
        
if __name__ == '__main__':
    StackUsage.reverse_file('/home/matija/Desktop/test_reverse', True)
    
    print(StackUsage.parentheses_test('(((({}[]))))'))
    
    print(StackUsage.valid_html("<html><head>This is head</head><body><h1>This is heading</h1><p>This is paragraph</p></body></html>"))                
