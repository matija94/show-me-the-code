class CaesarCipher:
    '''
     Encodes each lower and upper alphabet char to SHIFT values to the right in the alphabet
     
     For instance if shift=4 char 'A' would get encoded to (index_in_alphabet('A') + 4) modulo 26, which would equal to 4. We are considering alphabet indexes as zero based
     Notice that we are using 26 as modulo here because that is length of the alphabet itself
    '''
    R = 26 # alphabet length A-Z
    def __init__(self, shift=3):
        encoder = [None] * CaesarCipher.R
        decoder = [None] * CaesarCipher.R
        for k in range(CaesarCipher.R):
            encoder[k] = chr((k+shift) % 26 + ord('A'))
            decoder[k] = chr((k-shift) % 26 + ord('A'))
        self._forward_upper = ''.join(encoder)
        self._backward_upper = ''.join(decoder)
        self._forward_lower = ''.join([chr(ord(e)+32) for e in encoder])
        self._backward_lower = ''.join([chr(ord(d)+32) for d in decoder])
        
    def encrypt(self, message):
        return self._transform(message, self._forward_upper, self._forward_lower)
    
    def decrypt(self, secret):
        return self._transform(secret, self._backward_upper, self._backward_lower)
    
    def _transform(self, original, code_upper, code_lower):
        msg = list(original)
        for k in range(len(msg)):
            if msg[k].isupper():
                j = ord(msg[k]) - ord('A')
                msg[k] = code_upper[j]
            elif msg[k].islower():
                j = ord(msg[k]) - ord('a')
                msg[k] = code_lower[j]
        return ''.join(msg)
    
    
if __name__ == '__main__':
    CC = CaesarCipher()
    message = "HELLO WORLD, my name is matija!"
    coded = CC.encrypt(message)
    print("Secret : {0}".format(coded))
    ans = CC.decrypt(coded)
    print("Answer : {0}".format(ans))
