package maze;

import java.awt.*;

public class BrownDoor extends Door {

    public BrownDoor(final Room r1, final Room r2) {
        super(r1, r2);
    }

    @Override
    public Color getColor() {
        return Color.darkGray;
    }

}
