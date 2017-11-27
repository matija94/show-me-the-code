class Vertex:
    '''
    light weight structure representing a node in the graph
    '''
    __slots_ = '_element'
    
    def __init__(self, x):
        self._element = x
        
    def element(self):
        return self._element
    
    def __hash__(self):
        return hash(id(self))
    
class Edge:
    '''
    light weight structure representing connection between two nodes in the graph
    '''
    
    __slots_ = '_origin', '_destination', '_element'
    
    def __init__(self,u,v,x):
        self._origin = u
        self._destination = v
        self._element = x
    
    def endpoints(self):
        '''
        Return (u,v) tuple for vertices u and v
        '''
        return (self._origin, self._destination)
    
    def opposite(self, v):
        '''
        Return element associated with this edge
        '''
        return self._destination if v is self._origin else self._origin
    
    def element(self):
        '''
        return element associated with this edge
        '''
        return self._element
    
    def __hash__(self):
        return hash((self._origin, self._destination))
    

class Graph:
    '''
     representation of simple graph ( no self cycles and parallel edges ) using and adjacency map
    '''
    
    def __init__(self, directed=False):
        self._outgoing = {}
        self._incoming = {} if directed else self._outgoing
    
    def is_directed(self):
        '''
        return  True if graph is directed, False otherwise
        '''
        return self._incoming is not self._outgoing
    
    def vertex_count(self):
        '''
        return count of all vertices in the graph
        '''
        return len(self._outgoing)
    
    def vertices(self):
        '''
        return iteration over all vertices in the graph
        '''
        return self._outgoing.keys()
    
    def edge_count(self):
        '''
        return count of all edges off the graph
        '''
        total = sum(len(self._outgoing[v]) for v in self._outgoing)
        return total if self.is_directed() else total // 2
    
    def edges(self):
        '''
        return a set of all edges of the graph
        '''
        result = set()
        for secondary_map in self._outgoing.values():
            result.update(secondary_map.values())
        return result
    
    def get_edge(self,u,v):
        '''
        return the edge from u to v, or None if not adjacent.
        '''
        return self._outgoing[u].get(v) # returns None if not adjacent
    
    def degree(self,v,outgoing=True):
        '''
        Return number of (outgoing) edges incident to vertex v in the graph
        
        If graph is directed, optional paramter used to count incoming edges
        '''
        adj = self._outgoing if outgoing else self._incoming
        return len(adj[v])
        
    def incident_edges(self,v, outgoing=True):
        '''
        Return all (outgoing) edges incident to vertex v in the graph.
        If graph is directed, optional parameter used to request incoming edges.
        '''
        adj = self._outgoing if outgoing else self._incoming
        for edge in adj[v].values():
            yield edge
            
    def insert_vertex(self, x=None):
        '''
        insert and return new vertex with element x 
        '''
        v = Vertex(x)
        self._outgoing[v] = {}
        if self.is_directed():
            self._incoming[v] = {}
        return v
    
    def insert_edge(self,u,v,x=None):
        '''
        insert and return new edge from u to v with auxiliary element x
        '''
        e = Edge(u,v,x)
        self._outgoing[u][v] = e
        self._incoming[v][u] = e