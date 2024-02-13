import java.awt.*;

public class Ball {
    private final int RADIUS;
    private int ballPositionX;
    private int ballPositionY;
    private Color ballColor;
    private final int MINX = 0;
    private final int MINY = 0;
    private final int MAXX;
    private final int MAXY;
    private int speedX;
    private int speedY;

    public Ball(int radius, int initX, int initY, Color ballColor, int maxX, int maxY, int speedX, int speedY) {
        this.RADIUS = radius;
        this.ballPositionX = RADIUS + initX;
        this.ballPositionY = RADIUS + initY;
        this.ballColor = ballColor;
        this.MAXX = maxX;
        this.MAXY = maxY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void paintBall(Graphics g) {
        g.setColor(ballColor);
        g.fillOval(ballPositionX - RADIUS, ballPositionY - RADIUS,2*RADIUS, 2*RADIUS);
    }

    public void moveOneStep() {
        ballPositionX += speedX;
        ballPositionY += speedY;
        if (ballPositionX - RADIUS < MINX) {
            ballPositionX = MINX + RADIUS;
            speedX = -speedX;
        } else if (ballPositionX + RADIUS > MAXX) {
            ballPositionX = MAXX - RADIUS;
            speedX = -speedX;
        }

        if (ballPositionY - RADIUS < MINY) {
            ballPositionY = MINY + RADIUS;
            speedY = -speedY;
        } else if (ballPositionY + RADIUS > MAXY) {
            ballPositionY = MAXY - RADIUS;
            speedY = -speedY;
        }
    }
}
