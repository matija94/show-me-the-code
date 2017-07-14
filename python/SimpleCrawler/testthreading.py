import time
from concurrent.futures import ThreadPoolExecutor,ProcessPoolExecutor



def count(n):
    start = time.time()
    while n>0:
        n-=1
    return (time.time()) - start

            #2**64
numbers = [4294967296, 2**54,2**44,2**34,2**32]
numbers = [40000000,30000000,20000000,20000000,20000000,20000000,20000000,20000000,20000000,20000000,20000000,20000000,20000000]
def main():
    start = time.time()
    with ProcessPoolExecutor(len(numbers)) as pp:
        for number, seconds in zip(numbers, pp.map(count, numbers)):
            print("Counting to {} took {} seconds".format(number, seconds))
    print('Process lasted {}'.format((time.time()) - start))
    
    
if __name__ == '__main__':
    main()