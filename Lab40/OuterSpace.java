import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Character.*;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class OuterSpace extends Canvas implements KeyListener, MouseMotionListener, MouseListener, Runnable {
    private long startTime;
    private long spawnTime;
    private Ship ship;
    private AlienHorde horde;
    private Bullets bullets;
    private Vector2 mouse;

	/* uncomment once you are ready for this part
     *
   private AlienHorde horde;
	private Bullets shots;
	*/

    private boolean[] keys;
    private BufferedImage back;
    private Image healthBar;
    private Image healthBarBack;

    public OuterSpace() {
        startTime = System.nanoTime();
        spawnTime = -1;

        setBackground(Color.black);

        keys = new boolean[5];
        mouse = new Vector2();

        //instantiate other instance variables
        //Ship, Alien
        ship = new Ship(400, 450, 50, 50, 10);
        ship.setSpeed(2);

        horde = new AlienHorde();
        bullets = new Bullets();

        try {
            URL url = getClass().getResource("/images/health.png");
            healthBar = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        try {
            URL url = getClass().getResource("/images/healthEmpty.png");
            healthBarBack = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("File Not Found");
            System.exit(0);
        }

        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        long runTime = (System.nanoTime() - startTime) / (int) Math.pow(10, 9); // In seconds
        if (runTime % 5 == 0 && runTime != spawnTime) {
            int speed = ((int) runTime / 5) / 5 + 1; // Increase speed every 5 waves
            horde.spawn(speed);
            spawnTime = runTime;
        }
        ship.update();
        bullets.update();
        horde.update(bullets.getList(), ship);
        paint(window);
    }

    public void paint(Graphics window) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D twoDGraph = (Graphics2D) window;

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if (back == null)
            back = (BufferedImage) (createImage(getWidth(), getHeight()));

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        graphToBack.setColor(Color.BLUE);
        graphToBack.drawString("StarFighter ", 25, 50);
        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(0, 0, 800, 600);

        //add code to move Ship, Alien, etc.
        bullets.moveEmAll();
        horde.moveEmAll();

        if (keys[0]) {
            ship.move("LEFT");
        }
        if (keys[1]) {
            ship.move("RIGHT");
        }
        if (keys[2]) {
            ship.move("UP");
        }
        if (keys[3]) {
            ship.move("DOWN");
        }

        twoDGraph.drawImage(back, null, 0, 0);
        horde.drawEmAll(window);
        bullets.drawEmAll(window);
        ship.draw(window);
        twoDGraph.drawImage(healthBarBack, 20, 20, 100, 20,null);
        twoDGraph.drawImage(healthBar, 20, 20, ship.getHealth(), 20,null);
        twoDGraph.setColor(Color.WHITE);
        twoDGraph.drawString("Score: " + ship.getScore(), 20, 75);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = true;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = false;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
        //no code needed here
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse.set(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        bullets.add(new Ammo((int) ship.getX(), (int) ship.getY(), 1), new Vector2(mouse.getX(), mouse.getY()));
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
}