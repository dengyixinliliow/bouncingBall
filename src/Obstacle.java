import java.awt.*;

public class Obstacle {
    private final int WIDTH;
    private final int HEIGHT;
    private final int X;
    private final int Y;
    private final Color COLOR = Color.black;

    public Obstacle(int x, int y, int width, int height) {
        this.X = x;
        this.Y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public boolean inObstacle(int radius, int ballX, int ballY) {
        int xMin = X;
        int xMax = X + WIDTH;
        int yMin = Y;
        int yMax = Y + HEIGHT;

        int ballMinX = ballX - radius;
        int ballMaxX = ballX + radius;
        int ballMinY = ballY - radius;
        int ballMaxY = ballY + radius;

        if (ballMinX >= xMin && ballMinX <= xMax && (ballMinY >= yMin && ballMinY <= yMax || ballMaxY >= yMin && ballMaxY <= yMax)) {
            return true;
        }
        if (ballMaxX >= xMin && ballMaxX <= xMax && (ballMinY >= yMin && ballMinY <= yMax || ballMaxY >= yMin && ballMaxY <= yMax)) {
            return true;
        }

        return false;
    }

    public void paintObstacle(Graphics g) {
        g.setColor(this.COLOR);
        g.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
    }

}
