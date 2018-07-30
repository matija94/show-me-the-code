
class PriorityQueueBase:
    ''' abstract base class for a priority queue '''

    class _Item:
        __slots_ = '_key', '_value'

        def __init__(self, k, v):
            self._key = k
            self._value = v

        def __lt__(self, other):
            return self._key < other._key

    def is_empty(self):
        return len(self) == 0


class UnsortedPriorityQueue(PriorityQueueBase):
    ''' a min-oriented priority queue implemented with unsorted list '''

    def _find_min(self):
        ''' private function. Finds position holding smallest element in the queue'''
        if self.is_empty():
            raise ValueError('priority queue is empty')
        small = self._data.first()
        walk = self._data.after(small)
        while walk is not None:
            if walk.element() < small.element():
                small = walk
            walk = self._data.after(walk)
        return small

    def __init__(self):
        self._data = PositionalList()

    def __len__(self):
        return len(self._data)

    def add(self, key, value):
        ''' adds a key-value pair '''
        self._data.add_last(self._Item(key, value))

    def min(self):
        ''' inspect min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._find_min()
        item = p.element()
        return (item._key, item._value)

    def remove_min(self):
        ''' remove and return min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._find_min()
        item = self._data.delete(p)
        return (item._key, item._value)


class SortedPriorityQueue(PriorityQueueBase):

    def __init__(self):
        self._data = PositionalList()

    def __len__(self):
        return len(self._data)

    def add(self, key, value):
        ''' add a key-value pair '''
        newest = self._Item(key, value)
        walk = self._data.last()
        while walk is not None and newest < walk.element():
            walk = self._data.before(walk)
        if walk is None:
            self._data.add_first(newest)
        else:
            self._data.add_after(newest, walk)

    def min(self):
        ''' inspects minimum key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data.first().element()
        return (item._key, item._value)

    def remove_min(self):
        ''' removes and returns min key-value pair '''
        if self.is_empty():
            raise ValueError('empty')
        p = self._data.first()
        item = self._data.delete(p)
        return (item._key, item._value)


class MaxPriorityQueue(PriorityQueueBase):

    def _left(self, j):
        return 2 * j + 1

    def _right(self, j):
        return 2 * j + 2

    def _parent(self, j):
        return (j - 1) // 2

    def _swap(self, i, j):
        self._data[i], self._data[j] = self._data[j], self._data[i]

    def _upheap(self, j):
        parent = self._parent(j)
        if parent >= 0 and self._data[j] > self._data[parent]:
            self._swap(j, parent)
            self._upheap(parent)

    def _downheap(self, j):
        left = self._left(j)
        if left < len(self._data):
            big = left
            right = self._right(j)
            if right < len(self._data) and self._data[right] > self._data[left]:
                big = right
            if self._data[big] > self._data[j]:
                self._swap(big, j)
                self._downheap(big)

    def __len__(self):
        return len(self._data)

    def __init__(self, contents=()):
        self._data = [self._Item(k, v) for k, v in contents]
        if len(self._data) > 1:
            self._heapify()

    def _heapify(self):
        '''
        private function. Finds first non-leaf element and performs down heap for each element from first non-leaf to root
        '''
        first_non_leaf = self._parent(len(self._data) - 1)
        for i in range(first_non_leaf, -1, -1):
            self._downheap(i)

    def add(self, key, value):
        ''' adds key-value pair'''
        self._data.append(self._Item(key, value))
        self._upheap(len(self._data) - 1)

    def max(self):
        ''' inspects head of the queue
            Head of the queue is the minimum element heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data[0]
        return (item._key, item._value)

    def remove_max(self):
        '''
        removes and returns head of the queue
        head of the queue is the minimum element in the heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        self._swap(0, len(self._data) - 1)
        item = self._data.pop()
        self._downheap(0)
        return (item._key, item._value)


class MinPriorityQueue(PriorityQueueBase):

    def _left(self, j):
        return 2 * j + 1

    def _right(self, j):
        return 2 * j + 2

    def _parent(self, j):
        return (j - 1) // 2

    def _swap(self, i, j):
        self._data[i], self._data[j] = self._data[j], self._data[i]

    def _upheap(self, j):
        parent = self._parent(j)
        if parent >= 0 and self._data[j] < self._data[parent]:
            self._swap(j, parent)
            self._upheap(parent)

    def _downheap(self, j):
        left = self._left(j)
        if left < len(self._data):
            small = left
            right = self._right(j)
            if right < len(self._data) and self._data[right] < self._data[left]:
                small = right
            if self._data[small] < self._data[j]:
                self._swap(small, j)
                self._downheap(small)

    def __len__(self):
        return len(self._data)

    def __init__(self, contents=()):
        self._data = [self._Item(k, v) for k, v in contents]
        if len(self._data) > 1:
            self._heapify()

    def _heapify(self):
        '''
        private function. Finds first non-leaf element and performs down heap for each element from first non-leaf to root
        '''
        first_non_leaf = self._parent(len(self._data) - 1)
        for i in range(first_non_leaf, -1, -1):
            self._downheap(i)

    def add(self, key, value):
        ''' adds key-value pair'''
        self._data.append(self._Item(key, value))
        self._upheap(len(self._data) - 1)

    def min(self):
        ''' inspects head of the queue
            Head of the queue is the minimum element heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        item = self._data[0]
        return (item._key, item._value)

    def remove_min(self):
        '''
        removes and returns head of the queue
        head of the queue is the minimum element in the heap
        '''
        if self.is_empty():
            raise ValueError('empty')
        self._swap(0, len(self._data) - 1)
        item = self._data.pop()
        self._downheap(0)
        return (item._key, item._value)


class AdaptableMinPriorityQueue(MinPriorityQueue):
    class Locator(MinPriorityQueue._Item):
        __slots_ = '_index'

        def __init__(self, k, v, j):
            super().__init__(k, v)
            self._index = j

    def _swap(self, i, j):
        super()._swap(i, j)
        self._data[i]._index = i  # reset locator index, post-swap
        self._data[j]._index = j  # reset locator index, post-swap

    def _validate_loc(self, loc):
        if not type(loc) is self.Locator:
            raise TypeError('not locator')
        j = loc._index
        if not (0 <= j < len(self._data) and self._data[j] is loc):
            raise ValueError('invalid locator')
        return j

    def _bubble(self, j):
        if j > 0 and self._data[j] < self._data[self._parent(j)]:
            self._upheap(j)
        else:
            self._downheap(j)

    def add(self, key, value):
        ''' Add a key,value pair and returns Locator for new entry '''
        token = self.Locator(key, value, len(self._data))
        self._data.append(token)
        self._upheap(len(self._data) - 1)
        return token

    def update(self, loc, newkey, newvalue):
        ''' Updates key and value for the entry identified by Locator loc '''
        j = self._validate_loc(loc)
        loc._key = newkey
        loc._value = newvalue
        self._bubble(j)

    def remove(self, loc):
        ''' Remove and return (k,v) pair identified by Locator loc '''
        j = self._validate_loc(loc)
        if j == len(self._data) - 1:
            self._data.pop()
        else:
            self._swap(j, len(self._data) - 1)
            self._data.pop()
            self._bubble(j)
        return (loc._key, loc._value)


class AdaptableMaxPriorityQueue(MaxPriorityQueue):
    class Locator(MaxPriorityQueue._Item):
        __slots_ = '_index'

        def __init__(self, k, v, j):
            super().__init__(k, v)
            self._index = j

    def _swap(self, i, j):
        super()._swap(i, j)
        self._data[i]._index = i  # reset locator index, post-swap
        self._data[j]._index = j  # reset locator index, post-swap

    def _validate_loc(self, loc):
        if not type(loc) is self.Locator:
            raise TypeError('not locator')
        j = loc._index
        if not (0 <= j < len(self._data) and self._data[j] is loc):
            raise ValueError('invalid locator')
        return j

    def _bubble(self, j):
        if j > 0 and self._data[j] > self._data[self._parent(j)]:
            self._upheap(j)
        else:
            self._downheap(j)

    def add(self, key, value):
        ''' Add a key,value pair and returns Locator for new entry '''
        token = self.Locator(key, value, len(self._data))
        self._data.append(token)
        self._upheap(len(self._data) - 1)
        return token

    def update(self, loc, newkey, newvalue):
        ''' Updates key and value for the entry identified by Locator loc '''
        j = self._validate_loc(loc)
        loc._key = newkey
        loc._value = newvalue
        self._bubble(j)

    def remove(self, loc):
        ''' Remove and return (k,v) pair identified by Locator loc '''
        j = self._validate_loc(loc)
        if j == len(self._data) - 1:
            self._data.pop()
        else:
            self._swap(j, len(self._data) - 1)
            self._data.pop()
            self._bubble(j)
        return (loc._key, loc._value)

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

    def get_edge(self, u, v):
        '''
        return the edge from u to v, or None if not adjacent.
        '''
        return self._outgoing[u].get(v)  # returns None if not adjacent

    def degree(self, v, outgoing=True):
        '''
        Return number of (outgoing) edges incident to vertex v in the graph

        If graph is directed, optional paramter used to count incoming edges
        '''
        adj = self._outgoing if outgoing else self._incoming
        return len(adj[v])

    def incident_edges(self, v, outgoing=True):
        '''
        Return all (outgoing) edges incident to vertex v in the graph.
        If graph is directed, optional parameter used to request incoming edges.
        '''
        adj = self._outgoing if outgoing else self._incoming
        for vertex in adj[v]:
            yield vertex

    def insert_edge(self, u, v, x):
        '''
        insert and return new edge from u to v with auxiliary element x
        '''
        if u not in self._outgoing:
            self._outgoing[u] = {}
        if v not in self._outgoing:
            self._outgoing[v] = {}
        if u not in self._incoming:
            self._incoming[u] = {}
        if v not in self._incoming:
            self._incoming[v] = {}

        self._outgoing[u][v] = x
        self._incoming[v][u] = x

    def remove_node(self, x):
        del self._incoming[x]
        for v in self.vertices():
            if x in self._outgoing[v]:
                del self._outgoing[v][x]

    def farthest(self, s):
        d = {}
        cloud = {}
        pq = AdaptableMaxPriorityQueue()
        pqlocator = {}

        for v in self.vertices():
            if v is s:
                d[v] = 0
            else:
                d[v] = -1
            pqlocator[v] = pq.add(d[v], v)

        while not pq.is_empty():
            key, u = pq.remove_max()
            cloud[u] = key
            del pqlocator[u]

            for v in self.incident_edges(u):
                if v not in cloud:
                    wgt = self._outgoing[u][v]
                    if d[u] + wgt > d[v]:
                        d[v] = d[u] + wgt
                        pq.update(pqlocator[v], d[v], v)
        max = 0
        retnode = None
        for node, cost in cloud.items():
            if max < cost:
                max = cost
                retnode = node
        return retnode


    def distance(self, s, y):
        d = {}
        cloud = {}
        pq = AdaptableMinPriorityQueue()
        pqlocator = {}

        for v in self.vertices():
            if v is s:
                d[v] = 0
            else:
                d[v] = float('inf')
            pqlocator[v] = pq.add(d[v], v)

        while not pq.is_empty():
            key, u = pq.remove_min()
            cloud[u] = key
            del pqlocator[u]

            for v in self.incident_edges(u):
                if v not in cloud:
                    wgt = self._outgoing[u][v]
                    if d[u] + wgt < d[v]:
                        d[v] = d[u] + wgt
                        pq.update(pqlocator[v], d[v], v)
        return cloud[y]


def dijkstra(g, s):
    d = {}
    cloud = {}
    pq = AdaptableMinPriorityQueue()
    pqlocator = {}

    for v in g.vertices():
        if v is s:
            d[v] = 0
        else:
            d[v] = float('inf')
        pqlocator[v] = pq.add(d[v], v)

    while not pq.is_empty():
        key, u = pq.remove_min()
        cloud[u] = key
        del pqlocator[u]

        for e in g.incident_edges(u):
            v = e.opposite(u)
            if v not in cloud:
                wgt = e.element()
                if d[u] + wgt < d[v]:
                    d[v] = d[u] + wgt
                    pq.update(pqlocator[v], d[v], v)
    return cloud


def query_one(g, x, w):
     y = g.farthest(x)
     n = g.vertex_count()
     g.insert_edge(y, n+1, w)


def query_two(g, x, w):
    n = g.vertex_count()
    g.insert_edge(x, n+1, w)


def query_three(g, x):
    y = g.farthest(x)
    g.remove_node(y)


def query_four(g, x):
    y = g.farthest(x)
    return g.distance(x, y)

def cyclicalQueries(w, m):
    g = Graph(directed=True)
    n = len(w)
    for i in range(n):
        g.insert_edge((i+1) % n+1, (i+2) % n+1, w[i])
    queries = []
    for _ in range(m):
        query = list(map(int, input().rstrip().split()))
        queries.append(query)
    result = []
    for query in queries:
        qtype = query[0]
        if qtype == 1:
            query_one(g, query[1], query[2])
        elif qtype == 2:
            query_two(g, query[1], query[2])
        elif qtype == 3:
            query_three(g, query[1])
        elif qtype == 4:
            result.append(query_four(g, query[1]))
        else:
            raise ValueError('input error')
    return result


if __name__ == '__main__':
    n = int(input())

    w = list(map(int, input().rstrip().split()))

    m = int(input())

    result = cyclicalQueries(w, m)

    print('\n'.join(map(str, result)))
