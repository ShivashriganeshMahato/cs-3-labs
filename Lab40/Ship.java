import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {
    private int speed;
    private Image image;
    private int health;
    private int hitTimer;
    private int score;

    public Ship() {
        this(10, 10, 10, 10, 10);
    }

    public Ship(int x, int y) {
        this(x, y, 10, 10, 10);
    }

    public Ship(int x, int y, int s) {
        this(x, y, 10, 10, s);
    }

    public Ship(int x, int y, int w, int h, int s) {
        super(x, y, w, h);
        health = 100;
        hitTimer = -1;
        score = 0;
        setSpeed(s);
        try {
            URL url = getClass().getResource("/images/ship.png");
            image = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public void update() {
        if (hitTimer > -1)
            hitTimer++;
        if (hitTimer >= 100)
            hitTimer = -1;
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void hurt(int damage) {
        if (hitTimer == -1) {
            health -= damage;
            hitTimer = 0;
        }
        if (health <= 0) {
            System.out.println("GAME OVER!");
            System.out.println("Your score was " + getScore());
            System.exit(0);
        }
    }

    public void heal(int health) {
        this.health += health;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int scoreToAdd) {
        this.score += scoreToAdd;
    }

    public void move(String direction) {
        switch (direction) {
            case "LEFT":
                if (getX() > 0)
                    setX(getX() - speed);
                break;
            case "RIGHT":
                if (getX() < 800 - getWidth())
                    setX(getX() + speed);
                break;
        }
    }

    public void draw(Graphics window) {
        window.drawImage(image, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
    }

    public String toString() {
        return super.toString() + getSpeed();
    }
}
