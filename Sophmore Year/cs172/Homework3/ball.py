# Joseph Scherreik
# jhs88
# Homework 3 - Pygame
import pygame
from drawable import *

# Ball is a object that inherits Drawable
# It draws a red ball on the surface
class Ball(Drawable):
    def __init__(self,x,y,visible,color=(255,0,0),radius=8):
        super().__init__(x,y,visible)
        self.__color = color
        self.__rad = radius

    def getLoc(self):
        return (self.__x, self.__y)

    def getVis(self):
        return self.__visible

    def setLoc(self,p):
        self.__x = p[0]
        self.__y = p[1]

    def setVis(self,v):
        self.__vis = v

    def draw(self, surface):
        loc = self.getLoc()
        pygame.draw.circle(surface, self.__color, (loc[0], loc[1]), self.__rad)
        pygame.draw.rect(surface, [0,0,0], (loc[0]-self.__rad, loc[1]-self.__rad, (self.__rad * 2), (self.__rad*2)),1)

    def get_rect(self):
        loc = self.getLoc()
        return pygame.Rect([loc[0]-self.__rad, loc[1]-self.__rad, (self.__rad * 2), (self.__rad*2)])
