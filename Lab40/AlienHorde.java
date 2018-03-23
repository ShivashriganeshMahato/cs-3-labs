import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde
{
	private List<Alien> aliens;

	public AlienHorde(int size)
	{
		aliens = new ArrayList<>(size);
	}

	public void add(Alien al)
	{
		aliens.add(al);
	}

	public void drawEmAll( Graphics window )
	{
		aliens.forEach(alien -> alien.draw(window));
	}

	public void moveEmAll()
	{
	}

	public void removeDeadOnes(List<Ammo> shots)
	{
		for (Ammo shot : shots) {
			for (Alien alien : aliens) {
				if (shot.getX() < alien.getX() + alien.getWidth() &&
					shot.getX() + shot.getWidth() > alien.getX() &&
	     			shot.getY() > alien.getY() + alien.getHeight() &&
					shot.getY() + shot.getHeight() < alien.getY()) {
						shots.remove(shot);
						aliens.remove(alien);
					}
			}
		}
	}

	public String toString()
	{
		return aliens.toString();
	}
}
