import string 
import sequences

#P-5.35 exercise
class SubstitutionCipher:
    '''
     Encodes each alphabet char with each char from upper_case_alphabet respectively , which is argument for the constructor of the object
    '''
    R = 26 # alphabet length A-Z
    def __init__(self, upper_case_alphabet):
        #R-5.10 exercise
        self._forward_upper = upper_case_alphabet
        n = len(upper_case_alphabet)
        self._backward_upper = [None] * n
        for i in range(n):
            index = ord(upper_case_alphabet[i]) - ord('A')
            self._backward_upper[index] = chr(i+ord('A'))
        #P-5.34 exercise
        self._forward_lower = ''.join([chr(ord(e)+32) for e in self._forward_upper])
        self._backward_lower = ''.join([chr(ord(d)+32) for d in self._backward_upper])
        
        
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


class CaesarCipher(SubstitutionCipher):
    '''
     Encodes each lower and upper alphabet char to SHIFT values to the right in the alphabet
     
     For instance if shift=4 char 'A' would get encoded to (index_in_alphabet('A') + 4) modulo 26, which would equal to 4. We are considering alphabet indexes as zero based
     Notice that we are using 26 as modulo here because that is length of the alphabet itself
    '''
    R = 26 # alphabet length A-Z
    def __init__(self, shift=3):
        #R-5.10 exercise
        self._forward_upper = ''.join([chr((k+shift)%26+ord('A')) for k in range(CaesarCipher.R)])
        self._backward_upper = ''.join([chr((k-shift)%26+ord('A')) for k in range(CaesarCipher.R)])
        #P-5.34 exercise
        self._forward_lower = ''.join([chr(ord(e)+32) for e in self._forward_upper])
        self._backward_lower = ''.join([chr(ord(d)+32) for d in self._backward_upper])


#P-5.37 exercise
class RandomCipher(SubstitutionCipher):
    
    def __init__(self):
        arr = sequences.DynamicArray(26)
        for ch in string.ascii_uppercase:
            arr.append(ch)
        arr.shuffle(True)
        super().__init__(''.join(arr))
        
if __name__ == '__main__':
    CC = CaesarCipher()
    message = "HELLO WORLD, my name is matija!"
    coded = CC.encrypt(message)
    print("Secret : {0}".format(coded))
    ans = CC.decrypt(coded)
    print("Answer : {0}".format(ans))
    
    
    subcip = SubstitutionCipher('CDAB')
    msg = 'BCA'
    coded = subcip.encrypt(msg)
    print(coded)
    ans = subcip.decrypt(coded)
    print(ans)


    randchip = RandomCipher()
    msg = 'Hi my Name is MATIJA. Test!'
    coded = randchip.encrypt(msg)
    print(coded)
    ans = randchip.decrypt(coded)
    print(ans)