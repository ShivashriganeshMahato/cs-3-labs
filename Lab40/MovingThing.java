import java.awt.Graphics;

public abstract class MovingThing implements Moveable
{
	private double xPos;
	private double yPos;
	private int width;
	private int height;
	private boolean isDead;

	public MovingThing()
	{
		this(10, 10);
	}

	public MovingThing(int x, int y)
	{
		this(x, y, 10, 10);
	}

	public MovingThing(int x, int y, int w, int h)
	{
		isDead = false;
		setPos(x, y);
		setWidth(w);
		setHeight(h);
	}

	public void setPos( int x, int y)
	{
		setX(x);
		setY(y);
	}

	public void setX(double x)
	{
		xPos = x;
	}

	public void setY(double y)
	{
		yPos = y;
	}

	public double getX()
	{
		return xPos;
	}

	public double getY()
	{
		return yPos;
	}

	public void setWidth(int w)
	{
		 width = w;
	}

	public void setHeight(int h)
	{
		height = h;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public abstract void move(String direction);
	public abstract void draw(Graphics window);

	public boolean isTouching(MovingThing otherThing) {
		return otherThing.getY() + otherThing.getHeight() > getY() &&
				otherThing.getY() < getY() + getHeight() &&
				otherThing.getX() + otherThing.getWidth() > getX() &&
				otherThing.getX() < getX() + getWidth();
	}

	public boolean isDead() {
		return isDead;
	}

	public void kill() {
		isDead = true;
	}

	public String toString()
	{
		return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
	}
}