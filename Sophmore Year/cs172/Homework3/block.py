# Joseph Scherreik
# jhs88
# Homework 3 - Pygame
import pygame
from drawable import *

# Ball is a object that inherits Drawable
# It draws a blue block on the surface
class Block(Drawable):
    def __init__(self,x=0,y=0,visible=True,color=(0,0,255),width=20,length=20):
        super().__init__(x,y,visible)
        self.__color = color
        self.__width = width
        self.__length = length

    def getLoc(self):
        return (self.__x, self.__y)

    def getVis(self):
        return self.__vis

    def setLoc(self,p):
        self.__x = p[0]
        self.__y = p[1]

    def setVis(self,v):
        self.__vis = v

    def draw(self, surface):
        loc = self.getLoc()
        pygame.draw.rect(surface, self.__color, (loc[0], loc[1], self.__width, self.__length))
        pygame.draw.rect(surface, [0,0,0], (loc[0], loc[1], self.__width, self.__length), 1)

    def get_rect(self):
        loc = self.getLoc()
        return pygame.Rect((loc[0], loc[1], self.__width, self.__length))
