package game;

import engine.LGNode;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball implements LGNode {
    private double y = 0;
    private double speed = 0;
    private double acc = 0.5;

    /**
     * Update the image
     *
     * @param g2d The painter
     */
    @Override
    public void render(Graphics2D g2d) {
        y += speed;
        speed += acc;
        // When ball hits the bottom, ball bounces
        if (y > 500) {
            speed *= -1;
        }
        g2d.setColor(Color.yellow);
        g2d.fillOval(100, (int) y, 50, 50);
    }
}
