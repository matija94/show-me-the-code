from structures.queue import CircularQueue
from base.WebAgent import WebAgent
from criteo_fraud_detect.components import Actions
from base.exceptions.not_found import ElementNotFound

class CriteoFraudDetect:
    
    def __init__(self, criteo_client_product_url, non_disney_safe):
        '''
        criteo_client_product_url has to be string or list of strings. This variable represents single product urls which are sold by criteo clients ( products will be added to cart in order to trigger criteo cookie insertion)
        non_disney_safe has to be string or list of strings. This variable represents domains with fakes news,adult content, etc... It is fraudulent to use these domains as publishers for advertisements, script will monitor over these websites and take screenshots 
        '''
        if not (type(criteo_client_product_url) is list):
            self._clients = [criteo_client_product_url]
        else:
            self._clients = criteo_client_product_url
        self._circular = CircularQueue()
        if not (type(non_disney_safe) is list):
            self._circular.enqueue(non_disney_safe)
        else:
            for url in non_disney_safe:
                self._circular.enqueue(url)

    def run(self, repeat, path):
        '''
            Runs the application
            
            repeat represents number of times that each non disney safe website will be visited for each client product url.
            
            path represents directory under which taken screenshots will be saved
        '''
        if not (repeat >= 1):
            raise ValueError('repeat must be positive')
        repeat = repeat * len(self._circular)
        r = repeat
        agent = WebAgent()
        actions = Actions(agent)
        for client in self._clients:
            agent.delete_cookies()
            try:
                actions.add_to_cart(client)
            except ElementNotFound:
                print("Add to cart not found on {0}".format(client))
            while repeat > 0:
                url = self._circular.first()
                self._circular.rotate()
                actions.reload_and_ss(url,1,path)
                repeat-=1
            repeat = r
        
if __name__ == '__main__':
    cfd = CriteoFraudDetect(['https://www.toysrus.com/product?productId=97150726&rrec=true'],
                            'https://100percentfedup.com/')
    cfd.run(10, '/home/matija/Desktop/criteo_fraud_detect/')
    
    #http://www.sears.com/kenmore-18-cu-ft-top-freezer-refrigerator-white/p-04660412000P?plpSellerId=Sears&prdNo=1&blockNo=1&blockType=G1