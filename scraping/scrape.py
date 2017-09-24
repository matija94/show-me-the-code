from selenium import webdriver
from generators.implementation import SSNGen, AddressGenerator
import time
import random
from selenium.common.exceptions import NoSuchElementException

class Optimum:
        
    def __init__(self, url, address, city, zip_code, fname, lname, phone, email):
        self._driver = webdriver.Firefox()
        self._driver.set_window_size(1120, 550)
        self._root = url
        self._ssn = SSNGen()
        self._adrgen = AddressGenerator(address, zip_code)
        self._city = city
        self._fname = fname
        self._lname = lname
        self._phone = phone
        self._email = email


        
    def _connect(self):
        self._driver.get(self._root)
    
        
    def run(self, counter):
        if counter<=0:return
        
        self._connect()
        
        ''' fill in demographic data '''
        time.sleep(10)
        input_address = self._driver.find_element_by_id('streetAddress')
        input_city = self._driver.find_element_by_id('city')
        input_code = self._driver.find_element_by_id('zipCode')
        address = self._adrgen.get_address()
        print('Setting address {0}'.format(address))
        input_address.send_keys(address)
        print('Setting city {0}'.format(self._city))
        input_city.send_keys(self._city)
        zip_code = self._adrgen.get_code()
        print('Setting zip code {0}'.format(zip_code))
        input_code.send_keys(zip_code)
        print('Proceeding to offers')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary pull-right'][text()='Continue']")
        button.click()
        time.sleep(20)

        ''' get random offer '''
        offers = self._get_offers()
        n = len(offers)
        r = random.Random()
        index = r.randrange(0, n)
        print('Proceeding to customize offer')
        offers[index].click()
        time.sleep(20)
 
        ''' customize offer '''
        tv_count = self._driver.find_element_by_id('tv-count')
        tv_count.find_element_by_css_selector("option[label='1']").click() # select tv count to 1
        self._driver.find_elements_by_css_selector("i[class='fa fa-circle unselected fa-stack-1x']")[1].click()
        print('Proceeding to customer info')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary'][contains(text(), 'Continue')]")
        button.click()
        time.sleep(20)


        ''' fill in customer information '''
        self._send_customer_info(self._fname, self._lname, self._phone, self._email)
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary btn-form-submit'][contains(text(), 'Continue')]")
        button.click()
        time.sleep(20)
        self._driver.find_element_by_css_selector("input[name='ssn']").send_keys(self._ssn.get_ssn())
        print('Proceeding to picking date')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary btn-form-submit'][contains(text(), 'Continue')]")
        button.click()
        time.sleep(20)


        ''' pick installation date '''                
        checkboxes = self._driver.find_elements_by_css_selector("div[class='radio radio-checkbox']")
        checkboxes[1].click()
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary btn-form-submit'][contains(text(), 'place order')]")
        button.click()
        time.sleep(20)
        
        ''' extract order data '''
        order_no = self._driver.find_element_by_xpath("//div[@class='confirmation-number']/span[2]").text
        body_text = self._driver.find_element_by_css_selector('body').text
        
        with open('/home/matija/Desktop/offers/ORDER_{0}.txt'.format(order_no), 'w') as out:
            out.write(body_text)
            
        self._driver.save_screenshot("/home/matija/Desktop/offers/ORDER_{0}.png".format(order_no))
        self.run(counter-1)
            
    def _get_offers(self):
        offers_wrapper = self._driver.find_element_by_class_name('offers-flex')
        offers = offers_wrapper.find_elements_by_css_selector('.btn.btn-primary.offer-select')
        return offers
    
    def _send_customer_info(self, fname, lname,phone,email):
        self._driver.find_element_by_css_selector("input[name='firstName']").send_keys(fname)
        self._driver.find_element_by_css_selector("input[name='lastName']").send_keys(lname)
        self._driver.find_element_by_css_selector("input[name='phone']").send_keys(phone)
        self._driver.find_element_by_css_selector("input[name='email']").send_keys(email)
        try:
            self._driver.find_element_by_css_selector("select[name='state']").find_element_by_css_selector("option[value='32']").click()
        except NoSuchElementException:
            pass
        
class SuddenLink:
    
    def __init__(self, url, address, city, zip_code, fname, lname, phone, email):
        self._driver = webdriver.Firefox()
        self._driver.set_window_size(1120, 550)
        self._root = url
        self._ssn = SSNGen()
        self._adrgen = AddressGenerator(address, zip_code)
        self._city = city
        self._fname = fname
        self._lname = lname
        self._phone = phone
        self._email = email
        
    def _connect(self):
        self._driver.get(self._root)
    
        
    def run(self, counter):
        if counter<=0:return
        
        self._connect()
        
        ''' fill in demographic data '''
        time.sleep(10)
        input_address = self._driver.find_element_by_id('streetAddress')
        input_city = self._driver.find_element_by_id('city')
        input_code = self._driver.find_element_by_id('zipCode')
        address = self._adrgen.get_address()
        print('Setting address {0}'.format(address))
        input_address.send_keys(address)
        print('Setting city {0}'.format(self._city))
        input_city.send_keys(self._city)
        zip_code = self._adrgen.get_code()
        print('Setting zip code {0}'.format(zip_code))
        input_code.send_keys(zip_code)
        print('Proceeding to offers')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-success btn-green pull-right'][contains(text(), 'continue')]")
        button.click()
        time.sleep(30)

        ''' get random offer '''
        offers = self._get_offers()
        n = len(offers)
        r = random.Random()
        index = r.randrange(0, n)
        print('Proceeding to customize offer')
        offers[index].click()
        time.sleep(20)
 
        ''' customize offer '''
        tv_count = self._driver.find_element_by_css_selector("select[name='tvCount']")
        tv_count.find_element_by_css_selector("option[label='1']").click() # select tv count to 1
        
        self._driver.find_elements_by_css_selector("i[class='fa fa-circle-o fa-stack-1x']")[8].click()
        time.sleep(2)
        self._driver.find_element_by_css_selector("img[class='suppress-wifi-advice']").click()
        time.sleep(2)
        print('Proceeding to customer info')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-success btn-green'][contains(text(), 'continue')]")
        button.click()
        time.sleep(20)


        ''' fill in customer information '''
        self._send_customer_info(self._fname, self._lname, self._phone, self._email)
        print('Proceeding to picking date')
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-success btn-green btn-form-submit'][contains(text(), 'continue')]")
        button.click()
        time.sleep(20)
        #''' ssn '''
        #self._driver.find_element_by_css_selector("input[name='ssn']").send_keys(self._ssn.get_ssn())
        #button = self._driver.find_element_by_xpath("//button[@class='btn btn-primary btn-form-submit'][contains(text(), 'Continue')]")
        #button.click()
        #time.sleep(20)


        ''' pick installation date '''                
        checkboxes = self._driver.find_elements_by_css_selector("div[class='radio']")
        checkboxes[1].click()
        button = self._driver.find_element_by_xpath("//button[@class='btn btn-success btn-green btn-form-submit'][contains(text(), 'place order')]")
        button.click()
        time.sleep(20)
        
        ''' extract order data '''
        order_no = self._driver.find_element_by_xpath("//div[@class='confirmation-number']/span[2]").text
        body_text = self._driver.find_element_by_css_selector('body').text
        
        with open('/home/matija/Desktop/offers/ORDER_{0}.txt'.format(order_no), 'w') as out:
            out.write(body_text)
            
        self._driver.save_screenshot("/home/matija/Desktop/offers/ORDER_{0}.png".format(order_no))
        self.run(counter-1)
            
    def _get_offers(self):
        return self._driver.find_elements_by_xpath("//button[@class='btn btn-success btn-green offer-select'][text()='choose']")
    
    def _send_customer_info(self, fname, lname,phone,email):
        self._driver.find_element_by_css_selector("input[name='firstName']").send_keys(fname)
        self._driver.find_element_by_css_selector("input[name='lastName']").send_keys(lname)
        self._driver.find_element_by_css_selector("input[name='phone']").send_keys(phone)
        self._driver.find_element_by_css_selector("input[name='email']").send_keys(email)
        self._driver.find_element_by_xpath("//select[@name='secretQuestion']/option[2]").click()
        self._driver.find_element_by_css_selector("input[name='secretAnswer']").send_keys('Mike')
        #try:
            #self._driver.find_element_by_css_selector("select[name='state']").find_element_by_css_selector("option[value='32']").click()
        #except NoSuchElementException:
            #pass
    
if __name__ == '__main__':
    s = SuddenLink('https://order.suddenlink.com/Buyflow/Storefront','3176 Kelly Drive', 'Greenville, WV', '24945','mat', 'lukovic', '3223115678', 'mat@gmail.com')
    s.run(50)

