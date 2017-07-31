class GameEntry:
    
    def __init__(self, score, name):
        self._name = name
        self._score = score
        
    def get_name(self):
        return self._name

    def get_score(self):
        return self._score
    
    def __str__(self):
        return "{0} {1}".format(self._name, str(self._score))
    

class Scoreboard:
    
    '''
        Fixed-length sequence of high scores in decreasing order
    '''
    
    def __init__(self, capacity=10):
        self._board = [None] * capacity
        self._n = 0
        
    def __getitem__(self,k):
        if not 0 <= k < self._n:
            raise IndexError('invalid index')
        return self._board[k]
    
    def __str__(self):
        return '\n'.join(str(self._board[i]) for i in range(self._n))
    
    def add(self, entry):
        score = entry.get_score()
        
        good = self._n < len(self._board) or score > self._board[-1].get_score() # good if current capacity is less than maximum or new score is higher than lowest score
        
        if good:
            if self._n < len(self._board): # increase number of scores by one if len(current_scores) < max_capacity
                self._n+=1                 
            
            # if len(current_scores) == max_cap then the lowest score will be dropped out
                
            j = self._n-1
            while j>0 and self._board[j-1].get_score() < score:  # shift to the right all scores which are less than new one
                self._board[j] = self._board[j-1]
                j-=1
            self._board[j] = entry  # finally put new score in it's place which is made to be empty by the while loop which did right-shifting
            
            