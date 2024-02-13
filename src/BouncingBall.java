import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class BouncingBall extends JPanel implements KeyListener {
    private static final int BOX_WIDTH = 640;
    private static final int BOX_HEIGHT = 480;
    private static final int UPDATE_RATE = 30; // Number of refresh per second

    private final Container container;
    private List<Ball> balls;

    public BouncingBall() {
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        container = new Container(BOX_HEIGHT, BOX_WIDTH, Color.BLACK);
        balls = new ArrayList<>();
        Ball ball1 = new Ball(80, 0, 0, Color.PINK, container.getWIDTH(), container.getHEIGHT(), 1, 2);
        Ball ball2 = new Ball(60, 10, 10, Color.BLUE, container.getWIDTH(), container.getHEIGHT(), 2, 2);
        Ball ball3 = new Ball(80, 0, 0, Color.WHITE, container.getWIDTH(), container.getHEIGHT(), 3, 2);
        Ball ball4 = new Ball(60, 10, 10, Color.ORANGE, container.getWIDTH(), container.getHEIGHT(), 2, 1);
        balls.add(ball1);
        balls.add(ball2);
        balls.add(ball3);
        balls.add(ball4);

        setFocusable(true);
        addKeyListener(this);

        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    for (Ball ball : balls) {
                        ball.moveOneStep();
                    }
                    repaint();
                    try {
                        Thread.sleep(1000 / UPDATE_RATE);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        container.paintContainer(g);
        for (Ball ball : balls) {
            ball.paintBall(g);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Random rand = new Random();
            // Java 'Color' class takes 3 floats, from 0 to 1.
            float red = rand.nextFloat();
            float green = rand.nextFloat();
            float blue = rand.nextFloat();
            Color randomColor = new Color(red, green, blue);
            Ball newBall = new Ball(40, 5, 5, randomColor, container.getWIDTH(), container.getHEIGHT(), 2, 2);
            balls.add(newBall);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}