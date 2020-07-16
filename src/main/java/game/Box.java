package game;

import engine.LGNode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Our snake game will basically be a board with a bunch of boxes
 * We control how each box behaves
 */
public class Box implements LGNode {
    private final Rectangle area;
    private State state;

    public Box(final int x, final int y, final int size) {
        this.area = new Rectangle(x, y, size, size);
        this.state = State.Empty;
    }

    public void setState(final State state) {
        this.state = state;
    }

    /**
     * If state is Filled, we use green to indicate that this box is taken
     * if state if empty, we use black as the default color
     * @param g2d The painter
     */
    @Override
    public void render(Graphics2D g2d) {
        switch (state) {
            case Filled:
                g2d.setColor(Color.green);
                break;
            case Empty:
                g2d.setColor(Color.black);
                break;
        }
        g2d.fill(area);
        g2d.setColor(Color.gray);
        g2d.draw(area);
    }

    enum State {
        Filled, Empty
    }
}
