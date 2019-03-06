from fraction import Fraction

def H(n):
    sum = Fraction(0,1)
    for k in range(1,n+1):
        sum+=Fraction(1,k)
    return sum
def T(n):
    sum = Fraction(0,1)
    for k in range(0,n+1):
        sum+=(Fraction(1,2) ** k)
    return sum
def Z(n):
    return Fraction(2,1) - T(n)
def R(n,b):
    sum = Fraction(0,1)
    for k in range(1,n+1):
        sum+=(Fraction(1,k) ** b)
    return sum

def main():
    print('Welcome to Fun with Fractions!')
    while True:
        num = int(input('Enter Number of iterations (integer > 0):'))
        try:
            num > 0
            break
        except:
            print('Bad Input')
    print('H('+ str(num) + ')= ' + str(H(num)))
    print('H('+ str(num) + ')~= ' + str(H(num).approximate()))
    print('T('+ str(num) + ')= ' + str(T(num)))
    print('T('+ str(num) + ')~= ' + str(T(num).approximate()))
    print('Z('+ str(num) + ')= ' + str(Z(num)))
    print('Z('+ str(num) + ')~= ' + str(Z(num).approximate()))
    print('R('+ str(num) + ', 2)= ' + str(R(num,2)))
    print('R('+ str(num) + ', 2)~= ' + str(R(num,2).approximate()))
    print('R('+ str(num) + ', 3)= ' + str(R(num,3)))
    print('R('+ str(num) + ', 3)~= ' + str(R(num,3).approximate()))
    print('R('+ str(num) + ', 4)= ' + str(R(num,4)))
    print('R('+ str(num) + ', 4)~= ' + str(R(num,4).approximate()))
    print('R('+ str(num) + ', 5)= ' + str(R(num,5)))
    print('R('+ str(num) + ', 5)~= ' + str(R(num,5).approximate()))
    print('R('+ str(num) + ', 6)= ' + str(R(num,6)))
    print('R('+ str(num) + ', 6)~= ' + str(R(num,6).approximate()))
    print('R('+ str(num) + ', 7)= ' + str(R(num,7)))
    print('R('+ str(num) + ', 7)~= ' + str(R(num,7).approximate()))
    print('R('+ str(num) + ', 8)= ' + str(R(num,8)))
    print('R('+ str(num) + ', 8)~= ' + str(R(num,8).approximate()))
main()
