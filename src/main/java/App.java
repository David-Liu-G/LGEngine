import engine.LGEngine;
import game.Board;

public class App {
    //Let's do a small test

    public static void main(String[] args) {
        LGEngine engine = new LGEngine(300, 300);

        engine.register(new Board());

        engine.start(20);
    }
}
