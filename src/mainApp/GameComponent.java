package mainApp;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class GameComponent extends JComponent {
	Hero hero;
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private double currentGravity;
	private ArrayList<Egg> eggs;
	private NextLevelCaller callback;
	private boolean gameOver;
	private Image background;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		hero.drawOn((Graphics2D) g2);
		g2.setFont(new Font("Monospaced", Font.BOLD, 30));
		g2.drawString("Score: " + this.hero.getScore(), 10, 30);
		g2.drawString("Lives: " + this.hero.getLives(), 10, 60);

		for (Enemy enemy : enemies) {
			enemy.drawOn(g2);
		}

		for (Platform platform : platforms) {
			platform.drawOn(g2);
		}

		for (Egg e : eggs) {
			e.drawOn(g2);
		}
	}

	public void update() {
		this.handleCollisions();
		this.hero.update(this.currentGravity);
		this.updateNonPlayers();

		if (this.hero.isDead() && !gameOver) {
			this.callback.callBackGameOver();
			this.gameOver = true;
			this.hero.setMaxSpeed(1050);
			MainApp.newGame();
		}
		if (isLevelDone() && !gameOver) {
			this.callback.callbackLevelUp();
		}
	}

	public boolean isLevelDone() {
		return (this.eggs.size() == 0 && this.enemies.size() == 0);
	}

	public void drawLevel() {
		this.repaint();
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void loadNewLevel(Level level) {
		platforms.clear();
		enemies.clear();
		System.out.println(level.getPlatforms());
		System.out.println(level.getPlatforms().get(0).getX());

		platforms.addAll(level.getPlatforms());
		enemies.addAll(level.getEnemies());
		eggs.addAll(level.getEggs());

		hero.moveTo(level.getHeroPosition());
		this.currentGravity = level.getGravity();
	}

	public void handleCollisions() {

		for (Platform platform : this.platforms) {
			for (Enemy e : this.enemies) {
				if (platform.overlaps(e))
					platform.collideWith(e);
			}
			if (platform.overlaps(this.hero))
				platform.collideWith(this.hero);
			for (Egg e : eggs) {
				if (platform.overlaps(e))
					platform.collideWith(e);
			}
		}

		for (Egg egg : eggs) {
			if (egg.overlaps(this.hero)) {
				egg.collideWithHero(this.hero);
			}
		}

		for (Enemy enemy : this.enemies) {
			if (enemy.overlaps(this.hero)) {
				if (enemy.canJoust(this.hero)) {
					if (enemy.winsJoust(this.hero)) {
						this.hero.loseLife();
					} else
						enemy.loseLife();
				} else {
					enemy.collideWithEnemy(hero);
				}
			}

			ArrayList<Enemy> enemies2 = (ArrayList<Enemy>) this.enemies.clone();
			enemies2.remove(enemy);
			for (Enemy e : enemies2) {
				if (enemy.overlaps(e)) {
					enemy.collideWithEnemy(e);
				}
			}

		}
	}

	private void updateNonPlayers() {
		ArrayList<GameObject> shouldRemove = new ArrayList<>();

		for (Platform platform : this.platforms) {
			platform.update();
		}
		for (Enemy enemy : this.enemies) {
			enemy.update(currentGravity, this.hero);
			if (enemy.isRemove()) {
				Egg newEgg = new Egg(0, 0, this, 10);
				newEgg.addImage("images/egg-2.png");
				newEgg.setX(enemy.getX());
				newEgg.setY(enemy.getY());
				shouldRemove.add(enemy);
				this.eggs.add(newEgg);
			}
		}
		for (Egg e : eggs) {
			e.update(currentGravity);
			if (e.isTimeOut()) {
				Enemy newEnemy = new PassiveEnemy(0, 0, this);
				newEnemy.addImage("images/Enemy1_right.png");
				newEnemy.addImage("images/Enemy1_left.png");
				newEnemy.setX(e.getX());
				newEnemy.setY(e.getY() - 20);
				shouldRemove.add(e);
				this.enemies.add(newEnemy);
				shouldRemove.add(e);
			}
			if (e.isRemove())
				shouldRemove.add(e);
		}
		this.eggs.removeAll(shouldRemove);
		this.enemies.removeAll(shouldRemove);
	}

	public void addImage(String filename) {
		Image temp = null;
		try {
			temp = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("Image incorrect");
		}
		this.background = temp;
	}

	public GameComponent(NextLevelCaller caller) {
		super();
		this.platforms = new ArrayList<Platform>();
		this.enemies = new ArrayList<Enemy>();
		this.eggs = new ArrayList<Egg>();
		this.callback = caller;
		this.gameOver = false;
	}

}
