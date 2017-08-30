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
            
    