#Interface Class for a Stack
#Only allows access to the
#Stack commands of the built in list
class Stack:
	#Create a New Empty Stack
	def __init__(self):
		self.__S = []
	#Display the Stack
	def __str__(self):
		return str(self.__S)
	#Add a new element to top of stack
	def push(self,x):
		self.__S.append(x)
	#Remove the top element from stack
	def pop(self):
		return self.__S.pop()
	#See what element is on top of stack
	#Leaves stack unchanged
	def top(self):
		return self.__S[-1]


def Postfix_Math(exp):
    operators = ["+","-",'*','/']
    operands = Stack()
    listVar = exp.split(' ')

    for stuff in listVar:
        if stuff in operators:
            operator1= int(operands.pop())
            operator2= int(operands.pop())
            if stuff == "+":
                operands.push(operator2 + operator1)
            elif stuff == "-":
                operands.push(operator2 - operator1)
            elif stuff == '*':
                operands.push(operator2 * operator1)
            elif stuff == '/':
                operands.push(operator2 / operator1)
        else:
            operands.push(stuff)
    result = float(operands.top())
    return result

def main():
    print("Welcome to Postfix Calculator")
    print("Enter exit to quit.")
    loopVar = False

    while loopVar != True:
        userInput = input("Enter Expresion\n")
        if userInput.lower() == "exit":
            loopVar= True
        else:
            print("Result:", Postfix_Math(userInput))

main()
