package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The thing that combines everything together
 * To make things run continuously, we will extend TimerTask here
 */
public class LGEngine extends TimerTask {
    private final List<LGNode> nodeList;
    private final LGFrame frame;
    private final LGPanel panel;
    private final Graphics2D g2d;

    public LGEngine(final int width, final int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.nodeList = new ArrayList<>();
        this.frame = new LGFrame(width, height);
        this.panel = new LGPanel(bufferedImage);
        this.g2d = bufferedImage.createGraphics();
    }

    public void register(LGNode node) {
        nodeList.add(node);
    }

    /**
     * @param period How long each frame lasts
     */
    public void start(final long period) {
        frame.constructFrame();
        frame.register(panel);
        new Timer().schedule(this, 0, period);
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        // First, we clean the image
        panel.clear(g2d);
        // Next, we update image
        nodeList.forEach(item -> item.render(g2d));
        // Next, we update the window
        panel.repaint();
    }
}
