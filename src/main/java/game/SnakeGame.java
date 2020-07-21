package game;

import engine.LGNode;
import slowTimer.SlowTimer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SnakeGame implements LGNode {
    // So the size of the panel should be 15 * 20 = 300
    private static final int ROW = 15;
    private static final int COL = 15;
    private static final int SIZE = 20;

    /**
     * We use this map to represent the actual board
     */
    private Map<Point, Box> boxMap;
    private Snake snake;
    private Food food;
    private SlowTimer slowTimer;

    public SnakeGame() {
        this.boxMap = IntStream.range(0, ROW)
                .boxed()
                .map(row -> IntStream.range(0, COL)
                        .boxed()
                        .map(col -> new Point(col, row))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(p -> p, p -> new Box(p.x * SIZE, p.y * SIZE, SIZE)));
        this.snake = new Snake();
        this.food = new Food(this.boxMap.keySet(), this.snake.getBody());
        // We can decrease the threshold to make the snake move faster
        this.slowTimer = new SlowTimer(5, () -> {
            if (snake.move(this.food)) {
                this.food.generateFood(this.boxMap.keySet(), this.snake.getBody());
            }
        });
    }

    @Override
    public void render(Graphics2D g2d) {
        boxMap.values().forEach(box -> box.setState(Box.State.Empty));
        slowTimer.render(g2d);
        boxMap.get(food.getLocation()).setState(Box.State.Food);
        snake.getBody().forEach(p -> boxMap.get(p).setState(Box.State.Filled));
        boxMap.values().forEach(box -> box.render(g2d));
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                snake.setDirection(Snake.Direction.up);
                break;
            case KeyEvent.VK_DOWN:
                snake.setDirection(Snake.Direction.down);
                break;
            case KeyEvent.VK_LEFT:
                snake.setDirection(Snake.Direction.left);
                break;
            case KeyEvent.VK_RIGHT:
                snake.setDirection(Snake.Direction.right);
                break;
        }
    }
}
