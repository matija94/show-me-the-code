'''
url to problem
https://www.hackerrank.com/challenges/2d-array/problem
'''
import sys

arr = []
for arr_i in range(6):
   arr_t = [int(arr_temp) for arr_temp in input().strip().split(' ')]
   arr.append(arr_t)

n = len(arr)

maxHourglassSum=-sys.maxsize

for ind,subarr in enumerate(arr):
    if (ind+3 > n): break
    for index,elem in enumerate(subarr):
        if (index+3 > n): continue
        hourglassSum = subarr[index]+subarr[index+1]+subarr[index+2]+arr[ind+1][index+1]+arr[ind+2][index]+arr[ind+2][index+1]+arr[ind+2][index+2]
        maxHourglassSum = max(maxHourglassSum,hourglassSum)


print (maxHourglassSum)