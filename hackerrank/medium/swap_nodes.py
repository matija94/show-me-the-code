'''
url to problem
https://www.hackerrank.com/challenges/swap-nodes-algo/problem
'''

from queue import Queue


def levels_dict(root):
    def compute(ldict, root, depth):
        if root != None:
            nodes = ldict.get(depth, [])
            nodes.append(root)
            ldict[depth] = nodes
            compute(ldict, root.left, depth + 1)
            compute(ldict, root.right, depth + 1)

    ldict = {}
    compute(ldict, root, 1)
    return ldict


class Node:

    def __init__(self, item, left, right):
        self.item = item
        self.left = left
        self.right = right


def build_tree(indexes):
    root = Node(1, None, None)
    queue = Queue()
    queue.put(root)
    index = 0
    while not queue.empty() and index < len(indexes):
        node = queue.get()
        node.left = Node(indexes[index], None, None) if indexes[index] != -1 else None
        index+=1
        node.right = Node(indexes[index], None, None) if indexes[index] != -1 else None
        index+=1
        if node.left is not None:
            queue.put(node.left)
        if node.right is not None:
            queue.put(node.right)
    return root


def inorder_traversal(root):

    inorderl = []
    def compute(inorderl, root):
        if root is not None:
            compute(inorderl, root.left)
            inorderl.append(root.item)
            compute(inorderl, root.right)
    compute(inorderl,root)
    return inorderl

def swapNodes(indexes, queries):
    btree_list = [i for l in indexes for i in l]
    root = build_tree(btree_list)
    result = []
    ldict = levels_dict(root)
    for query in queries:
        for level in sorted(ldict):
            if level % query == 0:
                for node in ldict[level]:
                    node.left, node.right = node.right, node.left
        result.append(inorder_traversal(root))
    return result
