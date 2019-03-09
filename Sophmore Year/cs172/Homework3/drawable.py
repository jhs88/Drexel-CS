# Joseph Scherreik
# jhs88
# Homework 3 - Pygame
import pygame
import abc

# Drawable is a abstract class for draable objects
class Drawable(metaclass = abc.ABCMeta):
	def __init__(self,x=0,y=0,visible=True):
		self.__x = x
		self.__y = y
		self.__visible = visible

	def getLoc(self):
		return (self.__x, self.__y)

	def getVis(self):
		return self.__visible

	def setLoc(self,p):
		self.__x = p[0]
		self.__y = p[1]

	def setVis(self, v):
		self.__visible = v

	@abc.abstractmethod
	def draw(self, surface):
		pass

	@abc.abstractmethod
	def get_rect(self, surface):
		pass
