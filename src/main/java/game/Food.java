package game;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for generating the food on the board
 * you can put this inside snake class as well
 */
public class Food {
    private static final Random random = new Random();
    /**
     * The location of the food
     */
    private Point location;

    public Food(final Set<Point> board, final List<Point> snake) {
        generateFood(board, snake);
    }

    public void generateFood(final Set<Point> board, final List<Point> snake) {
        final List<Point> emptyCells = board.stream()
                .filter(item -> !snake.contains(item))
                .collect(Collectors.toList());
        location = emptyCells.get(random.nextInt(emptyCells.size()));
    }

    public Point getLocation() {
        return location;
    }
}
