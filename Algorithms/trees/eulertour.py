from trees.concrete import LinkedBinaryTree
from math import log2

class EulerTour:
    
    ''' abstract base class for performing euler tour
    
        _hook_previsit and _hook_postvisit may be overriden subclasses
    '''
    
    def __init__(self, tree):
        self._tree = tree
        
    def tree(self):
        return self._tree
    
    def execute(self):
        if len(self._tree) > 0:
            return self._tour(self._tree.root(), 0, list())
    
    def _tour(self,pos,depth,path):
        self._hook_previsit(pos, depth, path)
        results = []
        path.append(0)
        for child in self._tree.children(pos):
            results.append(self._tour(child, depth+1, path))
            path[-1]+=1
        path.pop()
        answer = self._hook_postvist(pos, depth, path, results)
        return answer
        
    def _hook_previsit(self,pos,depth,path):
        pass
    
    def _hook_postvist(self,pos,depth,path,results):
        pass
    
    
class BinaryEulerTour(EulerTour):
    
    def _tour(self, pos, depth, path):
        results = [None,None]
        self._hook_previsit(pos, depth, path)
        if self._tree.left(pos) is not None:
            path.append(0)
            results[0] = self._tour(self._tree.left(pos), depth+1, path)
            path.pop()
        self._hook_invisit(pos,depth,path)
        if self._tree.right(pos) is not None:
            path.append(1)
            results[1] = self._tour(self._tree.right(pos), depth+1, path)
            path.pop()
        answer = self._hook_postvist(pos, depth, path, results)
        return answer
    
    def _hook_invisit(self,pos,depth,path):
        pass

class BinaryLayout(BinaryEulerTour):
    '''
    example usage of binary euler tour
    
    represents coordinate pane for binary tree marking each node with (x,y)
    X axis represents number of invisits before given position
    Y axis represents depth of given position
    '''
    def __init__(self, tree):
        super().__init__(tree)
        self._count = 0 # represents x coordinate
        self._pane = dict()

    def _hook_invisit(self, pos, depth, path):
        key = str(self._count) + str(depth)
        self._pane[key] = pos
        self._count+=1
        
    def get(self, X, Y):
        if X > self._count or Y > int(log2(len(self._tree))):
            raise ValueError('Invalid coordinates')
        key = str(X) + str(Y)
        return self._pane[key]

class LevelNumberFunction(EulerTour):
    '''
        computes level of each position in the tree
    '''
    def _hook_previsit(self, pos, depth, path):
        s = 'Element: %s. Level: %s' %(pos.element(), depth)
        print(s)
        
class PreorderPrintIndentedTour(EulerTour):
    ''' indents each element in the tree by its depth
    
        prints tree in preorder manner
    '''
    
    def _hook_previsit(self, pos, depth, path):
        print(2*depth*' ' + str(pos.element()))


class PreorderPrintLabeledIndentedTour(EulerTour):
    ''' labels and indents each element in the tree by its depth/path
    
        prints tree in preorder manner
    ''' 
        
    def _hook_previsit(self, pos, depth, path):
        label = '.'.join(str(j+1) for j in path)
        sep = ' '
        if not label:
            sep = ''
        print(2*depth*' ' + label + sep + str(pos.element()))    


class ParenthesizeTour(EulerTour):
    
    ''' prints tree in parenthesized manner '''
    
    def _hook_previsit(self, pos, depth, path):
        if (len(path) > 0 and path[-1] > 0):
            print(', ', end='')
            
        print(str(pos.element()), end='')
        
        if not self.tree().is_leaf(pos):
            print(' (', end='')
    
    def _hook_postvist(self, pos, depth, path, results):
        if not self.tree().is_leaf(pos):
            print(')', end='')
            
if __name__ == '__main__':
    t = LinkedBinaryTree()
    print(len(t))
    cars = t.add_root("Cars")
    mercedes = t.add_left(cars, 'Mercedes')
    bmw = t.add_right(cars, 'BMW')
    bmw_series = t.add_left(bmw, 'BMW Series')
    bmw_z = t.add_right(bmw, 'BMW Z')
    mercedes_amg = t.add_left(mercedes, 'Mercedes AMG')
    mercedes_normal = t.add_right(mercedes, 'Mercedes normal')
    t.add_left(mercedes_amg, 'C63 AMG')
    t.add_right(mercedes_amg, 'CLA AMG')
    t.add_left(mercedes_normal, "C Class")
    t.add_right(mercedes_normal, 'S Class')
    t.add_left(bmw_series, 'BMW 5 Series')
    t.add_right(bmw_series, 'BMW 3 Series')
    t.add_left(bmw_z, "BMW Z4")
    bmwz5 = t.add_right(bmw_z, 'BMW Z5')
    t.add_left(bmwz5, "BMW Z5 Coupe")
    t.delete(bmwz5)
    tour = PreorderPrintIndentedTour(t)
    tour.execute()

    tour = PreorderPrintLabeledIndentedTour(t)
    tour.execute()
    print('\nCurrent size is {0}'.format(len(t)))
    t._delete_subtree(mercedes_amg)
    print('\nCurrent size is {0}'.format(len(t)))
    tour.execute()

    tour = ParenthesizeTour(t)
    tour.execute()
     
    tour = LevelNumberFunction(t)
    tour.execute()
    
    for p in t.breadthfirst():
        print(p.element()) 
     