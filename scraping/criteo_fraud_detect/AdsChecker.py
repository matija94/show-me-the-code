'''
Checks for Criteo ads on non 'disney safe' websites. See PG13 standard
'''

from base.FireFoxWebAgent import FireFoxWebAgent
from threading import Thread
#from utils.UrlUtils import UrlUtils
import logging
import re
from structures.queue import CircularQueue
import time
from utils.UrlUtils import UrlUtils
from os import makedirs

logging.basicConfig(level=logging.INFO, format='(%(threadName)-10s) %(message)s')

class AdsChecker(Thread):
    
    def __init__(self,monitor_sites,product_count=5,reload_count_website=20):
        '''
        Picks 'product_count' number of products from ivoryella.com shop and places them in cart in order to trigger ad retargeting by Criteo 
        
        Connects to 'monitor_sites' in circular fashion(round-robin) and reloads each website for 'reload_count_website' times. This process is known as monitoring for advertisement for ivoryella products by Criteo
        '''
        #super().__init__(name='Thread: {0}'.format(UrlUtils.get_domain(monitor_site)))
        super().__init__()
        self._monitor_sites = monitor_sites
        self._product_count = product_count
        self._reload_count_per_web = reload_count_website
        self._agent = FireFoxWebAgent(1900,1080) # insert width and height. Default is 800x600
        #self._agent.connect('https://www.ivoryella.com/')
        
    def run(self):
        self._register_products_in_cart(self._product_count)
        self._reload_and_ss(self._monitor_sites,self._reload_count_per_web*len(self._monitor_sites))
    
    def _register_products_in_cart(self, count):
        '''
        Puts products in the cart.
        Count determines number of products to put in the cart
        '''
        self.__register_products_in_cart(count, set())
    
    def _reload_and_ss(self,url,repeat):
        self._agent.open_window()
        circ = CircularQueue()
        for site in url:
            circ.enqueue(site)

        while repeat>0:
            logging.info('Reloading the page. Times left: %s', repeat)
            url  = circ.first()
            circ.rotate()
            self._agent.connect(url, timeout=180)
            self._agent.sleep(1)
            directory = '/home/matija/Desktop/fraud_detect_criteo_multiple/{0}/'.format(UrlUtils.get_domain(url).lower())
            makedirs(name=directory,exist_ok=True)
            self._agent.screenshot('{0}/ss_{1}.png'.format(directory, str(time.time())))
            repeat-=1
            
    def __register_products_in_cart(self, count, visited):
        self._agent.connect('https://www.ivoryella.com/')
        if count <= 0:
            return
        logging.info("Placed products in the cart: %s", self._product_count-count)
        products_wrapper = self._agent.find_element(FireFoxWebAgent.CLASS, 'featured-collection-module-body.new-arrivals.homepage-flex')
        product_div = None
        children = self._agent.children(products_wrapper, FireFoxWebAgent.CSS, '.catalog-list-item')
        for child in children:
            key = child.get_attribute('class')
            if key not in visited:
                visited.add(key)
                product_div = child
                break
        if product_div is None:
            return
        
        self._make_product_visible(product_div,'new-arrivals')
        product = self._agent.find_child(product_div, FireFoxWebAgent.CLASS, 'catalog-list-item-container')
        self._agent.click(product)

        ''' select size of the product if there is any '''
        size_elements = self._agent.find_elements(FireFoxWebAgent.CLASS, "swatch-element", 10)
        n = len(size_elements)
        logging.info('Found %s sizes', str(n))
        if n>0:
            size_elements = list(filter(lambda e: 'available' in e.get_attribute('class'), size_elements))
            n = len(size_elements)
            if n > 0:
                self._agent.click(size_elements[0])
                logging.info('Found % available sizes', str(n))
            else:
                logging.info('No available sizes found')
                self.__register_products_in_cart(count, visited)
        
        
        add_to_cart_btn = self._agent.find_element(FireFoxWebAgent.ID, 'addToCart')
        add_to_cart_btn.click()
        
        
        ''' close cart'''
        close_cart = self._agent.find_element(FireFoxWebAgent.CLASS, 'ajaxifyCart--close')
        self._agent.click(close_cart)
        self.__register_products_in_cart(count-1, visited)

    def _make_product_visible(self,product_div,collection):
        '''
        Makes product visible by paginating to the left or to the right, depending where the product is.
        This function recognizes where it has to paginate
        '''
        num = int(re.sub(r'[^0-9]', '', product_div.get_attribute('class')))
        if num <= 4: new_pag = 0
        elif num>4 and num <=8: new_pag = 1
        else: new_pag = 2
        if new_pag > 0:
            for i in range(0,new_pag):
                self._paginate_collection_right(collection)
        
    def _paginate_collection_right(self,collection):
        '''
        paginate right
        '''
        logging.info("Paginating %s collection to the right", collection)
        btn = self._agent.find_element(FireFoxWebAgent.XPATH, "//button[@class='collection-paginate-right'][@data-collection='{0}']".format(collection))
        self._agent.click(btn)
        
    def _paginate_collection_left(self,collection):
        '''
        paginate left
        '''
        logging.info("Paginating %s collection to the left", collection)
        btn = self._agent.find_element(FireFoxWebAgent.XPATH, "//button[@class='collection-paginate-left'][@data-collection='{0}']".format(collection))
        self._agent.click(btn)
        
if __name__ == '__main__':
    sites = ['http://donaldtrumppotus45.com/', 'http://iotwreport.com/', 'http://100percentfedup.com/', 'http://PerezHilton.com', 'http://www.tmz.com/']
    checker = AdsChecker(sites, product_count=3)
    'https://allegro.pl/'
    'https://www.rakuten.com'
    'https://www.groupon.com/cart'
    'https://www.lazada.com.my/cart/'
    'http://www.laredoute.com/pplp/100/157938/244/cat-271.aspx#shoppingtool=treestructureflyout'
    'http://www.sears.com'
    'https://www.bonprix.co.uk'
    'https://www.toysrus.com'
    checker.start()
    
    