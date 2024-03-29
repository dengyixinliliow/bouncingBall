import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class BouncingBall extends JPanel implements KeyListener, MouseListener {
    private static final int BOX_WIDTH = 840;
    private static final int BOX_HEIGHT = 680;
    private static final int UPDATE_RATE = 30; // Number of refresh per second

    private final Container CONTAINER;
    private List<Ball> balls;
    private ArrayList<Obstacle> obstacles;
    private double averageCpuUsage = 0.0;
    private Color backgroundColor = Color.ORANGE;

    public BouncingBall() {
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        CONTAINER = new Container(BOX_HEIGHT, BOX_WIDTH, backgroundColor);
        balls = new ArrayList<>();
        Ball ball1 = new Ball(30, 0, 0, Color.PINK, CONTAINER.getWIDTH(), CONTAINER.getHEIGHT(), 1, 2);
        Ball ball2 = new Ball(30, 200, 0, Color.BLUE, CONTAINER.getWIDTH(), CONTAINER.getHEIGHT(), 2, 2);
        balls.add(ball1);
        balls.add(ball2);

        obstacles = new ArrayList<>();
        Obstacle o1 = new Obstacle(400, 100, 50, 90);
        Obstacle o2 = new Obstacle(200, 150, 50, 90);
        obstacles.add(o1);
        obstacles.add(o2);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    Iterator<Ball> iterator = balls.iterator();
                    while (iterator.hasNext()) {
                        Ball ball = iterator.next();
                        if (!ball.moveOneStep(obstacles)) {
                            iterator.remove(); // Use iterator to remove the ball safely
                        }
                    }

                    List<Ball> ballsToRemove = new ArrayList<>(); // List to store balls to remove
                    List<Ball> ballsToAdd = new ArrayList<>();
                    for (int i = 0; i < balls.size(); i++) {
                        Ball ball1 = balls.get(i);
                        for (int j = i + 1; j < balls.size(); j++) {
                            Ball ball2 = balls.get(j);
                            if (ball1 != ball2 && ball1.collideWithOtherBall(ball2)) {
                                ballsToRemove.add(ball1);
                                ballsToRemove.add(ball2);
                                int newX = (ball1.getBallPositionX() + ball2.getBallPositionX()) / 2;
                                int newY = (ball1.getBallPositionY() + ball2.getBallPositionY()) / 2;
                                Ball newBall = new Ball(30, newX, newY, mixColors(ball1.getColor(), ball2.getColor()), CONTAINER.getWIDTH(), CONTAINER.getHEIGHT());
                                ballsToAdd.add(newBall);
                            }
                        }
                    }

                    balls.removeAll(ballsToRemove); // Remove collided balls from the main list
                    balls.addAll(ballsToAdd);
                    repaint();
                    try {
                        Thread.sleep(1000 / UPDATE_RATE);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        Thread processThread = new Thread() {
            public void run() {
                while(true) {
                    try {
                        // Execute the ps command
                        Process process = new ProcessBuilder("ps", "-A", "-o", "%cpu").start();

                        // Read the output of the command
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        double totalCpuUsage = 0;
                        int count = 0;

                        reader.readLine();

                        // Read each line of the output and calculate total CPU usage
                        while ((line = reader.readLine()) != null) {
                            // Each line contains the CPU usage of a process, which can be a fraction
                            // Convert it to a percentage by multiplying by 100
                            double cpuUsage = Double.parseDouble(line.trim()) * 100;
                            totalCpuUsage += cpuUsage;
                            count++;
                        }

                        if ((totalCpuUsage / count) > averageCpuUsage) {
                            backgroundColor = backgroundColor.brighter();
                            CONTAINER.changeColor(backgroundColor);
                        } else {
                            backgroundColor = backgroundColor.darker();
                            CONTAINER.changeColor(backgroundColor);
                        }
                        averageCpuUsage = totalCpuUsage / count;
                        System.out.println("Average CPU Usage: " + averageCpuUsage + "%");
                        repaint();
                        // Wait for the process to exit
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        gameThread.start();
        processThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        CONTAINER.paintContainer(g);
        for (Ball ball : balls) {
            ball.paintBall(g);
        }
        for (Obstacle o : obstacles) {
            o.paintObstacle(g);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        String cpuUsageInfo = String.valueOf(averageCpuUsage);
        g.drawString("Current average CPU usage: " + cpuUsageInfo, 30, 50);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Ball newBall = new Ball(30, CONTAINER.getWIDTH(), CONTAINER.getHEIGHT());
            balls.add(newBall);
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
                ball.changeColor();
                repaint();
                return; // Exit the method after growing the first clicked ball
            }
        }

        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle ob = iterator.next();
            if (ob.isInside(mouseX, mouseY)) {
                iterator.remove();
                repaint();
                return;
            }
        }

        obstacles.add(new Obstacle(mouseX, mouseY, 50, 90));
        repaint();
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

    public static Color mixColors(Color color1, Color color2) {
        int red = (color1.getRed() + color2.getRed()) / 2;
        int green = (color1.getGreen() + color2.getGreen()) / 2;
        int blue = (color1.getBlue() + color2.getBlue()) / 2;

        return new Color(red, green, blue);
    }
}