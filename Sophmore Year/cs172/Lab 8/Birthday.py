class Birthday():
    def __init__(self, day=0, month=0, year=0):
        self.__day = day
        self.__month = month
        self.__year = year
    def getDay(self):
        return self.__day
    def getMonth(self):
        return self.__month
    def getYear(self):
        return self.__year
    def setDay(self, day):
        self.__day = day
    def setMonth(self, month):
        self.__month = month
    def setYear(self, year):
        self.__year = year
    def __str__(self):
        return(str(self.__month) + '/' + str(self.__day) + '/' + str(self.__year))
    def __hash__(self):
        return (self.__day + self.__month + self.__year) % 12
    def __eq__(self, other):
        if self.__day == other.getDay() and self.__month == othe.getMonth() and self.__year == other.getYear():
            return True
        else:
            return False

if __name__ == '__main__':
    alex = Birthday(9,12,1998)
    bill = Birthday(4,25,2016)
    print(alex, bill)
    print(hash(alex), hash(bill))
