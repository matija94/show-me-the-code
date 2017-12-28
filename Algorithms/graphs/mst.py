'''
MINIMUM SPANNING TREES

In the office with 10 computers you want to establish a network. Network should include each computer, computers will be connected within cable. Although we want to spend as less cable as we need.

MST can solve this problem with use of undirected weighted graphs
'''
from prio_queue.structures import AdaptableMinPriorityQueue

def MST_PrimJarnik(G):
    '''
    compute a MST of weighted graph G
    
    Return a list of edges that compromise MST (in arbitary order)
    '''
    
    d = {}
    tree = []
    pq = AdaptableMinPriorityQueue()
    pqlocator = {}
    
    for v in G.vertices():
        if len(d) == 0:
            d[v] = 0
        else:
            d[v] = float('inf')
        pqlocator[v] = pq.add(d[v], (v,None))
        
    while not pq.is_empty():
        key,value = pq.remove_min()
        u,edge = value
        del pqlocator[u]
        if edge is not None:
            tree.append(edge)
        for link in G.incident_edges(u):
            v = link.opposite(u)
            if v in pqlocator:
                wgt = link.element()
                if wgt < d[v]:
                    d[v] = wgt
                    pq.update(pqlocator[v], d[v], (v,link))
    return tree
