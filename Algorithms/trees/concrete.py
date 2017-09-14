from trees.base import BinaryTree

class LinkedBinaryTree(BinaryTree):
    ''' Linked representation of a binary tree '''
    
    class _Node:
        __slots__='_element', '_parent', '_left', '_right'
        def __init__(self, element, parent=None, left=None, right=None):
            self._element = element
            self._parent = parent
            self._left = left
            self._right = right
            
    class Position(BinaryTree.Position):
        ''' an abstraction representing the location of single element '''
        
        def __init__(self, container, node):
            self._container = container
            self._node = node
            
        def element(self):
            return self._node._element
        
        def __eq__(self, other):
            return type(self) is type(other) and self._node is other._node

        def __ne__(self, other):
            return not (self == other)
        
    ''' #### private functions #### '''
    def _validate(self, p):
        ''' return associated node with Position p if p is valid '''
        if not isinstance(p, self.Position):
            raise TypeError('p must be proper Position type')
        if p._container is not self:
            raise ValueError('p does not belong to this container')
        if p._node._parent is p._node: # convention for deprecated nodes
            raise ValueError('p is no longer valid')
        return p._node
    
    def _make_position(self, node):
        ''' Returns Position instance for given node ( or None if no node ) '''  
        if node is not None:
            return self.Position(self,node)
        else:
            return None
        
    def __init__(self):
        self._root = None
        self._size = 0
    
    ''' #### public functions #### '''
    def __len__(self):
        return self._size
    
    def root(self):
        return self._make_position(self._root)
    
    def parent(self, p):
        node = self._validate(p)
        return self._make_position(node._parent)
    
    def left(self, p):
        node = self._validate(p)
        return self._make_position(node._left)
    
    def right(self, p):
        node = self._validate(p)
        return self._make_position(node._right)
    
    def num_children(self, p):
        node = self._validate(p)
        ans = 0
        if node._left is not None:
            ans+=1
        if node._right is not None:
            ans+=1
        return ans
    
    def add_root(self,e):
        ''' Returns Position for newly created root holding e (Raises ValueError if root already exists) '''
        if self._root is not None:
            raise ValueError('root already exists')
        self._size+=1
        self._root = self._Node(e)
        return self._make_position(self._root)
    
    def add_left(self,p,e):
        ''' Adds new node holding e as left child of node referred by Position p
            
            Returns new Position
            Raises ValueError if node referred by Position p already has left child or if Position p is invalid
        '''
        node = self._validate(p)
        if node._left is not None:
            raise ValueError('Node referred by Position already has left child')
        new_node = self._Node(e, node, left=None, right=None)
        node._left = new_node
        self._size+=1
        return self._make_position(new_node)
    
    def add_right(self,p,e):
        ''' Adds new node holding e as right child of node referred by Position p
            
            Returns new Position
            Raises ValueError if node referred by Position p already has right child or if Position p is invalid
        '''
        node = self._validate(p)
        if node._right is not None:
            raise ValueError('Node referred by Position already has right child')
        new_node = self._Node(e,node,left=None,right=None)
        node._right = new_node
        self._size+=1
        return self._make_position(new_node)
    
    def replace(self,p,e):
        ''' Replaces the element at position p
        
            Returns replaced element
            Raises ValueError if p is invalid
        '''
        node = self._validate(p)
        old = node._element
        node._element = e
        return old
    
    def delete(self, p):
        ''' Delete the node at Position p, and replace it with its child, if any.
        
            Return the element that had been stored at Position p.
            Raise ValueError if Position p is invalid or p has two children.
        '''
        node = self._validate(p)
        if node._right is not None:
            if node._left is not None:
                raise ValueError('Node referred by Position p has two children')
            else:
                old = node._element
                node._element = node._right._element
                node._right._parent = node._right = None
                self._size-=1
                return old
        else:
            if node._left is not None:
                old = node._element
                node._element = node._left._element
                node._left._parent = node._left = None
                self._size-=1
                return old
            else:
                raise ValueError('Node referred by Position p does not have children')
            
    def attach(self,p,t1,t2):
        ''' Attach trees t1 and t2 as left and right subtrees of external p 
            
            Raises ValueError if node referred by Position p is not external(leaf) or if any of the argument trees is empty
            Raises TypeError if t1 and t2 are not of same type as this tree 
        '''
        node = self._validate(p)
        if not self.is_leaf(p):
            raise ValueError('Node referred by Position p is internal node')
        if not (type(self) is type(t1) is type(t2)):
            raise TypeError('Tree t1 and Tree t2 must be of same type as this Tree')
        
        if not t1.is_empty() and not t2.is_empty():
            t1._root._parent = node
            node._left = t1._root
            t1._root = None
            t1._size = 0
            t2._root._parent = node
            node._right = t2._root
            t2._root = None
            t2._size = 0
        else:
            raise ValueError('Both t1 and t2 must be non empty Trees')
        
class ExpressionTree(LinkedBinaryTree):
    ''' arithmetic expression tree '''
    
    def __init__(self, token, left=None, right=None):
        
        super().__init__()
        if not isinstance(token, str):
            raise TypeError('token must be string')
        self.add_root(token)
        if left is not None:
            if token not in '*/+-':
                raise ValueError('token must be valid operator')
            self.attach(self.root(), left, right)
            
    def __str__(self):
        pieces = []
        self._parenthesize_recur(self.root(), pieces)
        return ''.join(pieces)
    
    def _parenthesize_recur(self, pos, result):
        if self.is_leaf(pos):
            result.append(pos.element()) # operand
        else:
            result.append('(')
            self._parenthesize_recur(self.left(pos), result)
            result.append(pos.element()) # operator
            self._parenthesize_recur(self.right(pos), result)
            result.append(')')
    
    def evaluate(self):
        ''' return numeric result of the expression '''
        return self._evaluate_recur(self.root())
    
    def _evaluate_recur(self, pos):
        if self.is_leaf(pos):
            return pos.element()
        else:
            operand = pos.element()
            left_val = self._evaluate_recur(self.left(pos))
            right_val = self._evaluate_recur(self.right(pos))
            if operand == '*': return int(left_val)*int(right_val)
            elif operand == '/': return int(left_val)/int(right_val)
            elif operand == '+': return int(left_val)+int(right_val)
            elif operand == '-': return int(left_val)-int(right_val)
            else: return None # will never happen
    
    @staticmethod
    def build_expression_tree(expr):
        S = []
        multidigit = []
        for i,e in enumerate(expr):
            if e == ')':
                expr2 = S.pop()
                op = S.pop()
                expr1 = S.pop()
                t = ExpressionTree(op, expr1, expr2)
                S.append(t)
            elif e in '+-*/':
                S.append(e)
            elif e in '0123456789':
                multidigit.append(e)
                if i<len(expr)-1 and expr[i+1] in '0123456789':
                    continue # it is multidigit
                number = ''.join(multidigit)
                t = ExpressionTree(number)
                S.append(t)
                multidigit.clear()
                    
        return S.pop()
if __name__ == '__main__':
    t = ExpressionTree.build_expression_tree('(((3+2)+(44+1))/10)')
    print(t.evaluate())
    