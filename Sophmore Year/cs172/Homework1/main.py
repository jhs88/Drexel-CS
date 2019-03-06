from question import Question

# List of all 10 avaliable questions
Questions = [
    Question(
        'What is the word\'s biggest island?',
        'Australia',
        'Greenland',
        'Barbados',
        'Great Britian',
        'b'),
    Question(
        'What is the word\'s longest river?',
        'Nile',
        'Mississippi',
        'Amazon',
        'Tiber',
        'c'),
    Question(
        'What is the diameter of Earth?',
        '8000 miles',
        '80000 miles',
        '100000 miles',
        '11 miles',
        'a'),
    Question(
        'In which country would you find the state of Tabasco?',
        'Peru',
        'Panama',
        'Barbados',
        'Mexico',
        'd'),
    Question(
        'What is the capital of Spain?',
        'Seville',
        'Barcelona',
        'Madrid',
        'Malaga',
        'c'),
    Question(
        'What does the term piano mean?',
        'play softly',
        'play loudly',
        'play with passion',
        'play with a swing',
        'a'),
    Question(
        'How many inches are in a mile?',
        '5320 in',
        '2200 in',
        '4356 in',
        '5280 in',
        'd'),
    Question(
        'When did the Cold War end?',
        '1776',
        '1984',
        '1989',
        '1812',
        'c'),
    Question(
        'When was SHakespeare born',
        'April 23, 1564',
        'June 11, 1546',
        'January 16 1568',
        'Septemer 19, 1599',
        'a'),
    Question(
        'Who invented the TV?',
        'John Logie Baird',
        'Thomas Edison',
        'George Carey',
        'Joseph Stalin',
        'c'),
    Question(
        'Where you would fine the Cresta Run?',
        'Denmark',
        'Greenland',
        'Germany',
        'Switzerland',
        'd'),
]

# Creates an object that keeps track of the players score
class Player():
    def __init__(self, s=0):
        self.score = s
    def getScore(self):
        return self.score

# Handles taking a turn for each player
def turn(question, player):
    print(question)
    ans = input('\nEnter your answer: ')
    if ans == question.getAns():
        player.score += 1
        print('That is correct!\n')
    else:
        print('That is incorrect. Better luck next time.\n')

# Creates two player objects
# This method creates the quiz's structure
# It also tallies up the scores and decides the winner of the game
def main():
    p1 = Player()
    p2 = Player()
    print('Welcome to the quiz\n')
    for i in range(0,10):
        if i % 2 == 0:
            print('Player 1 here is your question:')
            turn(Questions[i], p1)
        else:
            print('Player 2 here is your question:')
            turn(Questions[i], p2)
    print('\nAnd the final scores are:')
    print('Player 1: ' + str(p1.getScore()))
    print('Player 2: ' + str(p2.getScore()))
    if p1.getScore() > p2.getScore():
        print('Player 1 wins!')
    elif p2.getScore() > p1.getScore():
        print('Player 2 wins!')
    else:
        print('It\'s a tie!')
main()
