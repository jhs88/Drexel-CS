def mymin(L,start,stop):
    if len(L) == 1:
       return L[0]
    else:
        return mymin(L,0,len(L)-1)

print(mymin([7,1,9,3,2,4],2,5))

# def remainder(a,b):
#     if b == a:
#         return 0
#     elif b < a:
#         return remainder(a-b,b)
#     else:
#         return a
#
# print(remainder(11,5))
