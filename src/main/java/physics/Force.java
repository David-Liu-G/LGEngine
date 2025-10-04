package physics;

/**
 * Represents a force that can be applied to rigid bodies
 */
public abstract class Force {
    protected double magnitude;
    protected boolean active;
    
    public Force(double magnitude) {
        this.magnitude = magnitude;
        this.active = true;
    }
    
    public double getMagnitude() { return magnitude; }
    public void setMagnitude(double magnitude) { this.magnitude = magnitude; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    /**
     * Calculate the force components for a given rigid body
     * @param body The rigid body to calculate force for
     * @return Array with [forceX, forceY]
     */
    public abstract double[] calculate(RigidBody body);
}

/**
 * Gravity force - applies constant downward force
 */
class GravityForce extends Force {
    private double gravityX, gravityY;
    
    public GravityForce(double magnitude) {
        super(magnitude);
        this.gravityX = 0;
        this.gravityY = magnitude;
    }
    
    public GravityForce(double gravityX, double gravityY) {
        super(Math.sqrt(gravityX * gravityX + gravityY * gravityY));
        this.gravityX = gravityX;
        this.gravityY = gravityY;
    }
    
    @Override
    public double[] calculate(RigidBody body) {
        if (!active || body.isStatic()) {
            return new double[]{0, 0};
        }
        return new double[]{gravityX * body.getMass(), gravityY * body.getMass()};
    }
    
    /**
     * Update gravity values
     */
    public void setGravity(double gravityX, double gravityY) {
        this.gravityX = gravityX;
        this.gravityY = gravityY;
        this.magnitude = Math.sqrt(gravityX * gravityX + gravityY * gravityY);
    }
}

/**
 * Drag force - applies resistance proportional to velocity
 */
class DragForce extends Force {
    private double dragCoefficient;
    
    public DragForce(double magnitude, double dragCoefficient) {
        super(magnitude);
        this.dragCoefficient = dragCoefficient;
    }
    
    @Override
    public double[] calculate(RigidBody body) {
        if (!active || body.isStatic()) {
            return new double[]{0, 0};
        }
        
        double velocityX = body.getVelocityX();
        double velocityY = body.getVelocityY();
        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        
        if (speed == 0) {
            return new double[]{0, 0};
        }
        
        double dragMagnitude = dragCoefficient * speed * speed;
        double dragX = -(velocityX / speed) * dragMagnitude;
        double dragY = -(velocityY / speed) * dragMagnitude;
        
        return new double[]{dragX, dragY};
    }
}

/**
 * Spring force - applies force towards a target position
 */
class SpringForce extends Force {
    private double targetX, targetY;
    private double springConstant;
    private double damping;
    
    public SpringForce(double magnitude, double targetX, double targetY, double springConstant, double damping) {
        super(magnitude);
        this.targetX = targetX;
        this.targetY = targetY;
        this.springConstant = springConstant;
        this.damping = damping;
    }
    
    @Override
    public double[] calculate(RigidBody body) {
        if (!active || body.isStatic()) {
            return new double[]{0, 0};
        }
        
        double displacementX = targetX - body.getCenterX();
        double displacementY = targetY - body.getCenterY();
        
        double springForceX = displacementX * springConstant;
        double springForceY = displacementY * springConstant;
        
        // Add damping
        double dampingForceX = -body.getVelocityX() * damping;
        double dampingForceY = -body.getVelocityY() * damping;
        
        return new double[]{springForceX + dampingForceX, springForceY + dampingForceY};
    }
}
