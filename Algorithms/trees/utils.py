class TreeUtils:

    @staticmethod
    def print_tree_indent(T, p, depth):
        ''' prints tree in preorder manner
            Cars
                BMW
                    BMW_M4
                AUDI
                    AUDI_A6
                FIAT
                MERCEDES
                    MERCEDES CLA
                    MERCEDES C63 AMG
                VOLVO
        
            To print the whole tree use this function with arguments (T, T.root(), 0)
        '''
        print(2*depth*' ' + str(p.element()))
        for c in T.children(p):
            TreeUtils.print_tree_indent(T, c, depth+1)
            
            
    @staticmethod
    def print_tree_labeled_ident(T, p, depth):
        ''' prints tree in preorder manner
            Cars
                1 BMW
                    1.1 BMW_M4
                2 AUDI
                    2.1 AUDI_A6
                3 FIAT
                4 MERCEDES
                    4.1 MERCEDES CLA
                    4.2 MERCEDES C63 AMG
                5 VOLVO
        
            To print the whole tree use this function with arguments (T, T.root(), 0)
        '''
        TreeUtils._print_tree_labeled_indent(T, p, depth, list())
    
    @staticmethod
    def _print_tree_labeled_indent(T, p, depth, path):
        ''' private function '''
        label = '.'.join(str(j+1) for j in path)
        print(2*depth*' ' + label + ' ' + str(p.element()))
        path.append(0)
        for c in T.children(p):
            TreeUtils.print_tree_indent(T, c, depth+1, path)
            path[-1]+=1
        path.pop()
        
    
    @staticmethod
    def print_tree_parenthesize(T, p):
        print(p.element(), end='')
        first = True
        for c in T.children(p):
            part = ' (' if first else ', '
            print(part, end='')
            TreeUtils.print_tree_parenthesize(T, c)
            first = False
        print(')', end='')