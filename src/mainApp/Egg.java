package mainApp;

public class Egg extends Enemy {
	private int timeLeft;
	private static int EGG_TIME = 350;
	private static int INVICIBILITY_TIME = 300;

	private int points;

	public Egg(int row, int col, GameComponent g, int points) {
		super(row, col, g);
		this.timeLeft = EGG_TIME;
		this.points = points;
	}

	public boolean isTimeOut() {
		return timeLeft <= 0;
	}

	public void update(double currentGravity) {
		super.update(currentGravity);
		if (!isTimeOut())
			timeLeft--;
	}

	public void collideWithHero(Hero h) {
		if (this.timeLeft < INVICIBILITY_TIME) {
			this.setRemove(true);
			h.addScore(this.points);
		}
	}

	@Override
	public void collideWith(Character g) {

	}

	@Override
	public boolean isRemove() {

		return super.isRemove();
	}

	@Override
	public void loseLife() {
		this.setRemove(true);
	}
}
