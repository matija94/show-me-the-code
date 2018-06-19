'''
url to problem
https://www.hackerrank.com/challenges/sparse-arrays/problem
'''

def matchingStrings(strings, queries):
    strings_count = {}
    for string in strings:
        strings_count[string] = strings_count.get(string,0) + 1
    return list(map(lambda x: strings_count.get(x,0), queries))