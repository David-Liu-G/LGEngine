package engine;

import physics.PhysicsWorld;
import physics.RigidBody;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The thing that combines everything together
 * To make things run continuously, we will extend TimerTask here
 */
public class LGEngine extends TimerTask implements KeyListener {
    public final List<LGNode> nodeList;
    private final LGFrame frame;
    private final LGPanel panel;
    private final Graphics2D g2d;
    private final PhysicsWorld physicsWorld;
    private long lastTime;

    public LGEngine(final int width, final int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.nodeList = new ArrayList<>();
        this.frame = new LGFrame(width, height);
        this.panel = new LGPanel(bufferedImage);
        this.g2d = bufferedImage.createGraphics();
        this.physicsWorld = new PhysicsWorld(width, height);
        this.lastTime = System.currentTimeMillis();
    }

    public void register(LGNode node) {
        nodeList.add(node);
        
        // If the node has a physics body, add it to the physics world
        if (node instanceof game.Box) {
            game.Box box = (game.Box) node;
            physicsWorld.addBody(box.getPhysicsBody());
        } else if (node instanceof game.BuildingBlock) {
            game.BuildingBlock block = (game.BuildingBlock) node;
            physicsWorld.addBody(block.getPhysicsBody());
        } else if (node instanceof game.Cannonball) {
            game.Cannonball ball = (game.Cannonball) node;
            physicsWorld.addBody(ball.getPhysicsBody());
        }
    }

    /**
     * @param period How long each frame lasts
     */
    public void start(final long period) {
        frame.constructFrame();
        frame.register(panel);
        frame.getFrame().addKeyListener(this);
        new Timer().schedule(this, 0, period);
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        // Calculate delta time
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastTime) / 1000.0; // Convert to seconds
        lastTime = currentTime;
        
        // Update physics simulation
        physicsWorld.update(deltaTime);
        
        // First, we clean the image
        panel.clear(g2d);
        // Next, we update image
        nodeList.forEach(item -> item.render(g2d));
        // Next, we update the window
        panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        nodeList.forEach(item->item.keyPressed(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    /**
     * Get the physics world for external control
     */
    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }
    
    /**
     * Set gravity strength
     */
    public void setGravity(double gravity) {
        physicsWorld.setGravity(gravity);
    }
    
    /**
     * Add a physics body directly to the world
     */
    public void addPhysicsBody(RigidBody body) {
        physicsWorld.addBody(body);
    }
    
    /**
     * Register cannonballs from a cannon
     */
    public void registerCannonballs(List<game.Cannonball> cannonballs) {
        for (game.Cannonball ball : cannonballs) {
            if (ball.isActive()) {
                physicsWorld.addBody(ball.getPhysicsBody());
            }
        }
    }
}
