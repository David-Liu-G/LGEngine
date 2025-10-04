package game;

import engine.LGNode;
import physics.RigidBody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A building block for constructing castles
 */
public class BuildingBlock implements LGNode {
    private final RigidBody physicsBody;
    private final Rectangle2D.Double area;
    private final Color color;
    private final int blockType; // 0 = stone, 1 = wood, 2 = brick

    public BuildingBlock(final int x, final int y, final int width, final int height, int blockType) {
        this.physicsBody = new RigidBody(x, y, width, height, 2.0); // Heavier than regular boxes
        this.area = new Rectangle2D.Double();
        this.blockType = blockType;
        
        // Set physics properties based on block type
        switch (blockType) {
            case 0: // Stone
                this.color = Color.GRAY;
                physicsBody.setRestitution(0.3);
                physicsBody.setFriction(0.8);
                physicsBody.setMass(3.0);
                break;
            case 1: // Wood
                this.color = new Color(139, 69, 19); // Brown
                physicsBody.setRestitution(0.5);
                physicsBody.setFriction(0.6);
                physicsBody.setMass(1.5);
                break;
            case 2: // Brick
                this.color = new Color(178, 34, 34); // Red
                physicsBody.setRestitution(0.4);
                physicsBody.setFriction(0.7);
                physicsBody.setMass(2.5);
                break;
            default:
                this.color = Color.GRAY;
                physicsBody.setRestitution(0.4);
                physicsBody.setFriction(0.7);
                physicsBody.setMass(2.0);
        }
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
     * Render the building block
     */
    @Override
    public void render(Graphics2D g2d) {
        updateArea();
        
        // Draw the block
        g2d.setColor(color);
        g2d.fill(area);
        
        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.draw(area);
        
        // Add some texture based on block type
        g2d.setColor(color.darker());
        switch (blockType) {
            case 0: // Stone texture
                g2d.drawLine((int)area.getX() + 5, (int)area.getY() + 5, 
                           (int)(area.getX() + area.getWidth() - 5), (int)area.getY() + 5);
                g2d.drawLine((int)area.getX() + 5, (int)(area.getY() + area.getHeight() - 5), 
                           (int)(area.getX() + area.getWidth() - 5), (int)(area.getY() + area.getHeight() - 5));
                break;
            case 1: // Wood grain
                for (int i = 0; i < 3; i++) {
                    int y = (int)area.getY() + 10 + i * 15;
                    if (y < area.getY() + area.getHeight() - 5) {
                        g2d.drawLine((int)area.getX() + 5, y, 
                                   (int)(area.getX() + area.getWidth() - 5), y);
                    }
                }
                break;
            case 2: // Brick pattern
                g2d.drawLine((int)(area.getX() + area.getWidth()/2), (int)area.getY() + 5, 
                           (int)(area.getX() + area.getWidth()/2), (int)(area.getY() + area.getHeight() - 5));
                break;
        }
    }
}
