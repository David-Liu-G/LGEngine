package game;

import engine.LGNode;
import physics.RigidBody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * A cannonball projectile
 */
public class Cannonball implements LGNode {
    private final RigidBody physicsBody;
    private final Ellipse2D.Double area;
    private final Color color;
    private boolean active;
    private int lifetime; // Frames until removal

    public Cannonball(final int x, final int y, final double velocityX, final double velocityY) {
        this.physicsBody = new RigidBody(x, y, 12, 12, 0.8); // Small, light projectile
        this.area = new Ellipse2D.Double();
        this.color = Color.DARK_GRAY;
        this.active = true;
        this.lifetime = 300; // 5 seconds at 60 FPS
        
        // Set physics properties
        physicsBody.setRestitution(0.6); // Bouncy
        physicsBody.setFriction(0.3); // Low friction
        physicsBody.setVelocityX(velocityX);
        physicsBody.setVelocityY(velocityY);
    }
    
    /**
     * Get the physics body for external physics world management
     */
    public RigidBody getPhysicsBody() {
        return physicsBody;
    }
    
    /**
     * Check if the cannonball is still active
     */
    public boolean isActive() {
        return active && lifetime > 0;
    }
    
    /**
     * Update the cannonball (called each frame)
     */
    public void update() {
        if (!active) return;
        
        lifetime--;
        if (lifetime <= 0) {
            active = false;
        }
        
        // Remove if it goes too far off screen
        if (physicsBody.getX() < -100 || physicsBody.getX() > 600 || 
            physicsBody.getY() > 600) {
            active = false;
        }
    }
    
    /**
     * Update the visual area based on physics body position
     */
    private void updateArea() {
        area.setFrame(physicsBody.getX(), physicsBody.getY(), 
                     physicsBody.getWidth(), physicsBody.getHeight());
    }

    /**
     * Render the cannonball
     */
    @Override
    public void render(Graphics2D g2d) {
        if (!active) return;
        
        updateArea();
        
        // Draw the cannonball
        g2d.setColor(color);
        g2d.fill(area);
        
        // Draw highlight
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval((int)area.getX() + 2, (int)area.getY() + 2, 4, 4);
        
        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.draw(area);
    }
}
