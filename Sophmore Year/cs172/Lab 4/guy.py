from monster import monster

class guy(monster):
    def __init__(self, name = 'Jerry', descrip = 'I\'m just a guy.'):
        self.Name = name
        self.Description = descrip
        self.Health = 100
    def __str__(self):
        return self.Name
    def getName(self):
        return self.Name
    def getDescription(self):
        return self.Description
    def getHealth(self):
        return self.Health

    def basicAttack(self, enemy):
        enemy.doDamage(10)
    def basicName(self):
        return 'Punch'

    def defenseAttack(self, enemy):
        self.doDamage(-25)
    def defenseName(self):
        return 'Band Aids'

    def specialAttack(self, enemy):
        enemy.doDamage(35)
    def specialName(self):
        return 'Kick'

    def doDamage(self,damage):
        if damage > 0:
            self.Health -= damage
        else:
            self.Health += abs(damage)

    def resetHealth(self):
        self.Health = 100
