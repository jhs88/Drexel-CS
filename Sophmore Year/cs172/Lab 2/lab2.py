import datetime

class Item():
    def __init__(self,name, price, taxable):
       self.__name = name
       self.__price = price
       if taxable == "yes":
           self.__taxable = True
       else:
           self.__taxable = False

    def __str__(self):
        return "{:_<20}".format(self.__name) + "{:_>20}".format("${:.2f}".format(self.__price))

    def getPrice(self):
        return self.__price

    def getTax(self, taxRate):
        if self.__taxable == True:
            return taxRate * self.__price
        else:
            return 0

class Receipt():
    def __init__(self,tax_rate):
        self.__tax_rate = tax_rate
        self.__purchases = []

    def __str__(self):
        result = "----- Receipt " + str(datetime.datetime.now()) + " -----\n"
        total = 0
        taxTotal = 0
        for item in self.__purchases:
            result = result + str(item) + "\n"
            total += item.getPrice()
            taxTotal += item.getTax(self.__tax_rate)
        result = result + "\n" + "{:_<20}".format("Sub Total") + "{:_>20}".format("${:.2f}".format(total)) + "\n"
        result = result + "{:_<20}".format("Tax") + "{:_>20}".format("${:.2f}".format(taxTotal)) + "\n"
        result = result + "{:_<20}".format("Total") + "{:_>20}".format("${:.2f}".format(taxTotal + total))
        return result

    def addItem(self,Item):
        self.__purchases.append(Item)

def main():
    print('Welcome to Receipt Creator')
    receipt = Receipt(0.07)
    while True:
        name = input('Enter Item name: ')
        price = float(input('Enter Item price: '))
        taxable = input('Is the item taxable (yes/no): ')
        receipt.addItem(Item(name, price, taxable))
        add = input('Add another item (yes/no): ')
        if add == 'no':
            break
    print(receipt)

if __name__ == "__main__":
    main()
