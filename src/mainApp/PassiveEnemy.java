package mainApp;

import java.util.Random;

public class PassiveEnemy extends Enemy {
	private int counter;
	private Random rand;
	private static int X_ACCEL = 3;
	private static int Y_ACCEL = 10;

	public PassiveEnemy(int row, int col, GameComponent g) {
		super(row, col, g);
		this.counter = 0;
		this.rand = new Random();
	}

	@Override
	public void collideWith(Character g) {
	}

	@Override
	public void update(double currentGravity) {
		if (this.counter > 5) {
			this.counter = 0;
			if (this.rand.nextInt(4) == 0) {
				this.addVelocityX(-X_ACCEL);
				this.setImage(LEFT);
			}
			if (this.rand.nextInt(4) == 1) {
				this.addVelocityX(X_ACCEL);
				this.setImage(RIGHT);
			}
			if (this.rand.nextInt(4) == 2) {
				this.addVelocityY(-Y_ACCEL);
			}
		}
		this.counter++;
		super.update(currentGravity);
	}

	@Override
	public void loseLife() {
		this.setRemove(true);
	}
}
