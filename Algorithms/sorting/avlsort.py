from maps.concrete import AVLTreeMap
def sort(seq):
    tree = AVLTreeMap()
    for s in seq:
        tree[s] = 0
    res = [None] * len(seq)
    i=0
    for s in tree.inorder():
        res[i]=s.key()
        i+=1
    return res
    
if __name__ == '__main__':
    seq = [4,1,2,5,6,0,9,7,10,3]
    print(sort(seq))