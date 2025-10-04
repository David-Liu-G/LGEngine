package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds castle structures with different types of blocks
 */
public class CastleBuilder {
    
    /**
     * Build a simple castle structure
     */
    public static List<BuildingBlock> buildSimpleCastle(int startX, int groundY) {
        List<BuildingBlock> blocks = new ArrayList<>();
        int blockWidth = 30;
        int blockHeight = 20;
        
        // Ground level - stone foundation
        for (int i = 0; i < 8; i++) {
            blocks.add(new BuildingBlock(startX + i * blockWidth, groundY - blockHeight, 
                                      blockWidth, blockHeight, 0)); // Stone
        }
        
        // Second level - mixed materials
        for (int i = 1; i < 7; i++) {
            int material = (i % 2 == 0) ? 2 : 1; // Alternating brick and wood
            blocks.add(new BuildingBlock(startX + i * blockWidth, groundY - 2 * blockHeight, 
                                      blockWidth, blockHeight, material));
        }
        
        // Third level - smaller
        for (int i = 2; i < 6; i++) {
            blocks.add(new BuildingBlock(startX + i * blockWidth, groundY - 3 * blockHeight, 
                                      blockWidth, blockHeight, 2)); // Brick
        }
        
        // Tower on the right
        blocks.add(new BuildingBlock(startX + 7 * blockWidth, groundY - 2 * blockHeight, 
                                  blockWidth, blockHeight, 0)); // Stone
        blocks.add(new BuildingBlock(startX + 7 * blockWidth, groundY - 3 * blockHeight, 
                                  blockWidth, blockHeight, 0)); // Stone
        blocks.add(new BuildingBlock(startX + 7 * blockWidth, groundY - 4 * blockHeight, 
                                  blockWidth, blockHeight, 0)); // Stone
        
        return blocks;
    }
    
    /**
     * Build a more complex castle with walls and towers
     */
    public static List<BuildingBlock> buildComplexCastle(int startX, int groundY) {
        List<BuildingBlock> blocks = new ArrayList<>();
        int blockWidth = 25;
        int blockHeight = 18;
        
        // Left wall
        for (int i = 0; i < 3; i++) {
            blocks.add(new BuildingBlock(startX, groundY - (i + 1) * blockHeight, 
                                      blockWidth, blockHeight, 0)); // Stone
        }
        
        // Right wall
        for (int i = 0; i < 3; i++) {
            blocks.add(new BuildingBlock(startX + 8 * blockWidth, groundY - (i + 1) * blockHeight, 
                                      blockWidth, blockHeight, 0)); // Stone
        }
        
        // Back wall
        for (int i = 1; i < 8; i++) {
            blocks.add(new BuildingBlock(startX + i * blockWidth, groundY - 3 * blockHeight, 
                                      blockWidth, blockHeight, 0)); // Stone
        }
        
        // Front wall (lower)
        for (int i = 1; i < 8; i++) {
            blocks.add(new BuildingBlock(startX + i * blockWidth, groundY - blockHeight, 
                                      blockWidth, blockHeight, 1)); // Wood
        }
        
        // Central tower
        blocks.add(new BuildingBlock(startX + 3 * blockWidth, groundY - 4 * blockHeight, 
                                  blockWidth, blockHeight, 2)); // Brick
        blocks.add(new BuildingBlock(startX + 4 * blockWidth, groundY - 4 * blockHeight, 
                                  blockWidth, blockHeight, 2)); // Brick
        blocks.add(new BuildingBlock(startX + 3 * blockWidth, groundY - 5 * blockHeight, 
                                  blockWidth, blockHeight, 2)); // Brick
        blocks.add(new BuildingBlock(startX + 4 * blockWidth, groundY - 5 * blockHeight, 
                                  blockWidth, blockHeight, 2)); // Brick
        
        return blocks;
    }
    
    /**
     * Build a simple wall for target practice
     */
    public static List<BuildingBlock> buildSimpleWall(int startX, int groundY) {
        List<BuildingBlock> blocks = new ArrayList<>();
        int blockWidth = 30;
        int blockHeight = 20;
        
        // 3x3 wall
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int material = (row + col) % 3; // Different materials
                blocks.add(new BuildingBlock(startX + col * blockWidth, 
                                          groundY - (row + 1) * blockHeight, 
                                          blockWidth, blockHeight, material));
            }
        }
        
        return blocks;
    }
}
