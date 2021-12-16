/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import maze.ui.MazeViewer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SimpleMazeGame {

	public static maze.Maze createMaze() {

		maze.Maze maze = new maze.Maze();
		maze.addRoom(new maze.Room(0));
		maze.getRoom(0).setSide(Direction.North, new Wall());
		maze.getRoom(0).setSide(Direction.East, new Wall());
		maze.getRoom(0).setSide(Direction.West, new Wall());

		maze.addRoom(new maze.Room(1));
		maze.getRoom(1).setSide(Direction.South, new Wall());
		maze.getRoom(1).setSide(Direction.East, new Wall());
		maze.getRoom(1).setSide(Direction.West, new Wall());

		Door door12 = new Door(maze.getRoom(0),maze.getRoom(1));
		door12.setOpen(true);
		maze.getRoom(0).setSide(Direction.South, door12);
		maze.getRoom(1).setSide(Direction.North, door12);

		return maze;
	}

	public static String readFileAsString(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)));
	}

	public static maze.Maze loadMaze(final String path) throws IOException {
		maze.Maze maze = new maze.Maze();
		String[] mazeFile = readFileAsString(path).split("\n");
		ArrayList<Door> doors = new ArrayList<>();

		for (String s : mazeFile) {
			if (s.contains("room")) {
				String[] room = s.split(" ");
				maze.addRoom(new Room(Integer.parseInt(room[1])));
			} else if (s.contains("door")) {
				String[] doorFile = s.split(" ");
				Door door = new Door(maze.getRoom(Integer.parseInt(doorFile[2])), maze.getRoom(Integer.parseInt(doorFile[3])));
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
				room.setSide(Direction.North, roomFile[2].contains("wall") ? new Wall() :
						roomFile[2].startsWith("d") ? doors.get(Integer.parseInt(roomFile[2].substring(1).trim())) :
								maze.getRoom(Integer.parseInt(roomFile[2].trim())));
				room.setSide(Direction.South, roomFile[3].contains("wall") ? new Wall() :
						roomFile[3].startsWith("d") ? doors.get(Integer.parseInt(roomFile[3].substring(1).trim())) :
								maze.getRoom(Integer.parseInt(roomFile[3].trim())));
				room.setSide(Direction.East, roomFile[4].contains("wall") ? new Wall() :
						roomFile[4].startsWith("d") ? doors.get(Integer.parseInt(roomFile[4].substring(1).trim())) :
								maze.getRoom(Integer.parseInt(roomFile[4].trim())));
				room.setSide(Direction.West, roomFile[5].contains("wall") ? new Wall() :
						roomFile[5].startsWith("d") ? doors.get(Integer.parseInt(roomFile[5].substring(1).trim())) :
								maze.getRoom(Integer.parseInt(roomFile[5].trim())));
			}
		}

		maze.setCurrentRoom(0);
		return maze;
	}

	public static void main(String[] args) throws IOException {
		maze.Maze createM = createMaze();
		maze.Maze maze = loadMaze(System.getProperty("user.dir") + "/large.maze");
		MazeViewer createV = new MazeViewer(createM);
	    MazeViewer viewer = new MazeViewer(maze);
	    createV.run();
	    viewer.run();
	}
}
