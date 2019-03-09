# Joseph Scherreik
# jhs88
# Homework 4 - Data Structures
class Employee():
    def __init__(self, id='', hours=0.0, payRate=0.0, grossWage=0.0):
        self.__id = id
        self.__hours = hours
        self.__payRate = payRate
        self.__grossWage = grossWage
    # Setters  
    def setID(self, id):
        self.__id = id
    def setHours(self, hours):
        self.__hours = hours
    def setPayRate(self, payRate):
        self.__payRate = payRate
    def setGrossWage(self, grossWage):
        self.__grossWage = grossWage
    # Getters
    def getID(self):
        return self.__id
    def getHours(self):
        return self.__hours
    def getPayRate(self):
        return self.__payRate
    def getGrossWage(self):
        return self.__grossWage
    # String Function 
    def __str__(self):
        return self.__id