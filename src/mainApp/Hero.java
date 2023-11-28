package mainApp;

public class Hero extends Character {
	private int points;
	private int lives;
	private int counter;

	public Hero(int row, int col, GameComponent gameComponent) {
		super(row, col, 20, gameComponent);
		this.points = 0;
		this.lives = 5;
		this.counter = 0;
	}

	@Override
	public void collideWith(Character g) {

	}

	public void moveTo(int[] gridPosition) {
		this.setX(GRID_CONSTANT_X * gridPosition[0]);
		this.setX(GRID_CONSTANT_Y * gridPosition[1]);
		this.setxVelocity(0);
		this.setyVelocity(0);
	}

	public void addScore(int points) {
		this.points += points;
	}

	public void addLife() {
		this.lives++;
	}

	public void loseLife() {

		if (counter < 0) {
			this.lives--;
			this.counter = 250;
			int[] pos = { 0, 0 };
			this.moveTo(pos);
		}

	}

	public boolean isDead() {
		return this.lives <= 0;
	}

	public int getScore() {
		return this.points;
	}

	@Override
	public void update(double currentGravity) {
		counter--;
		super.update(currentGravity);
	}

	public int getLives() {
		return this.lives;
	}

}
