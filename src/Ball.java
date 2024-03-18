import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ball {
    private int radius;
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
        this.radius = radius;
        this.ballPositionX = this.radius + initX;
        this.ballPositionY = this.radius + initY;
        this.ballColor = ballColor;
        this.MAXX = maxX;
        this.MAXY = maxY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public Ball(int radius, int maxX, int maxY) {
        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();

        int randomX = rand.nextInt((maxX - radius) - (MINX + radius)) + (MINX + radius);
        int randomY = rand.nextInt((maxY - radius) - (MINY + radius)) + (MINY + radius);

        int randomSpeedX = rand.nextInt(15 - 1) + 1;
        int randomSpeedY = rand.nextInt(15 - 1) + 1;

        this.radius = radius;
        this.ballPositionX = this.radius + randomX;
        this.ballPositionY = this.radius + randomY;
        this.ballColor = new Color(red, green, blue);
        this.MAXX = maxX;
        this.MAXY = maxY;
        this.speedX = randomSpeedX;
        this.speedY = randomSpeedY;
    }

    public void changeColor() {
        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        this.ballColor = new Color(red, green, blue);
    }

    public void paintBall(Graphics g) {
        g.setColor(ballColor);
        g.fillOval(ballPositionX - radius, ballPositionY - radius, 2 * radius, 2 * radius);
    }

    public boolean moveOneStep(ArrayList<Obstacle> obs) {
        ballPositionX += speedX;
        ballPositionY += speedY;
        for (Obstacle ob : obs) {
            if (ob.inObstacle(this.radius, this.ballPositionX, this.ballPositionY)) {
                return false;
            }
        }
        if (ballPositionX - radius < MINX) {
            ballPositionX = MINX + radius;
            speedX = -speedX;
        } else if (ballPositionX + radius > MAXX) {
            ballPositionX = MAXX - radius;
            speedX = -speedX;
        }

        if (ballPositionY - radius < MINY) {
            ballPositionY = MINY + radius;
            speedY = -speedY;
        } else if (ballPositionY + radius > MAXY) {
            ballPositionY = MAXY - radius;
            speedY = -speedY;
        }
        return true;
    }

    public boolean isPointInsideBall(int x, int y) {
        // Calculate distance from the center of the ball to the clicked point
        double distance = Math.sqrt(Math.pow(x - ballPositionX, 2) + Math.pow(y - ballPositionY, 2));
        return distance <= radius;
    }

    public void grow() {
        radius += 5; // Increase the radius by 5 units
    }

    public int getSpeedX() {
        return this.speedX;
    }

    public int getSpeedY() {
        return this.speedY;
    }
}
