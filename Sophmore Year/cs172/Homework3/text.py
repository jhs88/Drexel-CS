# Joseph Scherreik
# jhs88
# Homework 3 - Pygame
import pygame
from drawable import *

class Text(Drawable):
    def __init__(self,x=0,y=0,text="",points=(-1),visible=True):
        self.__x = x
        self.__y = y
        self.__text = text
        self.__points = points
        self.__vis = visible

    def getLoc(self):
        return (self.__x, self.__y)

    def getVis(self):
        return self.__vis

    def setLoc(self,p):
        self.__x = p[0]
        self.__y = p[1]

    def setVis(self,v):
        self.__vis = v

    def score(self,n):
        self.__points += n

    def draw(self, surface):
        loc = self.getLoc()
        if self.__points < 0:
            font = pygame.font.Font("freesansbold.ttf", 32)
            self.surface = font.render(self.__text, True, (0,0,0))
            surface.blit(self.surface, (loc[0], loc[1]))
        elif self.__points >= 0:
            font = pygame.font.Font("freesansbold.ttf", 32)
            self.surface = font.render(self.__text + str(self.__points), True, (0,0,0))
            surface.blit(self.surface, (loc[0], loc[1]))

    def get_rect(self):
        pass
