'''
url to problem
https://www.hackerrank.com/challenges/arrays-ds/submissions
'''

import re


n = int(input().strip())
arr = [int(arr_temp) for arr_temp in input().strip().split(' ')]

print (re.sub(r'(\[|,|\])', '', str(arr[::-1])))