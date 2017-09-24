import time
import datetime
import os
import csv
import requests
from selenium import webdriver
from bs4 import BeautifulSoup
import collections

list_of_books = {'Science' : {"The Science of Nutrition (4th Edition)":["http://www.mypearsonstore.com/bookstore/science-of-nutrition-9780134175096","https://www.amazon.com/Science-Nutrition-4th-Janice-Thompson/dp/0134175093/ref=sr_1_1?s=books&ie=UTF8&qid=1497975220&sr=1-1&keywords=0134175093","https://www.barnesandnoble.com/w/the-science-of-nutrition-janice-j-thompson/1124174329?ean=9780134175096","http://www.chegg.com/textbooks/the-science-of-nutrition-4th-edition-9780134175096-0134175093?trackid=228577ea&strackid=227d81ec"],"Campbell Biology (11th Edition) ":["http://www.mypearsonstore.com/bookstore/campbell-biology-plus-masteringbiology-with-pearson-9780134082318","https://www.amazon.com/Campbell-Biology-11th-Lisa-Urry/dp/0134093410/ref=sr_1_1?s=books&ie=UTF8&qid=1497974718&sr=1-1&keywords=0134093410","https://www.barnesandnoble.com/w/campbell-biology-lisa-a-urry/1125430244?ean=9780134093413","http://www.chegg.com/textbooks/campbell-biology-11th-edition-9780134093413-0134093410?trackid=0ae7dbaa&strackid=0fef0b5f"],"Corporate Finance, 4th Edition":["http://www.mypearsonstore.com/bookstore/corporate-finance-9780134083278","https://www.amazon.com/Corporate-Finance-4th-Pearson-Standalone/dp/013408327X/ref=sr_1_1?s=books&ie=UTF8&qid=1497298276&sr=1-1&keywords=Corporate+Finance%2C+4th+Edition","https://www.barnesandnoble.com/w/corporate-finance-jonathan-berk/1124176693?ean=9780134083278","http://www.chegg.com/textbooks/corporate-finance-3rd-edition-9780133097894-0133097897?trackid=4d4c232e&strackid=7d355fae&ii=4"],"Human Anatomy, 8th Edition":["http://www.mypearsonstore.com/bookstore/human-anatomy-plus-masteringap-with-pearson-etext-access-0134215036","https://www.amazon.com/Anatomy-MasteringA-Pearson-Access-Package/dp/0134215036/ref=sr_1_8?ie=UTF8&qid=1497707014&sr=8-8&keywords=Human+Anatomy%2C+8th+Edition","https://www.barnesandnoble.com/w/human-anatomy-elaine-n-marieb/1124175210?ean=9780134243818","http://www.chegg.com/textbooks/human-anatomy-8th-edition-9780134243818-0134243811?trackid=67646d2c&strackid=4ff4718c&ii=5"], "Microbiology: An Introduction, 12th Edition":["http://www.mypearsonstore.com/bookstore/microbiology-an-introduction-plus-masteringmicrobiology-9780321928924","https://www.amazon.com/Microbiology-Introduction-Gerard-J-Tortora/dp/0321929152/ref=sr_1_1?ie=UTF8&qid=1497877870&sr=8-1&keywords=0321929152","http://www.chegg.com/textbooks/microbiology-12th-edition-9780321929150-0321929152?trackid=4c31d416&strackid=3d11b141","https://www.barnesandnoble.com/w/microbiology-gerard-j-tortora/1100835468?ean=9780321929150"],"Chemistry: A Molecular Approach, 4th Edition":["http://www.mypearsonstore.com/bookstore/chemistry-a-molecular-approach-9780134112831","https://www.amazon.com/Chemistry-Molecular-Approach-Nivaldo-Tro/dp/0134112830/ref=sr_1_1?ie=UTF8&qid=1497882180&sr=8-1&keywords=0134112830","https://www.barnesandnoble.com/w/chemistry-nivaldo-j-tro/1124174711?ean=9780134112831","http://www.chegg.com/textbooks/chemistry-4th-edition-9780134112831-0134112830?trackid=18d77fca&strackid=6d58ed38"],"Statistics: Informed Decisions Using Data, 5th Edition":["http://www.mypearsonstore.com/bookstore/statistics-informed-decisions-using-data-plus-mystatlab-0134135369","https://www.amazon.com/Statistics-Informed-Decisions-Using-Data/dp/0134133536/ref=sr_1_1?ie=UTF8&qid=1497883149&sr=8-1&keywords=0134133536","https://www.barnesandnoble.com/w/statistics-michael-sullivan/1100835545?ean=9780134133539","http://www.chegg.com/textbooks/statistics-5th-edition-9780134133539-0134133536?trackid=3799e1da&strackid=35ab70e8"],"Organic Chemistry (8th Edition)":["http://www.mypearsonstore.com/bookstore/organic-chemistry-9780134042282","https://www.amazon.com/Organic-Chemistry-Paula-Yurkanis-Bruice/dp/013404228X/ref=sr_1_1?ie=UTF8&qid=1497883558&sr=8-1&keywords=013404228X","https://www.barnesandnoble.com/w/organic-chemistry-paula-yurkanis-bruice/1124174698?ean=9780134042282","http://www.chegg.com/textbooks/organic-chemistry-8th-edition-9780134042282-013404228x?trackid=3cda4ce4&strackid=3f7a93c6"],"Physics for Scientists and Engineers: A Strategic Approach with Modern Physics (4th Edition)":["http://www.mypearsonstore.com/bookstore/physics-for-scientists-and-engineers-a-strategic-approach-0133953149","https://www.amazon.com/Physics-Scientists-Engineers-Strategic-Approach/dp/0133942651/ref=sr_1_1?ie=UTF8&qid=1497884078&sr=8-1&keywords=0133942651","https://www.barnesandnoble.com/w/physics-for-scientists-and-engineers-randall-d-knight-professor-emeritus/1124174694?ean=9780133942651","http://www.chegg.com/textbooks/physics-for-scientists-and-engineers-4th-edition-9780133942651-0133942651?trackid=52af05b2&strackid=7f21ec5b"]},"College Math":{"Introductory Algebra for College Students, 7th Edition":["http://www.mypearsonstore.com/bookstore/introductory-algebra-for-college-students-9780134178059","https://www.amazon.com/Introductory-Algebra-College-Students-7th/dp/013417805X/ref=sr_1_1?ie=UTF8&qid=1497884246&sr=8-1&keywords=013417805X","https://www.barnesandnoble.com/w/introductory-algebra-for-college-students-robert-f-blitzer/1124175196?ean=9780134178059","http://www.chegg.com/textbooks/introductory-algebra-for-college-students-7th-edition-9780134178059-013417805x?trackid=4c782b7b&strackid=75671b29"],"Statistics: Informed Decisions Using Data, 5th Edition":["http://www.mypearsonstore.com/bookstore/statistics-informed-decisions-using-data-plus-mystatlab-0134135369","https://www.amazon.com/Statistics-Informed-Decisions-Using-Data/dp/0134133536/ref=sr_1_1?ie=UTF8&qid=1497883149&sr=8-1&keywords=0134133536","https://www.barnesandnoble.com/w/statistics-michael-sullivan/1100835545?ean=9780134133539","http://www.chegg.com/textbooks/statistics-5th-edition-9780134133539-0134133536?trackid=3799e1da&strackid=35ab70e8"]},"Engineering":{"Engineering Mechanics: Dynamics, 14th Edition":["http://www.mypearsonstore.com/bookstore/engineering-mechanics-dynamics-plus-masteringengineering-0134116992","https://www.amazon.com/Engineering-Mechanics-Dynamics-Russell-Hibbeler/dp/0133915387/ref=sr_1_1?ie=UTF8&qid=1497884547&sr=8-1&keywords=0133915387","https://www.barnesandnoble.com/w/engineering-mechanics-russell-c-hibbeler/1124301221?ean=9780133915389","http://www.chegg.com/textbooks/engineering-mechanics-14th-edition-9780133915389-0133915387?trackid=65d51ae9&strackid=1d9fadb5"],"Technology In Action Complete (14th Edition) ":["http://www.mypearsonstore.com/bookstore/technology-in-action-complete-9780134608228","https://www.amazon.com/Technology-Action-Complete-Martin-Poatsy/dp/0134608224/ref=sr_1_1?ie=UTF8&qid=1498121470&sr=8-1&keywords=0134608224","https://www.barnesandnoble.com/w/technology-in-action-complete-alan-evans/1126356180?ean=9780134608228","http://www.chegg.com/textbooks/technology-in-action-complete-14th-edition-9780134608228-0134608224?trackid=601187c9&strackid=27f2cc10"],"Technology In Action Complete (13th Edition) ":["http://www.mypearsonstore.com/bookstore/technology-in-action-complete-0134289102","https://www.amazon.com/Technology-Action-Complete-Martin-Poatsy/dp/0134289102/ref=sr_1_1?ie=UTF8&qid=1497884707&sr=8-1&keywords=0134289102","https://www.barnesandnoble.com/w/technology-in-action-complete-alan-evans/1124175822?ean=9780134289106","http://www.chegg.com/textbooks/technology-in-action-complete-13th-edition-9780134289106-0134289102?trackid=4cea016b&strackid=0fbd6811"]},"Computer Science":{"C++ How to Program (10th Edition) ":["http://www.mypearsonstore.com/bookstore/c-plus-plus-how-to-program-plus-myprogramminglab-with-0134583000","https://www.amazon.com/How-Program-10th-Paul-Deitel/dp/0134448235/ref=sr_1_1?ie=UTF8&qid=1497884843&sr=8-1&keywords=0134448235","https://www.barnesandnoble.com/w/c-how-to-program-paul-deitel/1124175649?ean=9780134448237","http://www.chegg.com/textbooks/c-how-to-program-10th-edition-9780134448237-0134448235?trackid=5105102c&strackid=153a9002"],"Starting Out with Java: From Control Structures through Objects (6th Edition) ":["http://www.mypearsonstore.com/bookstore/starting-out-with-java-from-control-structures-through-0134059875","https://www.amazon.com/Starting-Out-Java-Control-Structures/dp/0133957055/ref=sr_1_1?ie=UTF8&qid=1497885039&sr=8-1&keywords=0133957055","https://www.barnesandnoble.com/w/starting-out-with-java-tony-gaddis/1124301261?ean=9780133957051","http://www.chegg.com/textbooks/starting-out-with-java-6th-edition-9780133957051-0133957055?trackid=45b19ffd&strackid=5e763c9f"]},"Business & economics":{"Organizational Behavior (17th Edition) ":["http://www.mypearsonstore.com/bookstore/organizational-behavior-9780134103983","https://www.amazon.com/Organizational-Behavior-17th-Standalone-book/dp/013410398X/ref=sr_1_1?ie=UTF8&qid=1497885189&sr=8-1&keywords=013410398X","https://www.barnesandnoble.com/w/organizational-behavior-stephen-p-robbins/1124175156?ean=9780134103983","http://www.chegg.com/textbooks/organizational-behavior-17th-edition-9780134103983-013410398x?trackid=5198d65a&strackid=6d087506"],"Marketing: An Introduction, 13th Edition":["http://www.mypearsonstore.com/bookstore/marketing-an-introduction-plus-2017-mymarketinglab-013478734X","https://www.amazon.com/Marketing-Introduction-13th-Gary-Armstrong/dp/013414953X/ref=sr_1_1?ie=UTF8&qid=1497885344&sr=8-1&keywords=013414953X","https://www.barnesandnoble.com/w/marketing-gary-armstrong/1100056885?ean=9780134149530","http://www.chegg.com/textbooks/marketing-13th-edition-9780134149530-013414953x?trackid=4795d1d5&strackid=058b8f69"],"Principles of Microeconomics, 12th Edition":["http://www.mypearsonstore.com/bookstore/principles-of-microeconomics-plus-myeconlab-with-pearson-0134435036","https://www.amazon.com/Principles-Microeconomics-12th-Karl-Case/dp/0134078810/ref=sr_1_1?ie=UTF8&qid=1497885450&sr=8-1&keywords=0134078810","https://www.barnesandnoble.com/w/principles-of-microeconomics-karl-e-case/1124174921?ean=9780134078816","http://www.chegg.com/textbooks/principles-of-microeconomics-12th-edition-9780134078816-0134078810?trackid=626b1f0f&strackid=465c99ce"]},"social science":{"The Good Society: An Introduction to Comparative Politics (3rd Edition)":["http://www.mypearsonstore.com/bookstore/good-society-an-introduction-to-comparative-politics-9780133974850","https://www.amazon.com/Good-Society-Introduction-Comparative-Politics/dp/0133974855/ref=sr_1_1?s=books&ie=UTF8&qid=1497974478&sr=1-1&keywords=0133974855","https://www.barnesandnoble.com/w/the-good-society-alan-draper/1124301279?ean=9780133974850","http://www.chegg.com/textbooks/the-good-society-3rd-edition-9780133974850-0133974855?trackid=2ee41d93&strackid=18e2e84e"],"Society: The Basics, 14th Edition":["http://www.mypearsonstore.com/bookstore/society-the-basics-plus-new-mysoclab-for-introduction-9780134226996","https://www.amazon.com/Society-Basics-14th-John-Macionis/dp/0134206320/ref=sr_1_1?ie=UTF8&qid=1497885532&sr=8-1&keywords=0134206320","https://www.barnesandnoble.com/w/society-john-j-macionis/1100057474?ean=9780134206325","http://www.chegg.com/textbooks/society-14th-edition-9780134206325-0134206320?trackid=0f27f686&strackid=73204e5a"],"Essentials of Sociology, 12th Edition":["http://www.mypearsonstore.com/bookstore/essentials-of-sociology-plus-new-mysoclab-for-introduction-0134495926","https://www.amazon.com/Essentials-Sociology-12th-James-Henslin/dp/0134205588/ref=sr_1_1?ie=UTF8&qid=1497885638&sr=8-1&keywords=0134205588","https://www.barnesandnoble.com/w/essentials-of-sociology-james-m-henslin/1124174762?ean=9780134205588","http://www.chegg.com/textbooks/essentials-of-sociology-12th-edition-9780134205588-0134205588?trackid=6ac84dff&strackid=545639e0"]},"Others":{"International Relations, 11th Edition":["http://www.mypearsonstore.com/bookstore/international-relations-9780134404769","https://www.amazon.com/International-Relations-11th-Jon-Pevehouse/dp/0134404769/ref=sr_1_1?ie=UTF8&qid=1497885750&sr=8-1&keywords=0134404769","https://www.barnesandnoble.com/w/international-relations-jon-c-pevehouse/1124175264?ean=9780134404769","http://www.chegg.com/textbooks/international-relations-11th-edition-9780134404769-0134404769?trackid=07cc6682&strackid=7c06002b"],"The Philosopher's Way: Thinking Critically About Profound Ideas (5th Edition)" : ["http://www.mypearsonstore.com/bookstore/philosophers-way-thinking-critically-about-profound-0133909506","https://www.amazon.com/Philosophers-Way-Thinking-Critically-Profound/dp/0133867544/ref=sr_1_1?s=books&ie=UTF8&qid=1497974199&sr=1-1&keywords=0133867544","https://www.barnesandnoble.com/w/the-philosophers-way-john-chaffee/1124174027?ean=9780133867541","http://www.chegg.com/textbooks/the-philosopher-s-way-5th-edition-9780133867541-0133867544?trackid=46ebb55d&strackid=06d6d2f4"]}}
list_of_books = collections.OrderedDict(list_of_books)

# list_of_books = {'Science' : {"Ethics: Theory and Practice, Books a la Carte, 11th Edition":["http://www.mypearsonstore.com/bookstore/ethics-theory-and-practice-books-a-la-carte-9780134010205","https://www.amazon.com/Ethics-Theory-Practice-Books-Carte/dp/0134010205/ref=sr_1_1?ie=UTF8&qid=1497886130&sr=8-1&keywords=0134010205","https://www.barnesandnoble.com/w/ethics-jacques-p-thiroux/1124176271?ean=9780134010205","http://www.chegg.com/textbooks/ethics-11th-edition-9780134010205-0134010205?trackid=5c0d27d7&strackid=149e3088"]}}

def pearson(url):
    r  = requests.get(url)
    data = r.text
    soup = BeautifulSoup(data,"html.parser")
    # print(soup)
    try:
        pubdate = soup.find('p',{'class':'pubdate'}).text.strip().split(',')[1].replace(' ','')
    except:
        pubdate = "Not Found"
    # print(pubdate)
    mainContainer = soup.find('div',{'class':'buybox'})
    # print(mainContainer)
    try:
        digital_Container = mainContainer.find('div',{'id':'digital'}).find_all('div',{'id':'genericChoice'})
    except:
        digital_Container = []
        
    if digital_Container ==[]:
        eText_price = "Not Found"
        website_price = "Not Found"
        digital_package = "Not Found"
    else:
        try:
            eText_price = "Not Found"
            website_price = "Not Found"
            digital_package = "Not Found"
            for item in digital_Container:
                if "Subscription" in item.find('p',{'class':'title'}).text.strip():
                    eText_price = item.find('span',{'class':'price'}).text.strip()
                    # print('eText',eText_price)
                if "without Pearson" in item.find('p',{'class':'title'}).text.strip():
                     website_price = item.find('span',{'class':'price'}).text.strip()
                if "with Pearson" in item.find('p',{'class':'title'}).text.strip() and "Instant" in item.find('p',{'class':'title'}).text.strip():
                     digital_package = item.find('span',{'class':'price'}).text.strip()
            if(website_price=="Not Found" and digital_package == "Not Found" and len(digital_Container)>1):
                website_price = digital_Container[-1].find('span',{'class':'price'}).text.strip()
        except:
            eText_price = "Not Found"
            website_price = "Not Found"
            digital_package = "Not Found"
    try:
        package_value_price = "Not Found"
        package_standard_price = "Not Found"
        package_Container = mainContainer.find('div',{'id':'digitalprint'}).find_all('div',{'id':'genericChoice'})
        for item in package_Container:
            if "la Carte" in item.find('p',{'class':'title'}).text.strip() or "La Carte" in item.find('p',{'class':'title'}).text.strip() or "Value Edition" in item.find('p',{'class':'title'}).text.strip():
                package_value_price = item.find('span',{'class':'price'}).text.strip()
        if package_value_price=="Not Found":
            package_standard_price = package_Container[0].find('span',{'class':'price'}).text.strip()
        elif package_value_price!="Not Found" and len(package_Container)>1:
            package_standard_price = package_Container[1].find('span',{'class':'price'}).text.strip()
    except:
        package_value_price = "Not Found"
        package_standard_price = "Not Found"

    try:
        print_Container = mainContainer.find('div',{'id':'print'}).find_all('div',{'id':'genericChoice'})
    except:
        print_Container = []

    if print_Container==[]:
        print_price_value = "Not Found"
        print_price_standard = "Not Found"
    else:
        print_price_value = "Not Found"
        print_price_standard = "Not Found"
        try:
            for item in print_Container:
                if package_standard_price == "Not Found" and "with Pearson eText" in item.find('p',{'class':'title'}).text.strip():
                    package_standard_price = item.find('span',{'class':'price'}).text.strip()
                if website_price == "Not Found" and "without Pearson eText" in item.find('p',{'class':'title'}).text.strip():
                    website_price = item.find('span',{'class':'price'}).text.strip()
                if "La Carte" in item.find('p',{'class':'title'}).text.strip() or "la Carte" in item.find('p',{'class':'title'}).text.strip() or "Value Edition" in item.find('p',{'class':'title'}).text.strip():
                    print_price_value = item.find('span',{'class':'price'}).text.strip()
        except:
            print_price_value = "Not Found"

        try:
            if(print_price_value=="Not Found"):
                print_price_standard = print_Container[0].find('span',{'class':'price'}).text.strip()
            elif(len(print_Container)>6):
                print_price_standard = print_Container[-2].find('span',{'class':'price'}).text.strip()
                package_standard_price = print_Container[-1].find('span',{'class':'price'}).text.strip()
            elif(len(print_Container)>1 and print_price_value!="Not Found"):
                if("Laboratory" in print_Container[0].find('p',{'class':'title'}).text.strip()):
                    print_price_standard = print_Container[-1].find('span',{'class':'price'}).text.strip()
                else:
                    print_price_standard = print_Container[1].find('span',{'class':'price'}).text.strip()
            else:
                print_price_standard = "Not Found"
        except:
            print_price_standard = "Not Found"
        # print('Print_value',print_price_value)
        # print('Print_standard',print_price_standard)

    # print(print_price_standard,print_price_value,website_price,eText_price,package_price)

    return([print_price_standard,print_price_value,website_price,eText_price,digital_package,package_standard_price,package_value_price])
    # list_of_all.append(['Category','title',pubdate,print_price_standard,print_price_value,website_price,eText_price,package_price])

def amazon(url):
    r  = requests.get(url)
    data = r.text
    soup = BeautifulSoup(data,"html.parser")
    # print(soup)
    # title = soup.find('span',{'id':'productTitle'}).text.strip()
    # print(title)
    try:
        rent = soup.find('div',{'id':'rentOfferAccordionRow'}).find('h5').find_all('div',{'class':'a-column'})
        # rent_Title = rent[0].find('span',{'class':'a-size-medium'}).text.strip()
        rent_Price = rent[1].find('span',{'class':'a-size-medium'}).text.strip()
        # print(title,rent_Title,rent_Price)
        # list_of_all.append(['Corporate Finance',title,rent_Title,rent_Price])
    except:
        rent_Price = "Not Found"

    try:
        buy_used = soup.find('div',{'id':'usedOfferAccordionRow'}).find('h5').find_all('div',{'class':'a-column'})
        # buy_used_Title = buy_used[0].find('span',{'class':'a-size-medium'}).text.strip()
        buy_used_Price = buy_used[1].find('span',{'class':'a-size-medium'}).text.strip()
        # print(title,buy_used_Title,buy_used_Price)
        # list_of_all.append(['Corporate Finance',title,buy_used_Title,buy_used_Price])
    except:
        # print("Not found")
        buy_used_Price = "Not Found"
        # list_of_all.append(['Corporate Finance',title,"Buy Used","Not Found"])
    
    try:
        buy = soup.find('div',{'id':'newOfferAccordionRow'}).find('h5').find_all('div',{'class':'a-column'})
        # print(len(buy))
        # buy_Title = buy[0].find('span',{'class':'a-size-medium'}).text.strip()
        buy_Price = buy[1].find('span',{'class':'a-size-medium'}).text.strip()
        # print(title,buy_Title,buy_Price)
    except:
        buy_Price = "Not Found"

    return([rent_Price,buy_used_Price,buy_Price])


def barnes(url):
    # chrome_ops = webdriver.ChromeOptions()
    # chrome_ops.add_argument('--disable-extensions')
    # chrome_ops.add_argument('--profile-directory=Default')
    # chrome_ops.add_argument("--incognito")
    # chrome_ops.add_argument("--disable-plugins-discovery")
    # chrome_ops.add_argument("--start-maximized")
    # path_to_chromedriver = r'C:\chromedriver.exe' # change path as needed
    # browser = webdriver.Chrome(chrome_options=chrome_ops)
    # path_to_chromedriver = r'C:\chromedriver.exe' # change path as needed
    # browser = webdriver.Chrome(chrome_options=chrome_ops)
    # browser.set_page_load_timeout(5)
    # browser.get(url)
    # browser.refresh()
    # html = browser.page_source
    r  = requests.get(url)
    data = r.text
    soup = BeautifulSoup(data,"html.parser")
    # print(soup)
    try:
        isbn = soup.find('div',{'class':'textbook-product-details'}).find('div').find('p').text.strip().replace('ISBN-10:','').replace(' ','')
        # print(isbn)
    except:
        isbn = "Not Found"

    try:
        pubdate = soup.find('div',{'class':'textbook-product-details'}).find_all('div')
        pubyear = pubdate[1].find('p').text.strip().replace('Pub. Date:','').split('/')[-1]
        # print(pubyear)
    except:
        pubyear = "Not Found"
    # title = soup.find('h1',{'itemprop':'name'}).text.strip()
    mainContainer = soup.find('ul',{'class':'multi-product-select'})
    # print(title)
    # print(mainContainer)
    try:
        rent = mainContainer.find('select',{'id':'rentalRates'}).find('option')
        days = rent.text.strip().replace(u'\xa0',u'').replace('60 Days','-').replace('90 Days','-').replace('130 Days','-').split('--')
        days_60 = days[1]
        days_90 = days[2]
        days_130 = days[3]
    except:
        days_60 = "Not Found"
        days_90 = "Not Found"
        days_130 = "Not Found"

    try:
        buy = mainContainer.find_all('li')
        buy_new = "Not Found"
        buy_used = "Not Found"
        try:
            gotinto = 0
            for item in buy:
                buy_title = item.find('label').text.strip()
                if 'New' in buy_title:
                    buy_new = item.find('label').text.strip().replace('Buy New','').replace('\n','')
                    # print(buy_new)
                if 'Used' in buy_title:
                    buy_used = item.find('label').text.strip().replace('Buy Used','').replace('\n','')
                    # print(buy_used)
                    gotinto = 1
                if(gotinto==0):
                    if 'Marketplace' in buy_title:
                        buy_used = item.find('label').text.strip().replace('Marketplace from','').replace('\n','')
                        # print('Market',buy_used)
        except:
            buy_new = "Not Found"
            buy_used = "Not Found"
    except:
        buy_new = "Not Found"
        buy_used = "Not Found"

    # browser.close()
    return(isbn,pubyear,[buy_new,buy_used,days_60, days_90, days_130])





def chegg(url):
    chrome_ops = webdriver.ChromeOptions()
    chrome_ops.add_argument('--disable-extensions')
    chrome_ops.add_argument('--profile-directory=Default')
    chrome_ops.add_argument("--incognito")
    chrome_ops.add_argument("--disable-plugins-discovery")
    chrome_ops.add_argument("--start-maximized")
    path_to_chromedriver = r'C:\chromedriver.exe' # change path as needed
    browser = webdriver.Chrome(chrome_options=chrome_ops)
    # path_to_chromedriver = r'C:\chromedriver.exe' # change path as needed
    # browser = webdriver.Chrome(chrome_options=chrome_ops)
    browser.get(url)
    html = browser.page_source
    soup = BeautifulSoup(html,"html.parser")
    # print(soup)
    title = soup.find('h1',{'class':'txt-hdr-dark'}).text.strip()
    try:
        rent = soup.find('div',{'class':'rent'})
        # rent_Title = rent.find('div',{'class':'pricebox-header-label'}).text.strip()
        rent_Price = rent.find('div',{'class':'pricebox-header-price'}).text.strip().replace('from','')
        # print("rent",rent_Price)
    except:
        rent_Price = "Not Found"
        # print("rent","Not Found")
    try:
        eTextbook = soup.find('div',{'class':'etextbook'})
        # eTextbook_Title = eTextbook.find('div',{'class':'pricebox-header-label'}).text.strip()
        eTextbook_Price = eTextbook.find('div',{'class':'pricebox-header-price'}).text.strip().replace('from','')
        # print("eTextbook",eTextbook_Price)
    except:
        eTextbook_Price = "Not Found"
        # print("eTextbook","Not Found")
    try:
        buy = soup.find('div',{'class':'buy'}).find('div',{'class':'pricebox-description'}).find_all('div',{'class':'pricebox-price-option'})
        # print(buy)
        buy_used_Price = "Not Found"
        buy_new_Price = "Not Found"
        try:
            for item in buy:
                buy_used_new = item.find_all('label',{'class':'pricebox-price-label'})
                # print(buy_used)
                buy_Title = buy_used_new[0].text.strip()
                if "Used" in buy_Title:
                    buy_used_Price = buy_used_new[1].text.strip()
                    # print("buy_used",buy_used_Price)
                if "New" in buy_Title:
                    buy_new_Price = buy_used_new[1].text.strip()
                    # print("buy_new",buy_new_Price)
        except:
            # print("buy_used","Not found")
            buy_used_Price = "Not Found"
            buy_new_Price = "Not Found"
    except:
        buy_new_Price = "Not Found"
        buy_used_Price = "Not Found"
        # print("buy_used","Not found")
        # print("buy_new",'Not found')
    try:
        TextbookSol = soup.find('div',{'class':'tbs-radio-btn'})
        # TextbookSol_Title = TextbookSol.find('div',{'class':'pricebox-header-label'}).text.strip()
        # print(TextbookSol)
        TextbookSol_Price = TextbookSol.find('div',{'class':'price-info'}).find('span',{'class':'price'}).text.strip().replace(' / month','')
        # print("Textbook Solution",TextbookSol_Price)
    except:
        TextbookSol_Price = "Not Found"
        # print("Textbook Solution","Not Found")
    browser.close()
    return([rent_Price,eTextbook_Price,buy_used_Price,buy_new_Price,TextbookSol_Price])

# if __name__ == '__main__':
#     print(pearson("http://www.mypearsonstore.com/bookstore/statistics-informed-decisions-using-data-plus-mystatlab-0134135369"))

if __name__ == '__main__':
    newpath = '/home/matija/Desktop/pearson/'
    if not os.path.exists(newpath):
        os.makedirs(newpath)
    i = datetime.datetime.now()
    # print(i.date())
    list_date = str(i.date()).split('-')
    new_date = list_date[2]+'-'+list_date[1]+'-'+list_date[0]
    path = newpath + new_date + '.csv'

    # g = open(path, 'a', newline='')
    # csvWriter = csv.writer(g)
    # for row in list_of_all:
    #     csvWriter.writerow(row)
    g = open(path, 'w', newline='')
    csvWriter = csv.writer(g)
    csvWriter.writerow(['','', '','','Pearson', '','', '','','','', 'Amazon', '','', 'Barnes', '', '','','', 'Chegg','','','',''])
    csvWriter.writerow(['Category','Book', 'ISBN-10','Publication','standard', 'value', 'website','eText','Digital_Package','Digital+Print_Standard_Package','Digital+Print_Value_Package', 'rent', 'buy used','buy new', 'buy new', 'buy used', '60 days', '90 days','130 days','rent','etext','buy used','buy new','textbook solution'])
    category_prev = ''
    for category in list_of_books:
        print(category)
        category_name = category

        for i in list_of_books[category]:
            # print(i,list_of_books[i][0])
            if category_name==category_prev:
                category_name = ''
            list_of_all = []
            list_of_all.append(category_name)
            list_of_all.append(i)
            for j in list_of_books[category][i]:
                if 'pearson' in j:
                    try:
                        list_of_pearson = pearson(j)
                    except:
                        list_of_pearson = pearson(j)
                if 'amazon' in j:
                    try:
                        list_of_amazon = amazon(j)
                    except:
                        list_of_amazon = amazon(j)
                if 'barnes' in j:
                    try:
                        isbn_nu,pubyear_nu,list_of_barnes = barnes(j)
                    except:
                        isbn_nu,pubyear_nu,list_of_barnes = barnes(j)
                if 'chegg' in j:
                    try:
                        list_of_chegg = chegg(j)
                    except:
                        list_of_chegg = chegg(j)
            list_of_all.append(isbn_nu)
            list_of_all.append(pubyear_nu)
            list_of_all = list_of_all + list_of_pearson + list_of_amazon +list_of_barnes + list_of_chegg
            print(list_of_all)

            g = open(path, 'a', newline='')
            csvWriter = csv.writer(g)
            csvWriter.writerow(list_of_all)
            category_prev = category_name


