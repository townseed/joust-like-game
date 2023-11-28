package mainApp;

public class ActiveEnemy extends Enemy {
	public ActiveEnemy(int row, int col, GameComponent g) {
		super(row, col, g);
		this.setMaxSpeed(MainApp.MAX_X_SPEED_ACTIVE_E);
		this.setMaximumVerticalSpeed(MainApp.MAX_Y_SPEED_ACTIVE_E);
	}

	@Override
	public void collideWith(Character g) {
	}

	@Override
	public void update(double currentGravity, Character c) {
		if (this.getX() > c.getX()) {
			this.setGoingLeft(true);
			this.setGoingRight(false);
			this.setImage(LEFT);
		}
		if (this.getX() < c.getX()) {
			this.setGoingLeft(false);
			this.setGoingRight(true);
			this.setImage(RIGHT);
		}
		super.update(currentGravity);
	}

	@Override
	public void loseLife() {
		this.setRemove(true);
	}
}
