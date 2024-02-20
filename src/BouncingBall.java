import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class BouncingBall extends JPanel implements KeyListener, MouseListener {
    private static final int BOX_WIDTH = 640;
    private static final int BOX_HEIGHT = 480;
    private static final int UPDATE_RATE = 30; // Number of refresh per second

    private final Container container;
    private List<Ball> balls;

    public BouncingBall() {
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        container = new Container(BOX_HEIGHT, BOX_WIDTH, Color.BLACK);
        balls = new ArrayList<>();
        Ball ball1 = new Ball(30, 0, 0, Color.PINK, container.getWIDTH(), container.getHEIGHT(), 1, 2);
        Ball ball2 = new Ball(30, 10, 10, Color.BLUE, container.getWIDTH(), container.getHEIGHT(), 2, 2);
        balls.add(ball1);
        balls.add(ball2);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

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
            Ball newBall = new Ball(30, container.getWIDTH(), container.getHEIGHT());
            balls.add(newBall);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!balls.isEmpty()) {
                Random random = new Random();
                // Generate a random index within the range of the list size
                int randomIndex = random.nextInt(balls.size());
                balls.remove(randomIndex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        for (Ball ball : balls) {
            if (ball.isPointInsideBall(mouseX, mouseY)) {
                ball.grow();
                repaint();
                return; // Exit the method after growing the first clicked ball
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}