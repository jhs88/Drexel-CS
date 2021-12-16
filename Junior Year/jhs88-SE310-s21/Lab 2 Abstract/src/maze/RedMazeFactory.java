package maze;

public class RedMazeFactory extends MazeFactory {

	public Wall makeWall() {
		return new RedWall();
	}

	public Door makeDoor(Room r1, Room r2) {
		return new Door(r1, r2);
	}

	public Room makeRoom(int r) {
		return new PinkRoom(r);
	}

}
