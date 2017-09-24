class UrlUtils:
    
    @staticmethod
    def get_domain(url):
        '''
        Gets the domain of the url
        i.e
        www.test.com => test.com
        test.com => test.com
        https://www.test.com/subdomain1/subdomain2 => test.com
        '''
        if not url or not isinstance(url, str):
            raise TypeError()
        tokens = url.split('.')
        return '{0}.{1}'.format(tokens[-2].split('/')[-1], tokens[-1].split('/')[0])
