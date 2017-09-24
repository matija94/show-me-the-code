from base.HtmlPage import HtmlPage
from utils.HtmlPageUtils import HtmlPageUtils
from os import makedirs
from utils.UrlUtils import UrlUtils
import time

class Actions:
    
    def __init__(self,agent):
        self._agent = agent
        
    def add_to_cart(self,url):
        '''
        Adds product to the cart 

        url should be valid single product url with add to cart link
        
        Raises ElementNotFound if add to cart link is not found 
        '''
        self._validate_agent(url)
        htmlPage = HtmlPage(self._agent.page_source())
        matched = htmlPage.find_all_tags_containing_text(tag=['button'],text=['cart','Cart','basket','Basket'])
        if len(matched)>0:
            for m in matched:
                print(HtmlPageUtils.get_path(m))
            self._agent.click(matched[0])
        
        self._agent.sleep(1.5)
        self._agent.close_popup()
        self._agent.sleep(1.5)
        return self
    
    def reload_and_ss(self,url,repeat,dir):
        if not repeat >= 1:
            raise ValueError('repeat must be positive')
        self._validate_agent(url)
        if dir[-1] != '/':
            dir += '/'
        domain = UrlUtils.get_domain(url)
        dir += domain + '/'
        makedirs(dir,exist_ok=True)
        while repeat>0:
            self._agent.screenshot('{0}{1}_{2}.png'.format(dir,'SS',str(time.time())))
            self._agent.connect(url)
            repeat-=1
        return self
        
    def _validate_agent(self,url):
        if self._agent.current_url() != url:
            self._agent.connect(url)
