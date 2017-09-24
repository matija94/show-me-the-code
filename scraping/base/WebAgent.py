from selenium import webdriver
from selenium.common.exceptions import WebDriverException,\
    ElementNotInteractableException, TimeoutException
import time
from selenium.webdriver.firefox.webelement import FirefoxWebElement
from utils.UrlUtils import UrlUtils
from _collections import defaultdict
from bs4.element import Tag
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys
from base.exceptions.not_found import ElementNotFound

class WebAgent:
    
    CSS=1
    CLASS=2
    ID=3
    XPATH=4
    
    FIREFOX_DRIVER = 10
    CHROME_DRIVER = 11
    
    def __init__(self,impl=WebAgent.CHROME_DRIVER,exe_path="chromedriver", profile_path='/home/matija/.config/chromium/bot'):
        if impl == WebAgent.CHROME_DRIVER:
            config = webdriver.ChromeOptions()
            config.add_argument('start-maximized')
            config.add_argument('user-data-dir={0}'.format(profile_path))
            config.add_argument('--incognito')
            self._driver = webdriver.Chrome(executable_path=exe_path,chrome_options=config)
        elif impl == WebAgent.FIREFOX_DRIVER:
            self._driver = webdriver.Firefox(executable_path=exe_path)
        #self._driver.set_window_size(window_height, window_width)
        
    def connect(self, url, timeout=60):
        '''
        Loads web page in current window. Opens new one if no windows exist
        '''
        self.__action(timeout, TimeoutException, self._driver.get,url)
        
    def open_window(self, url=None, focus=True):
        '''
        Open up new window
        If url is not specified blank window will be opened
        '''
        last_window_index = self._get_current_window_handle_index()
        self._driver.execute_script("window.open('');")
        self._driver.switch_to.window(self._driver.window_handles[-1])
        if url:
            self.connect(url)
        if not focus:
            self._driver.switch_to.window(self._driver.window_handles[last_window_index])
            
    def find_element(self, id_type, value, timeout=30, element=None):
        '''
        Looks for the element until it is found
        
        id_type is integer value which represents based on what lookup will be performed
        value is string representation for the lookup
        element represents upon which element search will be applied on. Default is firefox driver
        
        Returns element if found.
        Raises ValueError id id_type is not valid
        Raises ElementNotFound if element was not found after 'timeout' seconds
        '''
        if element is None:
            element = self._driver
        try:
            e = self.__action(timeout, WebDriverException, self._find_element,id_type,value,element)
        except TimeoutError:
            raise ElementNotFound()
        return e
    
    def find_elements(self, id_type, value, timeout=30, element=None):
        '''
        Uses same logic as find_element function but it does look for more than one
        '''
        if element is None:
            element = self._driver
        try:
            e = self.__action(timeout, WebDriverException, self._find_element,id_type,value,element)
        except TimeoutError:
            raise ElementNotFound()
        return e
            
    def find_child(self, element, id_type, value):
        '''
        Looks for a child element
        '''
        if not isinstance(element, FirefoxWebElement):
            raise TypeError('element is not of proper type')
        return self.find_element(id_type, value, element=element)
    
    def children(self, element, id_type, value):
        '''
        Returns children of the element
        '''
        if not isinstance(element, FirefoxWebElement):
            raise TypeError('element is not of proper type')
        return self.find_elements(id_type, value, element=element)

    def sleep(self, sec):
        '''
        Puts agent on sleep for 'sec' seconds
        '''
        time.sleep(sec)
        
    def disconnect(self):
        '''
         Quits the driver and closes every associated window
        '''
        self._driver.quit()
    
    def screenshot(self, fname):
        '''
        Takes screenshot of current browser and saves it as PATH under fname path
        '''
        self._driver.save_screenshot(fname)
    
    def cookies(self, *specificDomains):
        '''
        Returns list of dictionaries as cookies for current session
        If specificDomains is specified then all cookies for this/these domain/s will be returned
        '''
        cookies = self._driver.get_cookies()
        ans = ()
        specific_cookies = defaultdict(list)
        if specificDomains:
            for cookie in cookies:
                for specificDomain in specificDomains:
                    if UrlUtils.get_domain(cookie['domain']) == specificDomain:
                        specific_cookies[specificDomain].append(cookie)

            for specificDomain in specificDomains:
                ans = ans + (specific_cookies[specificDomain],)
            return ans
        return cookies
    
    def delete_cookies(self,*specificDomains):
        '''
        Deletes all cookies
        '''
        self._driver.delete_all_cookies()
    
    def back(self):
        '''
        Goes to the previous page in history
        '''
        self._driver.execute_script("window.history.go(-1)")
 
    def reload(self):
        '''
        Reloads page on current focused window(tab)
        '''
        self._driver.refresh()
    
    def click(self,e,timeout=30):
        '''
        Clicks on FirefoxWebElement 'e'
        
        This function wraps click driver function, although it guards against ElementNotInteractableException for 'timeout' seconds.
        
        Raises TimeoutError if timeout is exceeded
        
        
        usage:
            usually user will want to click X(cancel) pop-up which cancel button could be found within HTML code but is not clickable until pop-up is shown in the browser 
        '''
        if not isinstance(e, FirefoxWebElement):
            if not isinstance(e, Tag):
                raise TypeError('not fire fox web element neither Tag')
            else:
                e = self.find_element(WebAgent.XPATH, self._build_xpath(e))
        self.__action(timeout, ElementNotInteractableException, e.click)
        
    def close_popup(self):
        '''
        Closes pop up by simulating ESC keyboard button pressed
        '''
        ActionChains(self._driver).send_keys(Keys.ESCAPE).perform()
        
    def page_source(self):
        '''
        Returns page source of the current loaded page in the driver
        '''
        return self._driver.page_source
   
    def current_url(self):
        ''' returns current active url '''
        return self._driver.current_url
        
    ''' ### PRIVATE FUNCTIIONS ### '''
    def __action(self,timeout,exception,function,*args):
        init_time = time.time()
        while True:
            if (time.time() - init_time)>timeout:
                raise TimeoutError('timed out')
            try:
                ans=function(*args)
                return ans
            except exception:
                continue
            
    def _find_element(self,id_type,value,e):
        if id_type==WebAgent.CLASS:
            return e.find_element_by_class_name(value)
        elif id_type==WebAgent.ID:
            return e.find_element_by_id(value)
        elif id_type==WebAgent.CSS:
            return e.find_element_by_css_selector(value)
        elif id_type==WebAgent.XPATH:
            return e.find_element_by_xpath(value)
        else:
            raise ValueError('id_type is invalid')
    
    def _find_elements(self,id_type,value,e):
        if id_type==WebAgent.CLASS:
            return e.find_elements_by_class_name(value)
        elif id_type==WebAgent.ID:
            return e.find_elements_by_id(value)
        elif id_type==WebAgent.CSS:
            return e.find_elements_by_css_selector(value)
        elif id_type==WebAgent.XPATH:
            return e.find_elements_by_xpath(value)
        else:
            raise ValueError('id_type is invalid')
        
    def _get_current_window_handle_index(self):
        for index,window_handle in enumerate(self._driver.window_handles):
            if window_handle == self._driver.current_window_handle:
                return index
        raise ValueError('No window handles')
    
    def _build_xpath(self,bs4_tag):
        ''' builds css selector out of bs4.Tag instance '''
        ans = ['//'+bs4_tag.name]
        attrs = bs4_tag.attrs
            
        if attrs is None or len(attrs) == 0:
            return None
        if 'id' in attrs:
            return ans.pop() + "[@id='{0}']".format(attrs['id'])
        for key, value in attrs.items():
            if isinstance(value, list):
                value = ' '.join(value)
            ans.append("[@{0}='{1}']".format(key,value))
        return ''.join(ans)
    
    
    
            