from stack_queue_dequeue.queue import ArrayQueue
import re
class Empty(Exception):
    pass

class Full(Exception):
    pass

#C-6.16 exercise
#C-6.17 exercise
#C-6.18 exercise 
#C-6.19 exercise
class ArrayStack:
    
    def __init__(self, maxlen=None):
        if maxlen is None: 
            self._data = [] 
        else: 
            self._data = [None] * maxlen
        self._size = 0
        self._maxlen = maxlen
        
    def push(self, val):
        '''
        Puts element on top of the stack
        '''
        
        if self._maxlen==self._size:
            raise Full('stack is full')
        if self._maxlen is not None:
            self._data[self._size] = val
        else:
            self._data.append(val)
        self._size+=1
        
    def pop(self):
        '''
         Removes and retrieves element from top of the stack
        
        Raises error if stack is empty
        '''
        if self.is_empty():
            raise Empty('Stack is empty')
        if self._maxlen is not None:
            current_last_index = self._size-1
            ret = self._data[current_last_index]
            self._data[current_last_index] = None
        else:
            ret = self._data.pop()
        
        self._size-=1
        return ret
    
    def top(self):
        '''
        'Inspects' the top element.
        Retrieves element from top of the stack, without element being removed.
        
        Raises error if stack is empty
        '''
        if self.is_empty():
            raise Empty('Stack is empty')
        return self._data[-1]

    def reverse(self):
        '''
        Reverses the stack using two temporary stacks.
        This could be done using less space but exercises asks for this kind of implementation
        '''
        
        s1 = ArrayStack(len(self))
        s2 = ArrayStack(len(self))
        
        while not self.is_empty():
            s1.push(self.pop())
        while not s1.is_empty():
            s2.push(s1.pop())
        while not s2.is_empty():
            self.push(s2.pop())

    def __len__(self):
        return self._size
    
    def is_empty(self):
        return len(self)==0
    
    def __str__(self):
        return ' '.join([str(self._data[-i]) for i in range(1,len(self)+1)])

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
                #ignore tag attributes if any
                end = 0
                while end<len(tag) and tag[end] != ' ':
                    end+=1
                stack.push(tag[0:end])
            k = html.find('<',j+1)
        return stack.is_empty()
    
    #R-6.3 exercise
    @staticmethod
    def transfer(S,T):
        '''
        Transfers from stack S to stack S
        This will cause elements to be reversed in the stack T
        '''
        while not S.is_empty():
            T.push(S.pop())
        return T
    
    #R-6.4 exercise
    @staticmethod
    def clean(S,recursive=True):
        '''
        Cleans the stack, uses recursion to complete the task by default
        '''
        def _clean(S):
            if S.is_empty():
                return
            else:
                S.pop()
            _clean(S)
        _clean(S)
    
    @staticmethod
    def exercise_C_6_23(R,S,T):
        '''
         store all elements from T to S in their original order
         S should have those elements from T below it's current elements
         R should stay the same as it was on the input
        '''
        n = len(T)
        while not T.is_empty():
            R.push(T.pop())
        while not S.is_empty():
            T.push(S.pop())
        for i in range(0,n):
            S.push(R.pop())
        while not T.is_empty():
            S.push(T.pop())
    
    #C-6.22 exercise        
    @staticmethod
    def postfix(expr):
        if not StackUsage.parentheses_test(expr):
            raise ValueError('Mismatched parenthesis')
        res = []
        ''' Generate postfix notation'''
        operator_s = ArrayStack()
        items = expr.split(' ')
        '''
        TODO normalize expression such that each token (i.e operand, operator, parentheses is split by space)
        '''
        for ch in items:
            if ch == ')':
                if operator_s.top() == '(':
                    operator_s.pop()
                else:
                    while not operator_s.is_empty() and operator_s.top() != '(':
                        res.append(operator_s.pop())
                    if not operator_s.is_empty():
                        operator_s.pop()
            elif ch in '+-*/':
                while not operator_s.is_empty() and operator_s.top() != '(':
                    if operator_s.top() in '+-' and ch in '*/':
                        break
                    res.append(operator_s.pop())
                operator_s.push(ch)
            elif ch != '(':
                res.append(ch)
            else:
                operator_s.push(ch)
        while not operator_s.is_empty():
            res.append(operator_s.pop())
        return ' '.join(res)
    
    #P-6.34 exercise
    @staticmethod
    def postfix_eval(postfix_notation):
        items = postfix_notation.split(' ')
        s = ArrayStack()
        for item in items:
            if item in '+-*/':
                op2 = s.pop()
                op1 = s.pop()
                if item == '+':
                    s.push(op1+op2)
                if item == '-':
                    s.push(op1-op2)
                if item == '*':
                    s.push(op1*op2)
                if item == '/':
                    s.push(op1/op2)
            else:
                s.push(float(item))
        return s.pop()
    
    @staticmethod
    def calculator(expr):
        postfix = StackUsage.postfix(expr)
        print('Going to evaluate postfix notation: {}'.format(postfix))
        return StackUsage.postfix_eval(postfix)
    
if __name__ == '__main__':
    print(StackUsage.calculator('1 + 3 / 3 * 2 - 1 * 14 / 7 + 2'))
    print(StackUsage.parentheses_test('( ( a - b ) + ( ( ( c / d ) ) * r ) + ( ( ( (  1 / 2  ) ) * 3 ) / 4 ) - 5 )'))
    StackUsage.reverse_file('/home/matija/Desktop/test_reverse', True)
    
    print(StackUsage.valid_html("<html><head>This is head</head><body><h1 class='first_heading'>This is heading</h1><p style='color:red'>This is paragraph</p></body></html>"))                
    
    s = ArrayStack(10)
    for i in range(10):
        s.push(i+1)
    print(s)
    s.reverse()
    print(s)

    R = ArrayStack()
    S = ArrayStack()
    T = ArrayStack()
    
    for i in range(1,4):
        R.push(i)
    for i in range(7,10):
        S.push(i)
    for i in range(4,7):
        T.push(i)
    print(R)
    print(S)
    print(T)
    StackUsage.exercise_C_6_23(R, S, T)
    print(R)
    print(S)
