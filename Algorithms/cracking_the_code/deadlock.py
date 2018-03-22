

import threading
import time

resource1 = threading.RLock()
resource2 = threading.RLock()

def t1():
    resource1.acquire()
    time.sleep(1)
    print('Deadlock')
    resource2.acquire()
    resource1.release()
    resource2.release()


def t2():
    resource2.acquire()
    time.sleep(2)
    print("deadlock")
    resource1.acquire()
    resource2.release()
    resource1.release()
    
    
t1 = threading.Thread(target=t1)
t2 = threading.Thread(target=t2)

t1.start()
t2.start()
