package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
	private static final int TOTAL_LINES = 8;
	private double gravity;
	private String levelName;
	ArrayList<Platform> platforms;
	ArrayList<Enemy> enemies;
	ArrayList<Egg> eggs;
	private int[] heroPosition = new int[2];

	public double getGravity() {
		return this.gravity;
	}

	public void setGravity(Double gravity) {
		this.gravity = gravity;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public ArrayList<Platform> getPlatforms() {
		return this.platforms;
	}

	public void addPlatforms(Platform platform) {
		this.platforms.add(platform);
	}

	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	public ArrayList<Egg> getEggs() {
		return this.eggs;
	}

	public Level() {
		this.platforms = new ArrayList<Platform>();
		this.enemies = new ArrayList<Enemy>();
		this.eggs = new ArrayList<Egg>();
		System.out.println("making a level");
	}

	public void setOtherParam(ArrayList<Double> otherParams) {
		this.setGravity(otherParams.get(0));
	}

	public void readFile(String fileName, GameComponent gameComponent) throws IncorrectNumberOfLinesException {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("level filetype is incorrect");
			System.exit(1);
		}

		this.setLevelName(scanner.nextLine());
		ArrayList<Double> otherParams = new ArrayList<Double>();
		while (scanner.hasNextDouble())
			otherParams.add(scanner.nextDouble());
		this.setOtherParam(otherParams);

		int lineNumber = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			this.importStuff(line, lineNumber, gameComponent);
			lineNumber++;
		}

		if (lineNumber != TOTAL_LINES) {
			throw new IncorrectNumberOfLinesException(TOTAL_LINES, lineNumber);
		}

		scanner.close();
	}

	public void importStuff(String line, int lineNumber, GameComponent gameComponent) {
		char addition;
		for (int i = 0; i < line.length(); i++) {
			addition = line.charAt(i);
			if (addition == 'H') {
				this.heroPosition[0] = i;
				this.heroPosition[1] = lineNumber;
			}
			if (addition == 'p') {
				Platform p = new Platform(gameComponent, lineNumber, i);
				p.addImage("images/platform.png");
				this.platforms.add(p);
			}
			if (addition == 'e') {
				PassiveEnemy e = new PassiveEnemy(lineNumber, i, gameComponent);
				e.addImage("images/Enemy1_right.png");
				e.addImage("images/Enemy1_left.png");

				this.enemies.add(e);
			}
			if (addition == 'a') {
				ActiveEnemy a = new ActiveEnemy(lineNumber, i, gameComponent);
				a.addImage("images/Enemy2_right_1.png");
				a.addImage("images/Enemy2_left_1.png");
				this.enemies.add(a);
			}
			if (addition == 'g') {
				Egg a = new Egg(lineNumber, i, gameComponent, 10);
				a.addImage("images/egg-1.png");
				this.enemies.add(a);
			}
			if (addition == 'f') {
				Platform p = new Platform(gameComponent, lineNumber, i);
				p.addImage("images/floor.png");
				this.platforms.add(p);
			}
			if (addition == 'l') {
				Lava l = new Lava(gameComponent, lineNumber, i);
				l.addImage("images/lava.png");
				this.platforms.add(l);
			}
			if (addition == 'v') {
				Platform p = new Platform(gameComponent, lineNumber, i);
				p.addImage("images/victory-block.png");
				this.platforms.add(p);
			}
			if (addition == 'o') {
				Platform p = new Platform(gameComponent, lineNumber, i);
				p.addImage("images/loss-block.png");
				this.platforms.add(p);
			}
		}
	}

	public int[] getHeroPosition() {
		return this.heroPosition;
	}

}
