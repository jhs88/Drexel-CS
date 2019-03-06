# Joseph Scherreik
# jhs88
# Media Objects
class Media():
    def __init__(self, t, n, r):
        self.Type = t
        self.Name = n
        self.Rating = r
    def __str__(self):
        print(self.Name)
    def getType(self):
        return self.Type
    def getName(self):
        return self.Name
    def getRating(self):
        return self.Rating

class Movie(Media):
    def __init__(self, t, n, r, dir, runT):
        super().__init__(t, n, r)
        self.Director = dir
        self.runTime = runT
    def getDirector(self):
        return self.Director
    def getRunTime(self):
        return self.runTime
    def play(self):
        print(self.Name + ', playing now')
    def __str__(self):
        return 'Type: ' + self.Type + ', Name: ' + self.Name + ', Rating: ' + self.Rating + ', Director: ' + self.Director + ', Run Time: ' + self.runTime

class Song(Media):
    def __init__(self, t, n, r, art, alb):
        super().__init__(t, n, r)
        self.Artist = art
        self.Album = alb
    def getArtist(self):
        return self.Artist
    def getRunTime(self):
        return self.Album
    def play(self):
        print(self.Name + ' by ' + self.Artist + ', playing now')
    def __str__(self):
        return 'Type: ' + self.Type + ', Name: ' + self.Name + ', Rating: ' + self.Rating + ', Artist: ' + self.Artist + ', Album: ' + self.Album

class Picture(Media):
    def __init__(self, t, n, r, res):
        super().__init__(t, n, r='200 dpi')
        self.Resolution = res
    def getResolution(self):
        return self.Resolution
    def show(self):
        print('Showing ' + self.Resolution)
    def __str__(self):
        return 'Type: ' + self.Type + ', Name: ' + self.Name + ', Rating: ' + self.Rating + ', Resolution: ' + self.Resolution
