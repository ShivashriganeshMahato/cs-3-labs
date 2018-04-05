import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
    private int originX;
    private int speed;
    private Image image;

    public Alien() {
        this(0, 0, 30, 30, 0);
    }

    public Alien(int x, int y) {
        this(x, y, 30, 30, 0);
    }

    public Alien(int x, int y, int s) {
        this(x, y, 30, 30, s);
    }

    public Alien(int x, int y, int w, int h, int s) {
        super(x, y, w, h);
        originX = x;
        speed = s;
        try {
            URL url = getClass().getResource("/images/alien.jpg");
            image = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public int getSpeed() {
        return speed;
    }

    public void move(String direction) {
        setX((int) (originX + 75 * Math.sin((int) (System.nanoTime() / Math.pow(10, 9)))));
        setY(getY() + speed / 4.0);
    }

    public void draw(Graphics window) {
        window.drawImage(image, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
    }

    public void kill(List<Ammo> shots) {
        List<Ammo> toAdd = new ArrayList<>();
        for (Ammo shot : shots) {
            if (isTouching(shot)) {
                super.kill();
                shot.kill();
                Random rand = new Random();
                for (int i = 0; i < 3; i++)
                    toAdd.add(new Ammo((int) getX(), (int) getY(), 1).goTo(rand.nextInt(800), rand.nextInt(600)));
            }
        }
        shots.addAll(toAdd);
    }

    public void damageShip(Ship ship) {
        if (isTouching(ship))
            ship.hurt(10);
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        originX = x;
    }

    public String toString() {
        return "";
    }
}
