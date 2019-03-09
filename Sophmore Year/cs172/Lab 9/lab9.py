from random import randint
from BST import *
from time import time
import matplotlib.pyplot as plt

def populate(n):
    list = []
    tree = BST()
    for entry in range(n):
        list.append(randint(0,n))
        tree.append(list[entry])
    return list, tree

def inList(list, int):
    for i in list:
        if int == i:
            return True
    return False

if __name__ == '__main__':
    count = 0
    for i in range(1,10000,1000):
        populate(i)

        start = time()
        for i in pop[0]:
            if i is pop[0]:
                count+=1
        print('Number of ints:', count)
        end = time()

        print('Total time:', end - start)

        count = 0
        start = time()
        for j in pop[0]:
            if pop[1].isin(j):
                count+=1
        print('Number of ints:', count)
        end = time()

        print('Total time:', end - start)

plt.show()
