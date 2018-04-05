import java.awt.Color;
import java.awt.Graphics;

public class Ammo extends MovingThing
{
	private int speed;
	private Vector2 distance;

	public Ammo()
	{
		this(0,0,0);
	}

	public Ammo(int x, int y)
	{
		this(x, y, 0);
	}

	public Ammo(int x, int y, int s)
	{
		super(x, y);
		speed = s;
		distance = null;
	}

	public void setSpeed(int s)
	{
		speed = s;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void draw( Graphics window )
	{
		window.setColor(Color.RED);
		window.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public Ammo goTo(int x, int y) {
		return goTo(new Vector2(x, y));
	}

	public Ammo goTo(Vector2 position) {
		distance = new Vector2(position.getX() - getX(), position.getY() - getY());
		return this;
	}

	public void move() {
		if (distance == null) {
			move("UP");
		} else {
			setPos((int) (getX() + (speed * distance.getX()) / 100.0), (int) (getY() + (speed * distance.getY()) / 100.0));
		}
	}

	public void move( String direction )
	{
		setY(getY() - speed);
	}

	public String toString()
	{
		return super.toString() + getSpeed();
	}
}