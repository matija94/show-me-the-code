from queue import Queue
from crawler import Crawler
import time

urls = ['http://www.frys.com/', 'https://www.bhphotovideo.com/', 'https://www.officeshoes.rs/', 'https://www.zara.com/rs/', 'http://www.healthypets.com/',
        'https://www.walmart.com/', 'http://www.homedepot.com/',
'http://www.hammondscandies.com/',
'http://www.elchimusa.com/',
'http://www.theamazinggang.com/',
'http://shop.degen-nyc.com/',
'http://www.genuineguidegear.com/',
'http://www.fones.com/',
'http://www.jacksonhonda.net/',
'http://ca.loccitane.com/',
'http://www.impressionbridal.com/',
'http://podtech.net/',
'http://www.beautybridge.com/',
'http://www.thesensualcandle.com/',
'http://www.invitecottage.com/',
'http://www.highexposure.com.au/',
'http://www.princessetamtam.com/',
'http://www.annabellesflowers.net/',
'http://www.designandgrace.com/',
'http://www.camcentre.co.uk/',
'http://www.customizeelitesocks.com/',
'http://www.thewinecountry.com/']


q = Queue(500)

crawler = Crawler(70, urls,1000000)
start = time.time()
crawler.start()
while crawler.isRunning():
    pass
print ('Process lasted: %s' %(time.time()- start))

