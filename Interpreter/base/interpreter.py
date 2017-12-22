INTEGER,PLUS,MINUS,EOF = 'INTEGER', 'PLUS', 'MINUS', 'EOF'


class Token:
    def __init__(self,type,value):
        self.type = type
        self.value = value
        
    def __str__(self):
        return 'Token({type}, {value})'.format(
            type=self.type,
            value=repr(self.value))
        
    def __repr__(self):
        return self.__str__()
    
    
class Interpreter:
    
    def __init__(self, text):
        self.text = text
        self.pos = 0
        self.current_token = None
        self.current_char = self.text[self.pos]
        
    def error(self):
        raise Exception('Error parsing input')
    
    def advance(self):
        '''
        Advance the 'pos' pointer and set the 'current_char' variable
        '''
        self.pos+=1
        if self.pos > len(self.text) - 1:
            self.current_char = None
        else:
            self.current_char = self.text[self.pos]
            
    
    def skip_whitespace(self):
        while self.current_char is not None and self.current_char.isspace():
            self.advance()
    
    def integer(self):
        result = ''
        while self.current_char is not None and self.current_char.isdigit():
            result += self.current_char
            self.advance()
        return int(result)
    
    def get_next_token(self):
        
        text = self.text
        
        if self.pos >= len(text):
            return Token(EOF, None)
        
        current_char = text[self.pos]
        
        if current_char.isspace():
            self.skip_whitespace()
        
        elif current_char.isdigit():
            return Token(INTEGER, self.integer())
        
        elif current_char == '+':
            self.advance()
            return Token(PLUS, current_char)
        
        elif self.current_char == '-':
            self.advance()
            return Token(MINUS, '-')
        
        else:
            self.error()
        
    
    def eat(self, token_type):
        if self.current_token.type == token_type:
            self.current_token = self.get_next_token()
        else:
            self.error()
    
    def term(self):
        token = self.current_token
        self.eat(INTEGER)
        return token.value
        
    def expr(self):
        self.current_token = self.get_next_token()
        
        result = self.term()
        while self.current_token.type in (PLUS, MINUS):
            token = self.current_token
            if token.type == PLUS:
                self.eat(PLUS)
                result += self.term()
            elif token.type == MINUS:
                self.eat(MINUS)
                result -= self.term()
        return result
    
def main():
    while True:
        try:
            text = input('calc> ')
        except EOFError:
            break
        if not text:
            continue
        interpreter = Interpreter(text)
        result = interpreter.expr()
        print(result)


if __name__ == '__main__':
    main()