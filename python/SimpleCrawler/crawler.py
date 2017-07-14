import requests
import threading
import time

from queue import Empty,Queue
from concurrent.futures import ThreadPoolExecutor, as_completed
from pageparser import PageParser, UrlResolver
from urllib import robotparser 
from urllib.error import URLError
from pip._vendor.requests.exceptions import TooManyRedirects

#NOT FOR CLIENT
_rlock = threading.RLock()    
_robots = {}   
 
_rootDT = {}
_rootChilds = {}

class Downloader:
    '''
    NOT FOR CLIENT
    '''
    def __init__(self,threads):
        self._running=True
        self._tpe = ThreadPoolExecutor(threads)
        self._futures= Queue(threads)
        self._downloadedPages=0
        self._limitReached=False
        self._emptyroot = {}
        self._emptyrootthreshold = 3
        
    def __consumerQueue__(self):
        time.sleep(2)
        global _rlock, _rootDT, _rootChilds
        print ('Consumer queue started')
        while self._running:
            rooturl = None
            try:
                rooturl = min(_rootDT, key=_rootDT.get)
                url = _rootChilds[rooturl].get(block=True, timeout=1.5)
                robots = _robots[rooturl]
                if not robots.can_fetch('testingcrawler', url):
                    print('Not able to download url %s' %(url))
                    continue
                
                
                #crawl delay
                cd = 2
                current_time = time.time()
                if cd > (current_time - _rootDT[rooturl]):
                    sleepval = cd - (current_time - _rootDT[rooturl])
                    print('Sleeping for %d ' %(sleepval))
                    time.sleep(sleepval)
                    
                
                _rootDT[rooturl] = time.time()
                self._futures.put(self._tpe.submit(self.__downloadPage__, url))
            except Empty:
                #print ("Empty exception caught.URL: %s\nI will terminate the downloader now")
                #self.shutdown()
                #print ('Waited 0 seconds to get result from queue %s' % (rooturl))
                _rootDT[rooturl] = time.time()
                emptyrootval = self._emptyroot[rooturl]
                if emptyrootval >= 15:
                    print('%s root url will be removed from crawler' %(rooturl))
                    _rootDT.pop(rooturl, None)
                else:
                    self._emptyroot[rooturl] += 1
                
                
            if self._limitReached:
                print ('Limit has been reached and queue is empty!\nI will terminate downloader after all pending tasks are finished')
                self.shutdown()
                break

    def __feed__(self, url):
        global _rootChilds
        try:
            domain_name = UrlResolver.getRootUrlWithoutProtocol(url)
            _rootChilds[domain_name].put(url)
        except KeyError as k:
            print('Couldnt parse root url for url %s' %(url))
            print(str(k))
            
    def __downloadPage__(self, url):
        self._downloadedPages+=1
        print ('Downloading url: %s which is %d' %(url,self._downloadedPages))
        try:
            response = requests.get(url)
            if len(response.history) > 0:
                if not UrlResolver.getRootUrlWithoutProtocol(response.url) == UrlResolver.getRootUrlWithoutProtocol(url) : return None
                return Page(response)
        except TooManyRedirects:
            return None
    
    def shutdown(self):
        self._tpe.shutdown()
        self._running=False
        self._tpe=None

class Page:
    '''
    CLIENT READ-ONLY
    '''
    def __init__(self,response):
        self._url = response.request.url
        self._data = response.text
        
    def getUrl(self):
        return self._url
    
    def getContent(self):
        return self._data 
    

class Crawler:
    '''
    Simple crawler
    INPUT: pass the number of threads, urls to be downloaded and basic queue implementation
    OUTPUT: list of pages downloaded(containing url that was downloaded and content data)
    '''
    def __init__(self, threads, urlset,limit=None):
        self._running = True
        self.urlset = urlset
        self._downloadedUrls=set()
        self._limit=limit
        self._downloader = Downloader(threads)
        self._dowloadedCount = 0
        
    def __monitorState__(self):
        start = time.time()
        while self._running:
            time.sleep(2)
            downloadedPerS = float(self._downloader._downloadedPages/(time.time() - start))
            print('Downloading %.2f/s pages' %(downloadedPerS))
            if self._downloader._tpe is None:
                print ('Crawler is finished!')
                self._running=False
    
    def __consumerFutures__(self):
        print ('Consumer futures started')
        while self._running or not self._downloader._futures.qsize()==0:
            future = self._downloader._futures.get()
            written = 0
            if future.done():
                page = future.result()
                if page is None: 
                    continue
                written +=1
                print ('Writing %s content to file which is %d' % (page.getUrl(),written))
                #with open('/home/matija/Desktop/rakuten/'+ page.getUrl().replace("/", "."), 'wb') as f:
                    #f.write(page.getContent().encode('utf-8'))
                
                if self._limit==0:
                    break
                
                childUrls = PageParser(page).getChildUrls()
                print('Inserting %d urls to queue for %s' %(len(childUrls), UrlResolver.getRootUrl(page._url)))
                for childUrl in childUrls:
                    if childUrl not in self._downloadedUrls:
                        if self.__decrementLimit__() == 0:
                            print ('Limit has been reached, stopping crawler! Process should be stopped after submitted urls are downloaded')
                            self._downloader._limitReached=True
                            break
                        self._downloadedUrls.add(childUrl)
                        self._downloader.__feed__(childUrl)
            
            else:
                self._downloader._futures.put(future)
            
    def __decrementLimit__(self):
        if self._limit is not None:
            self._limit-=1
        return self._limit
        
    def start(self):
        global _robots, _rootDT, _rootChilds
        self._running=True
        for url in self.urlset:
            domain_name = UrlResolver.getRootUrlWithoutProtocol(url)
            _rootDT[domain_name] = 0
            _rootChilds[domain_name] = Queue(500000)
            self._downloader._emptyroot[domain_name] = 0
            robots = robotparser.RobotFileParser()
            robots.set_url(url+'robots.txt')
            try:
                robots.read()
            except URLError:
                del self._downloader._emptyroot[domain_name]
                del _rootDT[domain_name]
                del _rootChilds[domain_name]
                continue
            
            _robots[domain_name] = robots
            self._downloader.__feed__(url)
            self._downloadedUrls.add(url)
        
        self.urlset = None
        threading.Thread(target=self.__monitorState__).start()
        threading.Thread(target=self.__consumerFutures__).start()
        threading.Thread(target=self._downloader.__consumerQueue__).start()
        
    def stop(self):
        self._downloader.shutdown()
        self._running=False
                
    def isRunning(self):
        return self._running
    
    def downloaderRunning(self):
        return self._downloader._running
    
    def getResults(self, block=True):
        '''
        Returns list of futures which are not yet finished
        '''
        return self._downloader._futures
    
    def getDoneResult(self):
        '''
        Returns list of Page objects which are ready to be parsed
        '''
        _rlock.acquire()
        results = [future.result() for future in as_completed(self._downloader._futures)]
        _rlock.release()
        return results
