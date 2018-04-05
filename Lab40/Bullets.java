import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Bullets
{
	private final int ShootingFrameLag = 20;

	private int timer;
	private List<Ammo> ammo;

	public Bullets()
	{
		timer = -1;
		ammo = new ArrayList<>();
	}

	public void add(Ammo al)
	{
		if (timer == -1) {
			ammo.add(al);
			timer = 0;
		}
	}

	public void add(Ammo al, Vector2 gotoPos) {
		al.goTo(gotoPos);
		add(al);
	}

	//post - draw each Ammo
	public void drawEmAll( Graphics window )
	{
		ammo.forEach(ammo -> ammo.draw(window));
	}

	public void moveEmAll()
	{
		ammo.forEach(Ammo::move);
	}

	public void updateTimer() {
		if (timer > -1)
			timer++;
		if (timer > ShootingFrameLag)
			timer = -1;
	}

	public void cleanEmUp()
	{
		List<Ammo> toRemove = new ArrayList<>();
		ammo.forEach(_ammo -> {
			if (_ammo.getY() < -_ammo.getHeight() || _ammo.isDead())
				toRemove.add(_ammo);
		});
		ammo.removeAll(toRemove);
	}

	public void update() {
		updateTimer();
		cleanEmUp();
	}

	public List<Ammo> getList()
	{
		return ammo;
	}

	public String toString()
	{
		return ammo.toString();
	}
}