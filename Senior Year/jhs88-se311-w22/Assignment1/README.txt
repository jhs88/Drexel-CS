Joseph Scherreik
CS 311

To run just open in Jetbrains and use configurations in .idea folder.

I tried to get stop words implement, but I could not get it to work as intended. I commented it out and
thought I should explain my reasoning. I added a class in the Shifter called rmStopWords.
This function would loop through the lines of the file and then loop through the stop words list.
It will compare the first word of each line to the stop words. If it finds that the first word is a stop word
it will remove that line from the lines. For some reason most likely indexing issues I can't seem to get it to remove
the line with the remove() function. I would like to know if there are any better ways to implement this.