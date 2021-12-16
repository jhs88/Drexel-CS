package maze;

import java.awt.*;

public class GreenRoom extends Room {

    public GreenRoom(int r) {
        super(r);
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

}
