package engine;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;

/**
 * We actually need a window to show everything
 * We will use JFrame for it
 */
public class LGFrame {
    private final int width;
    private final int height;
    private final JFrame frame;

    /**
     * @param width The width of the window
     * @param height The height of the window
     */
    public LGFrame(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.frame = new JFrame();
    }

    public void constructFrame() {
        // Set the size of the frame
        frame.getContentPane().setPreferredSize(new Dimension(width, height));
        // Set the layout of the frame
        frame.getContentPane().setLayout(null);
        // Set the size of the outer frame
        frame.pack();
        // Make frame visible
        frame.setVisible(true);
        // Make frame at the center of the screen
        frame.setLocationRelativeTo(null);
        // Close the program when window is closed
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * @param component The place holder for our image
     */
    public void register(final Component component) {
        component.setBounds(0, 0, width, height);
        frame.getContentPane().add(component);
    }
}
