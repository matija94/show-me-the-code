import json
import random

class SSNGen:
        
        def __init__(self):
            with open('/home/matija/Desktop/ssn.json', 'r') as ssn:
                self._ssn = json.load(ssn)    
            self._index = len(self._ssn)-1
        
        def get_ssn(self):
            ans = self._ssn[self._index]
            self._index-=1
            return ans['ssn']
        
class AddressGenerator:
    
    def __init__(self, address, zip_code):
        self._set_address(address)
        self._set_code(zip_code)
    
    def _set_address(self,address):
        addres_num = address.split(' ')[0]
        self._add =int(addres_num)
        self._addrest = ' '.join(address.split(' ')[1:])
        
    def _set_code(self,code):
        self._code =int(code)
        
    def get_address(self):
        ans = self._add
        self._add-=1
        return ' '.join([str(ans), self._addrest])
    
    def get_code(self):
        ans = self._code
        #self._code-=1
        return str(ans)
    
    
if __name__ == '__main__':
    r = random.Random()
    print(r.randrange(0, 4))