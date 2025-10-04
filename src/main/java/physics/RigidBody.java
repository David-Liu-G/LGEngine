package physics;

import java.awt.geom.Rectangle2D;

/**
 * Represents a physics object with mass, velocity, and position
 */
public class RigidBody {
    private double mass;
    private double x, y;
    private double velocityX, velocityY;
    private double accelerationX, accelerationY;
    private double width, height;
    private boolean isStatic;
    private double restitution; // Bounciness factor (0 = no bounce, 1 = perfect bounce)
    private double friction; // Friction coefficient (0 = no friction, 1 = maximum friction)
    
    public RigidBody(double x, double y, double width, double height, double mass) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.velocityX = 0;
        this.velocityY = 0;
        this.accelerationX = 0;
        this.accelerationY = 0;
        this.isStatic = false;
        this.restitution = 0.8; // Default bounciness
        this.friction = 0.1; // Default friction
    }
    
    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getMass() { return mass; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public double getAccelerationX() { return accelerationX; }
    public double getAccelerationY() { return accelerationY; }
    public boolean isStatic() { return isStatic; }
    public double getRestitution() { return restitution; }
    public double getFriction() { return friction; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setMass(double mass) { this.mass = mass; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    public void setAccelerationX(double accelerationX) { this.accelerationX = accelerationX; }
    public void setAccelerationY(double accelerationY) { this.accelerationY = accelerationY; }
    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }
    public void setRestitution(double restitution) { this.restitution = Math.max(0, Math.min(1, restitution)); }
    public void setFriction(double friction) { this.friction = Math.max(0, Math.min(1, friction)); }
    
    /**
     * Apply a force to this rigid body
     * @param forceX Force in X direction
     * @param forceY Force in Y direction
     */
    public void applyForce(double forceX, double forceY) {
        if (isStatic) return;
        
        accelerationX += forceX / mass;
        accelerationY += forceY / mass;
    }
    
    /**
     * Apply an impulse (instantaneous change in velocity)
     * @param impulseX Impulse in X direction
     * @param impulseY Impulse in Y direction
     */
    public void applyImpulse(double impulseX, double impulseY) {
        if (isStatic) return;
        
        velocityX += impulseX / mass;
        velocityY += impulseY / mass;
    }
    
    /**
     * Update position based on velocity and acceleration
     * @param deltaTime Time step for integration
     */
    public void integrate(double deltaTime) {
        if (isStatic) return;
        
        // Update velocity based on acceleration
        velocityX += accelerationX * deltaTime;
        velocityY += accelerationY * deltaTime;
        
        // Update position based on velocity
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        
        // Reset acceleration for next frame
        accelerationX = 0;
        accelerationY = 0;
    }
    
    /**
     * Get the bounding rectangle for collision detection
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }
    
    /**
     * Check if this body collides with another body
     */
    public boolean collidesWith(RigidBody other) {
        return getBounds().intersects(other.getBounds());
    }
    
    /**
     * Get the center X position
     */
    public double getCenterX() {
        return x + width / 2;
    }
    
    /**
     * Get the center Y position
     */
    public double getCenterY() {
        return y + height / 2;
    }
}
