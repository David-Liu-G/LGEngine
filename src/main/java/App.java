import engine.LGEngine;
import game.SnakeGame;

public class App {
    //Let's do a small test

    public static void main(String[] args) {
        LGEngine engine = new LGEngine(300, 300);

        engine.register(new SnakeGame());
// Our game is now finished
        engine.start(20);
    }
}
