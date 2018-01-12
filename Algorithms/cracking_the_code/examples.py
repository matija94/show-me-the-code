n = 1000

map = {}
for c in range(1,n):
    for d in range(1,n):
        result = c**3 + d**3
        if result not in map:
            map[result] = [(c,d)]
        else:
            map[result].append((c,d))
            
for result, list in map.items():
    for pair1 in list:
        for pair2 in list:
            print(pair1, pair2)