from graphs.base import Graph, Vertex
from _collections import defaultdict
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


def BFS(g, s, discovered):
    '''
    Perform BFS of the undiscovered portion of Graph G starting at Vertex s
    
    discovered is a dictionary mapping each vertex to the edge that was used to discover it during BFS ( s should be mapped to None prior the call )
    Newly discovered vertices will be added to the dictionary as a result.
    ''' 
    que = [s]
    while len(que) > 0:
        v = que.pop()
        for e in g.incident_edges(v):
            opposite = e.opposite(v)
            if opposite not in discovered:
                que.append(opposite)
                discovered[opposite] = e

def BFS_complete(g):
    '''
    Perform BFS for the complete tree
    
    Returns forest dictionary, mapping each vertex to the edge that was used to discover it during BFS( vertices mapping to None are start of the graph component )
    '''
    forest = {}
    for v in g.vertices():
        if v not in forest:
            forest[v] = None
            BFS(g, v, forest)
    return forest


def is_dag_visit(g, s, visited):
    '''
    Returns true if the subgraph G with starting point at s has any cycles in it ( false otherwise )
    
    visited is dictionary mapping each vertex to it's parent vertex that used to discover it
    '''
    for e in g.incident_edges(s):
        v = e.opposite(s)
        if v not in visited:
            visited[v] = s
            return is_dag_visit(g, v, visited)
        else:
            return False
    return True
    
                
if __name__ == '__main__':
    g = Graph()
    a = g.insert_vertex('A')
    b = g.insert_vertex('B')
    c = g.insert_vertex('C')
    d= g.insert_vertex('D')
    g.insert_edge(a, b, 'ab_bi')
    g.insert_edge(a, d, 'ad_bi')
    g.insert_edge(b, d, 'bd_bi')
    g.insert_edge(d, c, 'dc_bi')
    g.insert_edge(a, c, 'ca_bi')


    forest = DFS_complete(g)
    print(forest)
    bfs_forest = BFS_complete(g)
    print(bfs_forest)
    
    path = construct_path(a, d, forest)
    print(path)
    
    path_bfs = construct_path(a, d, bfs_forest)
    print(path_bfs)
    