import engine.LGEngine;
import game.*;

import java.util.List;

public class App {
    // Castle destruction game with cannon

    public static void main(String[] args) {
        LGEngine engine = new LGEngine(800, 600);

        // Create cannon
        Cannon cannon = new Cannon(50, 550);
        engine.register(cannon);
        
        // Build castle
        List<BuildingBlock> castle = CastleBuilder.buildComplexCastle(400, 550);
        for (BuildingBlock block : castle) {
            engine.register(block);
        }
        
        // Set realistic gravity
        engine.setGravity(9.8);
        
        // Start the engine at 60 FPS
        engine.start(16);
        
        // Game loop for cannonball management
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(16); // 60 FPS
                    
                    // Update cannon
                    cannon.update();
                    
                    // Register new cannonballs
                    List<Cannonball> activeBalls = cannon.getActiveCannonballs();
                    for (Cannonball ball : activeBalls) {
                        if (!engine.nodeList.contains(ball)) {
                            engine.register(ball);
                        }
                    }
                    
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
