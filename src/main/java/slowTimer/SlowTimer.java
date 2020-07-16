package slowTimer;

import engine.LGNode;

import java.awt.Graphics2D;

public class SlowTimer implements LGNode {
    private final int threshold;
    private final Runnable runnable;
    private int timer;

    public SlowTimer(final int threshold, final Runnable runnable) {
        this.threshold = threshold;
        this.timer = 0;
        this.runnable = runnable;
    }

    @Override
    public void render(Graphics2D g2d) {
        timer ++;
        if (timer > threshold) {
            timer = 0;
            runnable.run();
        }
    }
}
