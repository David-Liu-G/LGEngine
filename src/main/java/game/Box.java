package game;

import engine.LGNode;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Let's make a box that moves right
 */
public class Box implements LGNode {
    private int x = 0;


    /**
     * Update the image
     *
     * @param g2d The painter
     */
    @Override
    public void render(Graphics2D g2d) {
        x++;
        g2d.setColor(Color.red);
        g2d.fillRect(x, 100, 50, 50);
    }
}
