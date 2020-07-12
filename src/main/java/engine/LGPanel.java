package engine;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This is the placeholder for our image
 * <p>
 * Game is like animation, it's showing a different image every period. All we need to do is to create a image and
 * update it
 */
public class LGPanel extends JPanel {
    private final BufferedImage bufferedImage;

    public LGPanel(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    /**
     * We don't want to paint on top of the existing image
     * We need to clear the image first before we continue every frame
     */
    public void clear(final Graphics2D g2d) {
        // We will use black as the default background color
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /**
     * We are going to use this method, which is coming from JPanel to update the image
     * You need to call repaint() every time the image is updated
     *
     * @param g The painter
     */
    public void paintComponent(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, null);
    }
}
