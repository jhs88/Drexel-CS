from room import *
from maze import *

my_rooms = []
my_rooms.append(Room("This room is the entrance."))
my_rooms.append(Room("This room has a table. Maybe a dinning room?"))
my_rooms.append(Room("This room has a door"))
my_rooms.append(Room("This room has a flower"))
my_rooms.append(Room("This room has a fridge."))
my_rooms.append(Room("This room has a desk."))
my_rooms.append(Room("This room has a table."))
my_rooms.append(Room("This room has a bed. "))
my_rooms.append(Room("This room has two beds. "))
my_rooms.append(Room("Exit"))
#room 0 is south of room 1
my_rooms[0].setNorth(my_rooms[1])
my_rooms[1].setSouth(my_rooms[0])
#Room 2 is east of room 1
my_rooms[1].setEast(my_rooms[2])
my_rooms[2].setWest(my_rooms[1])

my_rooms[2].setEast(my_rooms[3])
my_rooms[3].setWest(my_rooms[2])

my_rooms[3].setEast(my_rooms[4])
my_rooms[4].setWest(my_rooms[3])

my_rooms[4].setEast(my_rooms[5])
my_rooms[5].setWest(my_rooms[4])

my_rooms[5].setEast(my_rooms[6])
my_rooms[6].setWest(my_rooms[5])

my_rooms[6].setEast(my_rooms[7])
my_rooms[7].setWest(my_rooms[6])

my_rooms[7].setEast(my_rooms[8])
my_rooms[8].setWest(my_rooms[7])

my_rooms[8].setEast(my_rooms[9])
my_rooms[9].setWest(my_rooms[8])

#Make a maze!
#Set the start and exit rooms.
my_maze = Maze(my_rooms[0],my_rooms[2])

moved = False

while not my_maze.atExit():
    print(my_maze.getCurrent())
    dir = input('Enter direction to move north, east, south, west, restart\n')
    if dir == 'reset':
        my_maze.reset()
        moved = True
        print('You went back to the start')
    if dir == 'north' and my_maze.moveNorth():
        moved = True
        print('You moved North')
    if dir == 'south' and my_maze.moveSouth():
        moved = True
        print('You moved South')
    if dir == 'west' and my_maze.moveWest():
        moved = True
        print('You moved West')
    if dir == 'east' and my_maze.moveEast():
        moved = True
        print('You moved East')
    if moved == False:
        print('Direction invalid, try agian')

print('You went', dir, '\nYou found the exit!')
