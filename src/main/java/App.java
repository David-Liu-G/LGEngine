import engine.LGEngine;
import game.Ball;
import game.Box;

public class App {
    //Let's do a small test

    public static void main(String[] args) {
        LGEngine engine = new LGEngine(500, 500);

        // Adding the box that moves right
        engine.register(new Box());
        // Let's now add a ball that bounces
        engine.register(new Ball());

        // That's it
        // We will make our game next time

        engine.start(20);
    }
}
