'''
url to problem
https://www.hackerrank.com/challenges/array-left-rotation/problem
'''

if __name__ == '__main__':
    nd = input().split()

    n = int(nd[0])

    d = int(nd[1])

    a = list(map(int, input().rstrip().split()))

    b = [0 for _ in range(n)]
    for i in range(n):
        shifted_index = (i - d) % n
        b[shifted_index] = str(a[i])
    print(' '.join(b))
