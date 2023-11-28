package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class: MainApp
 * 
 * @author Put your team name here <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp {
	public final static int SCREEN_SIZE_X = 1100;
	public final static int SCREEN_SIZE_Y = 800;
	public static final int DELAY = 10;

	public final static int MAX_X_SPEED = 6;
	public final static int MAX_X_SPEED_ACTIVE_E = 2;
	public final static int MAX_Y_SPEED = 10;
	public final static int MAX_Y_SPEED_ACTIVE_E = 6;
	public final static int X_ACCELERATION = 3;
	public final static int Y_ACCELERATION = 2;

	private ArrayList<Level> levels = new ArrayList<Level>();
	private Level currentLevel;
	private static JFrame frame = new JFrame();
	private GameComponent gameComponent;

	class KeyboardListen implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				gameComponent.hero.setGoingLeft(true);
				gameComponent.hero.setImage(1);
			}
			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				gameComponent.hero.setGoingRight(true);
				gameComponent.hero.setImage(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				gameComponent.hero.setGoingUp(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				gameComponent.hero.setGoingDown(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_U) {
				loadNextLevel();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				gameComponent.hero.setGoingLeft(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				gameComponent.hero.setGoingRight(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				gameComponent.hero.setGoingUp(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				gameComponent.hero.setGoingDown(false);
			}
		}

	}

	private void runApp() {
		final int frameWidth = SCREEN_SIZE_X;
		final int frameHeihgt = SCREEN_SIZE_Y;
		System.out.println("Write your cool arcade game here!");

		frame.setTitle("Arcade Game");
		frame.setSize(frameWidth, frameHeihgt);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(false);

		NextLevelCaller caller = new NextLevelCaller() {

			@Override
			public void callbackLevelUp() {
				loadNextLevel();
			}

			@Override
			public void callBackGameOver() {
				Level gameOver = readFile("levels/gameOver.txt");
				gameComponent.loadNewLevel(gameOver);
			}
		};

		gameComponent = new GameComponent(caller);
		gameComponent.addImage("images/background.png");
		Hero hero = new Hero(0, 0, gameComponent);
		hero.addImage("images/Hero-right.png");
		hero.addImage("images/Hero-left.png");
		gameComponent.setHero(hero);
		Level level = readFile("levels/levelTest.txt");
		this.currentLevel = level;
		this.gameComponent.loadNewLevel(currentLevel);
		levels.add(level);

		Level level_1 = this.readFile("levels/level2.txt");
		Level level_2 = this.readFile("levels/level3.txt");
		Level level_3 = this.readFile("levels/level4.txt");
		Level level_4 = this.readFile("levels/level5.txt");
		Level victory = this.readFile("levels/victory.txt");
		levels.add(level_1);
		levels.add(level_2);
		levels.add(level_3);
		levels.add(level_4);
		levels.add(victory);

		frame.add(gameComponent);
		KeyboardListen keyboard = new KeyboardListen();
		frame.addKeyListener(keyboard);

		GameAdvanceListener advanceListener = new GameAdvanceListener(gameComponent);

		Timer timer = new Timer(DELAY, advanceListener);
		timer.start();

		frame.setVisible(true);

	} // runApp

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

	private Level readFile(String filename) {
		/**
		 * read the text file. The expected format is filename on the first line, other
		 * stuff on the second line, and the rest of the level on the rest of the
		 * following lines
		 * 
		 * It should look something like this:
		 * LevelName
		 * gravity: 5, some other stuff here, the scanner is only looking for integers
		 * - - - - - - -
		 * H - - - - - -
		 * p p p - - - -
		 * - - - - - - -
		 * l l l l l l l
		 * 
		 * H being the Hero, p being platforms, and l being lava
		 * 
		 */

		Level level = new Level();

		try {
			level.readFile(filename, gameComponent);
		} catch (IncorrectNumberOfLinesException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return level;
	}

	public void loadNextLevel() {
		int current = this.levels.indexOf(this.currentLevel);
		if (current + 1 == levels.size())
			return;
		this.currentLevel = this.levels.get(current + 1);
		this.gameComponent.loadNewLevel(currentLevel);
	}

	public static void newGame() {
		// TODO Auto-generated method stub
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		JPanel panel = new JPanel();
		panel.add(newGame);
//		frame.add(panel, BorderLayout.CENTER);	
//		frame.setVisible(true);
	}

	public void restart() {
		this.currentLevel = this.levels.get(0);
	}

}