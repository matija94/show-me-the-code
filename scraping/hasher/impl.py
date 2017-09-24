from collections import defaultdict


class VisitorHasher:
    
    def __init__(self):
        self._visited = defaultdict(list)
    
    def visit(self, address, city, code, offer_name):
        a = self._Address(address, city, code)
        o = self._Offer(offer_name)
        self._visited[a].append(o)
        
    def __iter__(self):
        for address in self._visited:
            yield self._visited[address]
    
    def __getitem__(self, item):
        if item in self._visited:
            return self._visited[item]
        else:
            return None
        
    def is_visited(self, address, city, code, offer_name):
        a = self._Address(address, city, code)
        o = self._Offer(offer_name)
        if a in self._visited:
            offers = self._visited[a] 
            return o in offers
        return False

    class _Address:
        
        def __init__(self, address, city, code):
            self._address = address
            self._city = city
            self._code = code

        def __hash__(self):
            res = 0
            prime = 31
            res = prime * res + hash(self._address) if self._address is not None else 0
            res = prime * res + hash(self._city) if self._city is not None else 0
            res = prime * res + hash(self._code) if self._city is not None else 0
            return res
        
        def __eq__(self, other):
            if not isinstance(other, VisitorHasher._Address): return False
            if self._address != other._address: return False
            if self._city != other._city: return False
            if self._code != other._code: return False
            return True
        
        def __str__(self):
            return 'Address: {0} City: {1} Zip: {2}'.format(self._address, self._city, self._code)
    
    class _Offer:
        
        def __init__(self, name):
            self._name = name
            
        def __eq__(self, other):
            if not isinstance(other, VisitorHasher._Offer): return False
            return self._name == other._name

        def __str__(self):
            return self._name
