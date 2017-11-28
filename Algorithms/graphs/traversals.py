def DFS(g, u, discovered):
    '''
    perform DFS on the Graph g starting at Vertex u
    
    discovered is a dictionary mapping each vertex to the edge which was followed to discover vertex ( u should be discovered prior to the call )
    '''
    for e in g.incident_edges(u):
        v = e.opposite(u)
        if v not in discovered:
            discovered[v] = e
            DFS(g, v, discovered)
            
def construct_path(u, v, discovered):
    '''
    constructs a path from u to v, represented as list of vertices
    
    discovered is the dictionary mapping each vertex to the edge which was followed to discover the vertex.
    '''
    path = []
    if v in discovered:
        path.append(v)
        walk = v
        while walk is not u: # until we reach starting point
            e = discovered[walk]
            parent = e.opposite(walk)
            path.append(parent)
            walk = parent
        path.reverse()
    return path

def DFS_complete(g):
    '''
    perform DFS for entire graph and return forest as a dictionary
    
    forest is dictionary mapping each vertex to the edge that discovered it. Vertices which have None as the key are considered to be roots of the traversal
    '''
    forest = {}
    for u in g.vertices():
        if u not in forest:
            forest[u] = None
            DFS(g, u, forest)
    return forest