package game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * THis will represent our snake
 */
public class Snake {
    private LinkedList<Point> body;
    private Direction direction;

    public Snake() {
        this.body = new LinkedList<>();
        this.direction = Direction.right;
        body.addFirst(new Point(0, 0));
        body.addFirst(new Point(1, 0));
        body.addFirst(new Point(2, 0));
    }

    /**
     * @param food The food on the board
     * @return true if snake gets food, false otherwise
     */
    public boolean move(final Food food) {
        final Point nextPoint = getNextPoint();
        if (body.contains(nextPoint)) {
            throw new RuntimeException("Game end!");
        }
        body.addFirst(nextPoint);
        // If snake gets food, we don't need to remove last
        if (!nextPoint.equals(food.getLocation())) {
            body.removeLast();
            return false;
        }
        return true;
    }

    public List<Point> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        if (direction == Direction.up || direction == Direction.down) {
            if (this.direction == Direction.up || this.direction == Direction.down) {
                return;
            }
        }
        if (direction == Direction.left || direction == Direction.right) {
            if (this.direction == Direction.left || this.direction == Direction.right) {
                return;
            }
        }
        this.direction = direction;
    }

    /**
     * @return The next head
     */
    private Point getNextPoint() {
        final Point currentPoint = body.getFirst();
        assert currentPoint != null;
        int deltaX = 0;
        int deltaY = 0;
        switch (direction) {
            case up:
                deltaY = -1;
                break;
            case down:
                deltaY = 1;
                break;
            case left:
                deltaX = -1;
                break;
            case right:
                deltaX = 1;
                break;
        }
        return new Point(currentPoint.x + deltaX, currentPoint.y + deltaY);
    }

    enum Direction {
        left, right, up, down
    }
}
