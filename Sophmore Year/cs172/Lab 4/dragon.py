from monster import monster

class dragon(monster):
    def __init__(self, name = 'dragon', descrip = 'I breathe fire.'):
        self.Name = name
        self.Description = descrip
        self.Health = 100000
    def __str__(self):
        return self.Name
    def getName(self):
        return self.Name
    def getDescription(self):
        return self.Description
    def getHealth(self):
        return self.Health

    def basicAttack(self, enemy):
        enemy.doDamage(35)
    def basicName(self):
        return 'Claw'

    def defenseAttack(self, enemy):
        enemy.doDamage(50)
    def defenseName(self):
        return 'Fly'

    def specialAttack(self, enemy):
        self.doDamage(200)
    def specialName(self):
        return 'Breath Fire'

    def doDamage(self,damage):
        if damage > 0:
            self.Health -= damage
        else:
            self.Health += abs(damage)

    def resetHealth(self):
        self.Health = 100
