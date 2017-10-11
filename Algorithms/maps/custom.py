from maps.concrete import SortedTableMap
class CostPerformanceDatabase:
    
    def __init__(self):
        self._map = SortedTableMap()
        
    def add(self,cost,performance):
        lower_cost_car = self._map.find_lt(cost)
        
        if lower_cost_car is not None and lower_cost_car[1] >= performance: # find car that is lower cost than argument
            return                                                          # if lower cost car has better performance than argument car do not add since this data point would be 
                                                                            # dominated by existing one
        self._map[cost] = performance
        '''
        now let's remove all data points that are dominated by the new data point
        i.e remove cars that are more expensive then newly added one but have lower performance
        '''
        higher_cost_car = self._map.find_gt(cost)
        while higher_cost_car is not None and higher_cost_car[1] <= performance:
            del self._map[higher_cost_car[0]]
            higher_cost_car = self._map.find_gt(cost) # keep searching if there are more cars with poorer performance and higher cost
            
    def best(self, cost):
        ''' Returns best-performance for the given cost '''
        return self._map.find_le(cost)
        




if __name__ == '__main__':
    cpDB = CostPerformanceDatabase()
    data = [(3000,50),(3100,52),(3000,60),(1500,20),(2500,45),(9500,100)]
    for c,p in data:
        cpDB.add(c, p)
    
    
    print(cpDB.best(10000))