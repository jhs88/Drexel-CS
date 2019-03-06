# Joseph Scherreik
# jhs88
# Homework 2 - Media Library
from media import *

# List of all 12 avaliable Media
Lib = [
    Movie(
        'movie',
        'Friday the 13th',
        '5 stars',
        'Jason',
        '90 mins'),
    Song(
        'song',
        'Everlong',
        '5 stars',
        'Foo Fighters',
        'The Colour And The Shape'),
    Picture(
        'pic',
        'Dogs',
        '3 stars',
        '1000 dpi'),
    Movie(
        'movie',
        'Superbad',
        '4 stars',
        'A Man',
        '1 hr 20 min'),
    Song(
        'song',
        'Over the Hills and Far Away',
        '5 stars',
        'Led Zepplin',
        'Houses of the Holy'),
    Picture(
        'pic',
        'Cats',
        '2 stars',
        '300 dpi'),
    Movie(
        'movie',
        'Stars Wars: Episode IV - A New Hope',
        '5 stars',
        'George Lucas',
        '2 hrs'),
    Song(
        'song',
        'Comfortably Numb',
        '5 stars',
        'Pink Floyd',
        'The Wall'),
    Picture(
        'pic',
        'Cheese',
        '1 Mil stars',
        '1000 dpi'),
    Movie(
        'movie',
        'Monty Python and the Holy Grail',
        '5 stars',
        'Some Person',
        '2 hrs'),
    Song(
        'song',
        'Snow (Hey Oh)',
        '5 stars',
        'Red Hot Chili Peppers',
        'Stadium Arcadium'),
    Picture(
        'pic',
        'Cows',
        '1 stars',
        '500 dpi'),
]

def main():
    print('Welcome to your Media Library.')
    print('\"all\" - a list of all media.\n \"[Media]\" - a list of all [Media].\n \"[Song/Movie Name]\" - Plays song or movie.\n \"[Picture Name]\" - displays picture.\n \"quit\" - quits program.')
    while(True):
        # exits program if user types "quit"
        command = input()
        if command == 'quit':
            break
        # prints type of media
        elif command == 'all':
            for media in Lib:
                print(media)
        elif command == 'songs':
            for song in Lib:
                if song.getType() == 'song':
                    print(song)
        elif command == 'movies':
            for movie in Lib:
                if movie.getType() == 'movie':
                    print(movie)
        elif command == 'pictures':
            for pic in Lib:
                if pic.getType() == 'pic':
                    print(pic)
        else:
            # Handles playing / showing of Media Objects
            for media in Lib:
                if ((command == media.getName()) and (media.getType() == 'song' or media.getType() == 'movie')):
                    media.play()
                elif command == media.getName() and media.getType() == 'pic':
                    media.show()
main()
