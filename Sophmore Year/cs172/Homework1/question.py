class Question:
    # Constructor
    def __init__(self, q, a, b, c, d, ans):
        self.question = q
        self.A = a
        self.B = b
        self.C = c
        self.D = d
        self.correct = ans
    # Getters
    def getQuest(self):
        return self.question
    def getA(self):
        return self.A
    def getB(self):
        return self.B
    def getC(self):
        return self.C
    def getD(self):
        return self.D
    def getAns(self):
        return self.correct
    # Setters
    def setQuest(self, q):
        self.question = q
    def setA(self, a):
        self.A = a
    def setB(self, b):
        self.B = b
    def setA(self, c):
        self.C = c
    def setA(self, d):
        self.D = d
    def setAns(self, ans):
        self.correct = ans
    # Prints the Question with avaliable answers
    def __str__(self):
        return str(self.question) + '\nA. ' + str(self.A) + '\nB. ' + str(self.B) + '\nC. ' + str(self.C) + '\nD. ' + str(self.D)
