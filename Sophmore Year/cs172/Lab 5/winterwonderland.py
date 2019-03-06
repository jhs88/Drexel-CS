import pygame
import random
from Drawable import *

class Rectangle(Drawable):
    def __init__(self, x, y, w, h, c):
        super().__init__(x, y)
        self.width = w
        self.height = h
        self.color = c

    def draw(self, surface):
        location = self.getLoc()
        pygame.draw.rect(surface, self.color, (location[0], location[1],self.width, self.height))

class Snowflake(Drawable):
    def __init__(self, x, y):
        super().__init__(x, y)
        self.color = (255,255,255)
        self.maxY = random.randint(175,300)

    def animate(self):
        pos = self.getLoc()
        self.setLoc([pos[0],pos[1]+1])

    def draw(self, surface):
        location = self.getLoc()
        pygame.draw.line(surface, self.color, [location[0]-5, location[1]], [location[0]+5, location[1]], 1)
        pygame.draw.line(surface, self.color, [location[0], location[1]-5], [location[0], location[1]+5], 1)
        pygame.draw.line(surface, self.color, [location[0]-5, location[1]-5], [location[0]+5, location[1]+5], 1)
        pygame.draw.line(surface, self.color, [location[0]-5, location[1]+5], [location[0]+5, location[1]-5], 1)
        if location[1] <= self.maxY:
            self.animate()

def main():
    pygame.init()
    surface = pygame.display.set_mode((400,300))
    snowFall = []

    while(True):
        for event in pygame.event.get():
            if (event.type == pygame.QUIT) or (event.type == pygame.KEYDOWN and event.__dict__['key'] == pygame.K_q):
                pygame.quit()
                exit()

        surface.fill((0,0,255))
        Rectangle(0,175,400,125,(0,255,0)).draw(surface)
        if event.type == pygame.KEYDOWN and event.__dict__['key'] == pygame.K_SPACE:
            flake = Snowflake(random.randint(0,400),0)
            snowFall.append(flake)

        for snow in snowFall:
            snow.draw(surface)

        pygame.display.update()



main()
