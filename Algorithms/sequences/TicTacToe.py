class TicTacToe:
    
    '''
     Tic-Tac-Toe game
     It is played in the console
    '''
    
    def __init__(self):
        self._player = 'X' # X is initial and first mark
        self._board = [[' '] * 3 for j in range(3)] # generate empty 3x3 grid
        
    def mark(self,i,j):
        if not(0 <= i < 3 or 0 <= j < 3):
            raise IndexError('invalid position')
        if self._board[i][j] != ' ':
            raise IndexError('position already occupied')
        '''
            moved check for is winner to the __main__ 
            Winner is checked each time after player marks his next position
        '''
        #for mark in 'XO':
            #if self._is_winner(mark):
                #raise ValueError('{0} won!'.format(mark))
        self._board[i][j] = self._player
        if self._player == 'X':
            self._player = 'O'
        else:
            self._player = 'X'
                
    def _is_winner(self, mark):
        return (    (mark == self._board[0][0] and mark == self._board[0][1] and mark == self._board[0][2]) or # first row
                    (mark == self._board[1][0] and mark == self._board[1][1] and mark == self._board[1][2]) or # second row
                    (mark == self._board[2][0] and mark == self._board[2][1] and mark == self._board[2][2]) or # third row
                    (mark == self._board[0][0] and mark == self._board[1][0] and mark == self._board[2][0]) or # first col
                    (mark == self._board[0][1] and mark == self._board[1][1] and mark == self._board[2][1]) or # second col
                    (mark == self._board[0][2] and mark == self._board[1][2] and mark == self._board[2][2]) or # third col
                    (mark == self._board[0][0] and mark == self._board[1][1] and mark == self._board[2][2]) or # left-right diagonal
                    (mark == self._board[0][2] and mark == self._board[1][1] and mark == self._board[2][0])
                )
    def __str__(self):
        rows = ['|'.join(self._board[r]) for r in range(3)]
        return '\n-----\n'.join(rows)
    
    def get_curr_player(self):
        return self._player
    
    def parseCoordinates(self,coord_str):
        coords_list = coord_str.split(sep=',')
        return int(coords_list[0]), int(coords_list[1])
    
if __name__ == '__main__':
    t = TicTacToe()
    while True:
        curr_player = t.get_curr_player()
        coord_str = input('{0} player is on the move. Enter your coordinates <x,y>\n'.format(curr_player))
        x, y = t.parseCoordinates(coord_str)
        t.mark(x, y)
        print(t)
        if t._is_winner(curr_player):
            print('Game over. Player {0} has won!'.format(curr_player))
            break
        
        
        
        
        
        