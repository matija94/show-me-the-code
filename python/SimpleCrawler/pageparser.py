from urllib import parse
import re
import bs4


class UrlResolver:

    @staticmethod
    def getRootUrl(url):
        o = parse.urlparse(url)
        return o[0] + "://" + o[1] + "/"

    @staticmethod
    def isRelative(url):
        return url[0]=='/'
    
    @staticmethod
    def getRootUrlWithoutProtocol(url):
        o = parse.urlparse(url)
        return o.netloc

class PageParser:
    
    def __init__(self, page):
        '''
        uses crawler.Page object
        '''
        self._page = page
        self._rootUrl = UrlResolver.getRootUrl(page.getUrl())
        
    def getChildUrls(self):
        childUrls = set()
        bs = bs4.BeautifulSoup(self._page.getContent(), 'html.parser')
        for tag in bs.find_all('a', attrs={'href': re.compile('.')}):
            hrefval = tag.get('href')
            if UrlResolver.isRelative(hrefval):
                childUrls.add(parse.urljoin(self._rootUrl, hrefval))
            elif self._rootUrl in hrefval:
                childUrls.add(hrefval)
        
        return childUrls
