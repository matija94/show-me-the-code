import bs4
import re

class HtmlPage:
    
    def __init__(self, page_source):
        if not type(page_source) is str:
            raise TypeError('must be string')
        self._bs = bs4.BeautifulSoup(page_source, 'html.parser')
        
    def find_all_tags_containing_text(self,tag,text,attributes=None):
        if not type(text) is str:
            if type(text) is list:
                text = '|'.join(text)
            else:
                raise TypeError('text must be string or list of strings')
        if attributes is None:
            matched = self._bs.find_all(name=tag,string=re.compile(text))
        else:
            matched = self._bs.find_all(name=tag,string=re.compile(text),attrs=attributes)
        return matched
    
    def body(self):
        return self._bs.body
