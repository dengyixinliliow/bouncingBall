import java.awt.*;

public class Container {
    private final int HEIGHT;
    private final int WIDTH;
    private final Color BACKGROUNDCOLOR;

    public Container(int height, int width, Color backgroundColor) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.BACKGROUNDCOLOR = backgroundColor;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void paintContainer(Graphics g) {
        g.setColor(BACKGROUNDCOLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
}
