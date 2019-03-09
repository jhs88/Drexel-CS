# Joseph Scherreik
# jhs88
# Homework 4 - Data Structures
from employee import Employee
# Taken from lecture demo
from LinkedListDemo import LinkedList

# Declare payroll as a linked list
payroll = LinkedList()

while True:
    # Takes user input as a comand
    c = input('\nadd - Add new employee\ncww - Calculate weekly wages.\npayroll - Shows the payroll.\nuhr - Updates Hourly Rate of an employee.\nfire - Removes employee from payroll.\nq - Quits the program.\n')
    # Quit comand - ends program
    if c == 'q':
        break
    # Add comand - adds new Employees to payroll 
    elif c == 'add':
            id = input('Enter new employee\'s ID:')
            payRate = input('Enter new employee\'s payrate:')
            if float(payRate) < 6.0:
                print('\nINVALID PAY RATE.')
            else:
                payroll.append(Employee(id,0.0,float(payRate),0.0))
                print('\nEmployee Created.')
    # Calculates weekly wage based on hours worked
    elif c == 'cww':
        for i in range(0,len(payroll)):
            hours = input('Enter number of hours worked for Employee ' + str(payroll[i]) + ':\n')
            if float(hours) < 0.0:
                print('You can\'t work negative hours.')
                break
            else:
                payroll[i].setHours(float(hours))
                payroll[i].setGrossWage(float(hours) * float(payroll[i].getPayRate()))
                print('\nGross Pay set.')
    # Lists all Employees and their stats
    elif c == 'payroll':
        print('\n')
        for i in range(0,len(payroll)):
            print('ID:', str(payroll[i]), 'Total Hours:', str(payroll[i].getHours()), 'Pay Rate:', str(payroll[i].getPayRate()), 'Gross Wage:', str(payroll[i].getGrossWage()))
    # Updates a particular Employee's hourly rate
    elif c == 'uhr':
        id = input('Enter Employee ID to update their hourly rate:')
        for i in range(0,len(payroll)):
            newRate = input('Enter new hourly rate:')
            if payroll[i].getID() == id and float(newRate) > 6.0:
                payroll[i].setPayRate(float(newRate))
                print('\nHourly Rate Updated.')
            else:
                print('Pay rate too low.')
                break
    # Removes Employee from payroll
    elif c == 'fire':
        id = input('Enter Employee ID to fire:')
        for i in range(0,len(payroll)):
            if payroll[i].getID() == id:
                payroll.remove(payroll[i])