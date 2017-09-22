from linked_lists.linked_queue import LinkedQueue
from _collections import deque
class Tree:
    ''' base abstract class for all implementations of trees '''
    class Position:
        
        def element(self):
            raise NotImplementedError()
        
        def __eq__(self,o):
            raise NotImplementedError()
        
        def __ne__(self,o):
            raise NotImplementedError()
        
    
    def root(self):
        raise NotImplementedError()
    
    def parent(self,p):
        raise NotImplementedError()
    
    def num_children(self, p):
        raise NotImplementedError()
    
    def children(self, p):
        raise NotImplementedError()
    
    def __len__(self):
        raise NotImplementedError()
    
    def is_root(self,p):
        ''' returns true if p represents root of the tree '''
        return self.root() == p
    
    def is_leaf(self, p):
        ''' returns true if position doesn't have any children '''
        return self.num_children(p) == 0
    
    def is_empty(self):
        ''' returns true if tree is empty '''
        return len(self) == 0
    
    def depth(self,p):
        ''' computes depth of the position p in the tree '''
        if self.is_root(p):
            return 0
        else:
            return 1+self.depth(self.parent(p))
        
    def _height(self,p):
        if self.is_leaf(p):
            return 0
        else:
            return 1+max(self._height(c) for c in self.children(p))
        
    def height(self, p=None):
        ''' computes height of the position if set otherwise computes height of the tree'''
        if p is None:
            p = self.root()
        return self._height(p)
        
    def preorder(self):
        ''' Rather than visiting nodes this function just yields positions in the tree and lets the caller do the 'visiting'.
            This traversal goes into depth. It is one of three types of depth-first-search traversal algorithm
            Preorder goes in depth in such way that it yields root of the subtree before visiting children
        '''
        if not self.is_empty():
            for c in self._subtree_preorder(self.root()):
                yield c
    
    def _subtree_preorder(self,p):
        yield p
        for c in self.children(p):
            for other in self._subtree_preorder(c):
                yield other
    
    def postorder(self):
        ''' Rather than visiting nodes this function just yields positions in the tree and lets the caller do the 'visiting'.
            This traversal goes into depth. It is one of three types of depth-first-search traversal algorithm
            Postorder goes in depth in such way that it yields children of root in subtree first
        '''
        if not self.is_empty():
            for p in self._subtree_postorder(self.root()):
                yield p
    
    def breadthfirst(self):
        ''' Rather than visiting nodes this function just yields positions in the tree and lets the caller do the 'visiting'.
            This traversal goes into width. It is famous tree traversal algorithm
            BFS goes in width in such way that it yields all elements from left to right in each tree-level starting from the root of the tree
        '''
        if not self.is_empty():
            que = LinkedQueue()
            que.enqueue(self.root())
            while not que.is_empty():
                p = que.dequeue()
                yield p
                for c in self.children(p):
                    que.enqueue(c)

    def _subtree_postorder(self,p):
        for c in self.children(p):
            for other in self._subtree_postorder(c):
                yield other
        yield p
    
    def positions(self,impl=None):
        ''' Yields all positions from the tree
            Implementation can be passed as reference to one of the tree traversal algorithms in order to generate elements in such manner
            Default implementation is preorder tree traversal
        '''
        return self.preorder()
        
class BinaryTree(Tree):
    ''' base abstract class representing binary tree structure '''
    
    ''' #### abstract functions #### '''
    def left(self,p):
        raise NotImplementedError()
    
    def right(self, p):
        raise NotImplementedError()
    
    ''' #### concrete functions #### '''
    def sibling(self,p):        
        ''' Returns a Position representing p's sibling ( or None if no sibling) '''
        parent = self.parent(p)
        if parent is None:
            return None
        else:
            if p == self.left(parent):
                return self.right(parent)
            else:
                return self.left(parent)
            
    def children(self, p):
        ''' generate iteration of Positions representing p's children '''
        if self.left(p) is not None:
            yield self.left(p)
        if self.right(p) is not None:
            yield self.right(p)
    
    def num_children(self, p):
        '''
        Returns children number of node at position p
        '''
        ans = 0
        for child in self.children(p):
            ans+=1
        return ans
    
    def inorder(self):
        ''' Rather than visiting nodes this function just yields positions in the tree and lets the caller do the 'visiting'.
            This traversal goes into depth. It is one of three types of depth-first-search traversal algorithm
            Inorder goes in depth in such way that it yields elements from most left of the tree to the most right of the tree
            I.e 0
               / \
             1    2
            / \  / \
           3  4  5  6
           
           produces : [3,1,4,0,5,2,6]
           
           Inorder is only used for binary trees(decision trees).
           Good example of inorder algorithm usage is computing arithmetic expression(where operands are saved as external nodes and operations are saved as internal nodes)
        '''
        if not self.is_empty():
            for p in self._subtree_inorder(self.root()):
                yield p
            
    def _subtree_inorder(self,p):
        if self.left(p) is not None:
            for other in self._subtree_inorder(self.left(p)):
                yield other
        yield p
        if self.right(p) is not None:
            for other in self._subtree_inorder(self.right(p)):
                yield other
            
    def positions(self, impl=None):
        ''' Uses inorder traversal algorithm
            Check inorder function documentation for more information about algorithm
        '''
        return self.inorder()