package physics;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the physics simulation world
 */
public class PhysicsWorld {
    private List<RigidBody> bodies;
    private List<Force> forces;
    private double gravity;
    private double worldWidth, worldHeight;
    
    public PhysicsWorld(double worldWidth, double worldHeight) {
        this.bodies = new ArrayList<>();
        this.forces = new ArrayList<>();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.gravity = 9.8; // Default gravity
        
        // Add default gravity force
        addForce(new GravityForce(gravity));
    }
    
    /**
     * Add a rigid body to the physics world
     */
    public void addBody(RigidBody body) {
        bodies.add(body);
    }
    
    /**
     * Remove a rigid body from the physics world
     */
    public void removeBody(RigidBody body) {
        bodies.remove(body);
    }
    
    /**
     * Add a force to the physics world
     */
    public void addForce(Force force) {
        forces.add(force);
    }
    
    /**
     * Remove a force from the physics world
     */
    public void removeForce(Force force) {
        forces.remove(force);
    }
    
    /**
     * Update the physics simulation
     * @param deltaTime Time step for integration
     */
    public void update(double deltaTime) {
        // Apply forces to all bodies
        for (RigidBody body : bodies) {
            if (body.isStatic()) continue;
            
            // Apply all forces
            for (Force force : forces) {
                double[] forceComponents = force.calculate(body);
                body.applyForce(forceComponents[0], forceComponents[1]);
            }
        }
        
        // Integrate motion for all bodies
        for (RigidBody body : bodies) {
            body.integrate(deltaTime);
        }
        
        // Handle collisions
        handleCollisions();
        
        // Handle world boundaries
        handleWorldBoundaries();
    }
    
    /**
     * Handle collisions between all bodies
     */
    private void handleCollisions() {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                RigidBody bodyA = bodies.get(i);
                RigidBody bodyB = bodies.get(j);
                
                if (bodyA.collidesWith(bodyB)) {
                    resolveCollision(bodyA, bodyB);
                }
            }
        }
    }
    
    /**
     * Resolve collision between two bodies
     */
    private void resolveCollision(RigidBody bodyA, RigidBody bodyB) {
        // Simple collision response - separate bodies
        double overlapX = 0, overlapY = 0;
        
        // Calculate overlap
        double leftA = bodyA.getX();
        double rightA = bodyA.getX() + bodyA.getWidth();
        double topA = bodyA.getY();
        double bottomA = bodyA.getY() + bodyA.getHeight();
        
        double leftB = bodyB.getX();
        double rightB = bodyB.getX() + bodyB.getWidth();
        double topB = bodyB.getY();
        double bottomB = bodyB.getY() + bodyB.getHeight();
        
        if (rightA > leftB && leftA < rightB && bottomA > topB && topA < bottomB) {
            double overlapLeft = rightA - leftB;
            double overlapRight = rightB - leftA;
            double overlapTop = bottomA - topB;
            double overlapBottom = bottomB - topA;
            
            if (overlapLeft < overlapRight && overlapLeft < overlapTop && overlapLeft < overlapBottom) {
                overlapX = -overlapLeft;
            } else if (overlapRight < overlapTop && overlapRight < overlapBottom) {
                overlapX = overlapRight;
            } else if (overlapTop < overlapBottom) {
                overlapY = -overlapTop;
            } else {
                overlapY = overlapBottom;
            }
        }
        
        // Separate bodies
        if (!bodyA.isStatic() && !bodyB.isStatic()) {
            bodyA.setX(bodyA.getX() + overlapX * 0.5);
            bodyA.setY(bodyA.getY() + overlapY * 0.5);
            bodyB.setX(bodyB.getX() - overlapX * 0.5);
            bodyB.setY(bodyB.getY() - overlapY * 0.5);
        } else if (!bodyA.isStatic()) {
            bodyA.setX(bodyA.getX() + overlapX);
            bodyA.setY(bodyA.getY() + overlapY);
        } else if (!bodyB.isStatic()) {
            bodyB.setX(bodyB.getX() - overlapX);
            bodyB.setY(bodyB.getY() - overlapY);
        }
        
        // Apply collision response (bounce)
        if (Math.abs(overlapX) > Math.abs(overlapY)) {
            // Horizontal collision
            if (!bodyA.isStatic() && !bodyB.isStatic()) {
                double totalMass = bodyA.getMass() + bodyB.getMass();
                double newVelA = (bodyA.getVelocityX() * (bodyA.getMass() - bodyB.getMass()) + 
                                2 * bodyB.getMass() * bodyB.getVelocityX()) / totalMass;
                double newVelB = (bodyB.getVelocityX() * (bodyB.getMass() - bodyA.getMass()) + 
                                2 * bodyA.getMass() * bodyA.getVelocityX()) / totalMass;
                
                bodyA.setVelocityX(newVelA * bodyA.getRestitution());
                bodyB.setVelocityX(newVelB * bodyB.getRestitution());
            } else if (!bodyA.isStatic()) {
                bodyA.setVelocityX(-bodyA.getVelocityX() * bodyA.getRestitution());
            } else if (!bodyB.isStatic()) {
                bodyB.setVelocityX(-bodyB.getVelocityX() * bodyB.getRestitution());
            }
        } else {
            // Vertical collision
            if (!bodyA.isStatic() && !bodyB.isStatic()) {
                double totalMass = bodyA.getMass() + bodyB.getMass();
                double newVelA = (bodyA.getVelocityY() * (bodyA.getMass() - bodyB.getMass()) + 
                                2 * bodyB.getMass() * bodyB.getVelocityY()) / totalMass;
                double newVelB = (bodyB.getVelocityY() * (bodyB.getMass() - bodyA.getMass()) + 
                                2 * bodyA.getMass() * bodyA.getVelocityY()) / totalMass;
                
                bodyA.setVelocityY(newVelA * bodyA.getRestitution());
                bodyB.setVelocityY(newVelB * bodyB.getRestitution());
            } else if (!bodyA.isStatic()) {
                bodyA.setVelocityY(-bodyA.getVelocityY() * bodyA.getRestitution());
            } else if (!bodyB.isStatic()) {
                bodyB.setVelocityY(-bodyB.getVelocityY() * bodyB.getRestitution());
            }
        }
    }
    
    /**
     * Handle world boundaries (ground, walls)
     */
    private void handleWorldBoundaries() {
        for (RigidBody body : bodies) {
            if (body.isStatic()) continue;
            
            // Ground collision
            if (body.getY() + body.getHeight() > worldHeight) {
                body.setY(worldHeight - body.getHeight());
                body.setVelocityY(-body.getVelocityY() * body.getRestitution());
                // Apply friction
                body.setVelocityX(body.getVelocityX() * (1 - body.getFriction()));
            }
            
            // Ceiling collision
            if (body.getY() < 0) {
                body.setY(0);
                body.setVelocityY(-body.getVelocityY() * body.getRestitution());
            }
            
            // Left wall collision
            if (body.getX() < 0) {
                body.setX(0);
                body.setVelocityX(-body.getVelocityX() * body.getRestitution());
            }
            
            // Right wall collision
            if (body.getX() + body.getWidth() > worldWidth) {
                body.setX(worldWidth - body.getWidth());
                body.setVelocityX(-body.getVelocityX() * body.getRestitution());
            }
        }
    }
    
    /**
     * Get all bodies in the world
     */
    public List<RigidBody> getBodies() {
        return new ArrayList<>(bodies);
    }
    
    /**
     * Set gravity strength
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
        // Update gravity force
        for (Force force : forces) {
            if (force instanceof GravityForce) {
                ((GravityForce) force).setGravity(0, gravity);
                break;
            }
        }
    }
    
    /**
     * Get gravity strength
     */
    public double getGravity() {
        return gravity;
    }
}
