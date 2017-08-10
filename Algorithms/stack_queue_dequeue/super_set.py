from stack_queue_dequeue.stack import ArrayStack
from stack_queue_dequeue.queue import ArrayQueue


def copy_shallow(s):
    r = set()
    for i in s:
        r.add(i)
    return r

#C-6.20
def super_set(stack):
    s = set()
    que = ArrayQueue()
    que.enqueue(s)
    while not stack.is_empty():
        elem = stack.pop()
        for i in range(len(que)):
            s = que.dequeue()
            clone = copy_shallow(s)
            clone.add(elem)
            que.enqueue(s)
            que.enqueue(clone)
    return que


if __name__ == '__main__':
    stack = ArrayStack()
    for i in range(3,0,-1):
        stack.push(i)
    print(stack)
    
    
    q = super_set(stack)
    while not q.is_empty():
        print(q.dequeue())