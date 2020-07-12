package engine;

import java.awt.Graphics2D;

/**
 * This is the actor that will update the image
 * We can have multiple actors
 * You can image actor as the game object
 * Like a moving box, a character, etc
 */
public interface LGNode {
    /**
     * Update the image
     * @param g2d The painter
     */
    void render(Graphics2D g2d);
}
