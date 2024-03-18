import java.awt.*;

public class Container {
    private final int HEIGHT;
    private final int WIDTH;
    private Color backgroundColor;

    public Container(int height, int width, Color backgroundColor) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.backgroundColor = backgroundColor;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void paintContainer(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void changeColor( Color color) {
        backgroundColor = color;
    }
}
