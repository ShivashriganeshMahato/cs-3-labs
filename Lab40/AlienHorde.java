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
		this();
		for (int i = 0; i < size; i++) {
			Alien newAlien = new Alien();
			int x = i % 7 * 100 + 75;
			int y = i / 7 * newAlien.getHeight() * 10 - 700;
			newAlien.setPos(x - newAlien.getWidth() / 2, y);
			add(newAlien);
		}
	}

	public AlienHorde() {
		aliens = new ArrayList<>();
	}

	public void spawn(int speed) {
		for (int i = 0; i < 7; i++) {
			Alien newAlien = new Alien();
			newAlien.setPos(i * 100 + 100 - newAlien.getWidth() / 2, -newAlien.getHeight() - 100);
			newAlien.setSpeed(speed);
			add(newAlien);
		}
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
		aliens.forEach(alien -> alien.move("DOWN"));
	}

	public void update(List<Ammo> shots, Ship ship) {
		for (Alien alien : aliens)
			alien.damageShip(ship);
		removeDeadOnes(shots, ship);
	}

	public void removeDeadOnes(List<Ammo> shots, Ship ship)
	{
		List<Alien> toRemove = new ArrayList<>();
		for (Alien alien : aliens) {
			alien.kill(shots);
			if (alien.isDead()) {
				toRemove.add(alien);
				ship.addScore(1);
			}
			else if (alien.getY() > 650) {
				toRemove.add(alien);
				ship.hurt(5);
			}
		}
		aliens.removeAll(toRemove);
	}

	public String toString()
	{
		return aliens.toString();
	}

	public List<Alien> getAliens() {
		return aliens;
	}
}