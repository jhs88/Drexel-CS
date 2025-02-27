package maze;

import java.io.IOException;
import java.util.ArrayList;

public class BlueMazeGameCreator extends MazeGameCreator {

    public static Maze loadMaze(final String path) throws IOException {
        Maze maze = new Maze();
        String[] mazeFile = readFileAsString(path).split("\n");
        ArrayList<Door> doors = new ArrayList<>();

        for (String s : mazeFile) {
            if (s.contains("room")) {
                String[] room = s.split(" ");
                maze.addRoom(makeRoom(Integer.parseInt(room[1])));
            } else if (s.contains("door")) {
                String[] doorFile = s.split(" ");
                Door door = makeDoor(maze.getRoom(Integer.parseInt(doorFile[2])), maze.getRoom(Integer.parseInt(doorFile[3])));
                if (doorFile[4].contains("open")) {
                    door.setOpen(true);
                } else if (doorFile[4].contains("close")) {
                    door.setOpen(false);
                }
                doors.add(door);
            }
        }

        for (String s : mazeFile) {
            String[] roomFile = s.split(" ");
            if (s.contains("room")) {
                Room room = maze.getRoom(Integer.parseInt(roomFile[1]));
                room.setSide(Direction.North, roomFile[2].contains("wall") ? makeWall() :
                        roomFile[2].startsWith("d") ? doors.get(Integer.parseInt(roomFile[2].substring(1).trim())) :
                                maze.getRoom(Integer.parseInt(roomFile[2].trim())));
                room.setSide(Direction.South, roomFile[3].contains("wall") ? makeWall() :
                        roomFile[3].startsWith("d") ? doors.get(Integer.parseInt(roomFile[3].substring(1).trim())) :
                                maze.getRoom(Integer.parseInt(roomFile[3].trim())));
                room.setSide(Direction.East, roomFile[4].contains("wall") ? makeWall() :
                        roomFile[4].startsWith("d") ? doors.get(Integer.parseInt(roomFile[4].substring(1).trim())) :
                                maze.getRoom(Integer.parseInt(roomFile[4].trim())));
                room.setSide(Direction.West, roomFile[5].contains("wall") ? makeWall() :
                        roomFile[5].startsWith("d") ? doors.get(Integer.parseInt(roomFile[5].substring(1).trim())) :
                                maze.getRoom(Integer.parseInt(roomFile[5].trim())));
            }
        }

        maze.setCurrentRoom(0);
        return maze;
    }

    public static Wall makeWall() {
        return new BlueWall();
    }

    public static Door makeDoor(Room r1, Room r2) {
        return new BrownDoor(r1, r2);
    }

    public static Room makeRoom(int r) {
        return new GreenRoom(r);
    }

}
