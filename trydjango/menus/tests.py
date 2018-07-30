#from django.test import TestCase

# Create your tests here.


'''



10 15 20    50    100


'''


def make_subsets(s):
    subsets = []
    for i in range(1 << len(s)):
        subset = []
        for bit in range(len(s)):
            if i & (1 << bit):
                subset.append(s[bit])
        subsets.append(subset)
    return subsets


if __name__ == '__main__':
    print(make_subsets('mat'))

