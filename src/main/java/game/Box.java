package game;

import engine.LGNode;
import physics.RigidBody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A physics-enabled box that uses the physics engine for movement and collision
 */
public class Box implements LGNode {
    private final RigidBody physicsBody;
    private final Rectangle2D.Double area;

    public Box(final int x, final int y, final int size) {
        this.physicsBody = new RigidBody(x, y, size, size, 1.0); // Mass of 1.0
        this.area = new Rectangle2D.Double();
        
        // Set physics properties
        physicsBody.setRestitution(0.8); // Bouncy
        physicsBody.setFriction(0.1); // Low friction
    }
    
    /**
     * Get the physics body for external physics world management
     */
    public RigidBody getPhysicsBody() {
        return physicsBody;
    }
    
    /**
     * Update the visual area based on physics body position
     */
    private void updateArea() {
        area.setRect(physicsBody.getX(), physicsBody.getY(), 
                    physicsBody.getWidth(), physicsBody.getHeight());
    }

    /**
     * Render the box using the physics body's position
     *
     * @param g2d The painter
     */
    @Override
    public void render(Graphics2D g2d) {
        updateArea();
        g2d.setColor(Color.RED);
        g2d.fill(area);
        g2d.setColor(Color.gray);
        g2d.draw(area);
    }
}
