package game;

import engine.LGNode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A cannon that can shoot cannonballs
 */
public class Cannon implements LGNode {
    private final int x, y;
    private final int width, height;
    private double angle; // Angle in radians
    private final double maxAngle = Math.PI / 3; // 60 degrees max
    private final double minAngle = -Math.PI / 3; // -60 degrees min
    private final double power; // Shot power
    private final List<Cannonball> cannonballs;
    private int cooldown; // Frames between shots
    
    public Cannon(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 20;
        this.angle = 0; // Start pointing straight
        this.power = 300.0; // Shot power
        this.cannonballs = new ArrayList<>();
        this.cooldown = 0;
    }
    
    /**
     * Aim the cannon up
     */
    public void aimUp() {
        angle += 0.05;
        if (angle > maxAngle) angle = maxAngle;
    }
    
    /**
     * Aim the cannon down
     */
    public void aimDown() {
        angle -= 0.05;
        if (angle < minAngle) angle = minAngle;
    }
    
    /**
     * Fire a cannonball
     */
    public void fire() {
        if (cooldown > 0) return; // Still on cooldown
        
        // Calculate velocity based on angle and power
        double velocityX = Math.cos(angle) * power;
        double velocityY = Math.sin(angle) * power;
        
        // Create cannonball at cannon tip
        int ballX = (int)(x + Math.cos(angle) * (width + 10));
        int ballY = (int)(y + Math.sin(angle) * (width + 10));
        
        Cannonball ball = new Cannonball(ballX, ballY, velocityX, velocityY);
        cannonballs.add(ball);
        
        cooldown = 30; // 0.5 second cooldown at 60 FPS
    }
    
    /**
     * Update cannon and cannonballs
     */
    public void update() {
        if (cooldown > 0) cooldown--;
        
        // Update cannonballs
        cannonballs.removeIf(ball -> {
            ball.update();
            return !ball.isActive();
        });
    }
    
    /**
     * Get all active cannonballs
     */
    public List<Cannonball> getCannonballs() {
        return new ArrayList<>(cannonballs);
    }
    
    /**
     * Get cannonballs for physics world registration
     */
    public List<Cannonball> getActiveCannonballs() {
        return cannonballs.stream()
                .filter(Cannonball::isActive)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Handle key presses for cannon control
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                aimUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                aimDown();
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                fire();
                break;
        }
    }

    /**
     * Render the cannon and cannonballs
     */
    @Override
    public void render(Graphics2D g2d) {
        // Draw cannon base
        g2d.setColor(new Color(101, 67, 33)); // Brown
        g2d.fillRect(x, y, width, height);
        
        // Draw cannon barrel
        int barrelLength = 30;
        int barrelX = (int)(x + width/2 + Math.cos(angle) * barrelLength/2);
        int barrelY = (int)(y + height/2 + Math.sin(angle) * barrelLength/2);
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(barrelX - 8, barrelY - 8, 16, 16);
        
        // Draw cannon barrel line
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x + width/2, y + height/2, 
                    (int)(x + width/2 + Math.cos(angle) * barrelLength), 
                    (int)(y + height/2 + Math.sin(angle) * barrelLength));
        
        // Draw cannonballs
        for (Cannonball ball : cannonballs) {
            ball.render(g2d);
        }
        
        // Draw power indicator
        g2d.setColor(Color.RED);
        g2d.drawString("Power: " + (int)power, x - 20, y - 10);
        g2d.drawString("Angle: " + (int)(Math.toDegrees(angle)) + "°", x - 20, y + 5);
    }
}
