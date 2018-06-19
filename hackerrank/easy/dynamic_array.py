'''
url to problem
https://www.hackerrank.com/challenges/dynamic-array/problem
'''

lastAns = 0
data = input().split(' ')
N = int(data[0])

seqList = []
for i in range(0, N):
    l = []
    seqList.append(l)

for i in range(0, int(data[1])):
    instructions = input().split(' ')
    typeQuery = int(instructions[0])
    x = int(instructions[1])
    y = int(instructions[2])
    if (typeQuery == 1):
        seqList[((x ^ lastAns) % N)].append(y)

    else:
        seq = seqList[((x ^ lastAns) % N)]
        lastAns = seq[y % len(seq)]
        print(lastAns)