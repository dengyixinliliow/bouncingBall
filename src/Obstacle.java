import java.awt.*;

public class Obstacle {
    private final int WIDTH;
    private final int HEIGHT;
    private final int X;
    private final int Y;
    private final Color COLOR = Color.GRAY;

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

    public boolean isInside(int x, int y) {
        return x >= X && x <= X + WIDTH && y >= Y && y <= Y + HEIGHT;
    }

    public void paintObstacle(Graphics g) {
        // Define colors for the rectangle and its shadow
        Color rectangleColor = COLOR.darker();
        Color shadowColor = new Color(0, 0, 0, 80); // Semi-transparent black for the shadow

        // Draw the shadow first
        g.setColor(shadowColor);
        g.fillRect(X + 4, Y + 4, WIDTH, HEIGHT); // Adjust the shadow position

        // Then draw the actual rectangle
        g.setColor(rectangleColor);
        g.fillRect(X, Y, WIDTH, HEIGHT);
    }

}
