import java.awt.*;

public class Obstacle {
    private final int width;
    private final int height;

    private final int x;
    private final int y;

    private final Color color = Color.red;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean inObstacle(int radius, int ballX, int ballY) {
        int xMin = x - width/2;
        int xMax = x + width/2;
        int yMin = y - height/2;
        int yMax = y + height/2;

        int ballMinX = ballX - radius;
        int ballMaxX = ballX + radius;
        int ballMinY = ballY - radius;
        int ballMaxY = ballY + radius;

        if (ballMinX >= xMin && ballMinX <= xMax) {
            return true;
        }
        if (ballMaxX >= xMin && ballMinX >= xMax) {
            return true;
        }
        if (ballMinY >= yMin && ballMinY <= yMax) {
            return true;
        }
        if (ballMaxY >= yMin && ballMinY >= yMax) {
            return true;
        }

        return false;
    }

    public void paintObstacle(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

}
