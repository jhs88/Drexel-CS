# Joseph Scherreik
# jhs88
# Homework 3 - Pygame
import pygame
from ball import *
from block import *
from text import *

# Checks if two rectangles intersect
def intersect(rect1, rect2):
    if rect1.x < rect2.x + rect2.width and \
        rect1.x + rect1.width > rect2 . x and \
        rect1.y < rect2.y + rect2.height and \
        rect1.height + rect1.y > rect2.y:
            return True
    return False

# Main Game Method
def main():
    # Initializing game
    pygame.init()
    surface = pygame.display.set_mode((500,500))
    pygame.display.set_caption('Angry Orbs')
    timer = pygame.time.Clock()

    # blocks is a list of drawable objects
    blocks = []
    drawables = []
    blocks.append(Block(400,380))
    blocks.append(Block(380,380))
    blocks.append(Block(360,380))
    blocks.append(Block(400,360))
    blocks.append(Block(380,360))
    blocks.append(Block(360,360))
    score = Text(50,50,"Score:",0)
    drawables.append(score)
    ball = Ball(20,400,True)
    drawables.append(ball)

    # Gravity Vars
    dt = 0.1
    g = 6.67
    R = 0.7
    eta = 0.5
    xv = 0
    yv = 0


    check = True

    # Game loop
    while check:
        surface.fill((255,255,255))
        pygame.draw.line(surface, (0,0,0), (0,400), (500,400))
        # Checks if object should be drawn
        for drawable in drawables:
            if drawable.getVis():
                drawable.draw(surface)
        for block in blocks:
            if block.getVis():
                block.draw(surface)
                if intersect(block.get_rect(),ball.get_rect()):
                    block.setVis(False)
                    score.score(1)
        initx = ball.getLoc()[0] # Inital
        inity = ball.getLoc()[1] # Locations
        # Checks if ball is out of bounds and if it is the game ends
        if initx > 500 or initx < 0:
            gameover = Text(250,250,"Game Over")
            gameover.draw(surface)
            check = False
        if inity > 400:
            yv = -R * yv
            xv = eta * xv
        elif inity < 400:
            yv = yv - g * dt
        # Update the ball to it's new location
        finx = dt * xv
        finy = dt * yv
        ball.setLoc([int(initx + finx),int(inity - finy)])

        # Event loop
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
            if event.type == pygame.MOUSEBUTTONDOWN:
                pos1 = pygame.mouse.get_pos()
                x1 = pos1[0]
                y1 = pos1[1]
            if event.type == pygame.MOUSEBUTTONUP:
                pos2 = pygame.mouse.get_pos()
                x2 = pos2[0]
                y2 = pos2[1]
                xv = x2 - x1
                yv = y2 - y1

        pygame.display.update()
        timer.tick(30)

main()
