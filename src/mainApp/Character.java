package mainApp;

public abstract class Character extends GameObject {
	private int joustHeight, facingDirection, maximumVerticalSpeed, maxSpeed, hAcceleration, vAcceleration;
	private boolean isGoingLeft, isGoingRight, isGoingUp, isGoingDown;

	public double getJoustHeight() {
		return joustHeight + this.getY();
	}

	public void setJoustHeight(int joustHeight) {
		this.joustHeight = joustHeight;
	}

	public int getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(int facingDirection) {
		this.facingDirection = facingDirection;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Character(int row, int col, int joustHeight, GameComponent gameComponent) {
		super(gameComponent, row, col);

		this.facingDirection = LEFT;
		this.maximumVerticalSpeed = MainApp.MAX_Y_SPEED;
		this.maxSpeed = MainApp.MAX_X_SPEED;
		this.joustHeight = joustHeight;
		this.isGoingLeft = false;
		this.isGoingRight = false;
		this.isGoingUp = false;
		this.hAcceleration = MainApp.X_ACCELERATION;
		this.vAcceleration = MainApp.Y_ACCELERATION;
	}

	public boolean isGoingLeft() {
		return isGoingLeft;
	}

	public void setGoingLeft(boolean isGoingLeft) {
		this.isGoingLeft = isGoingLeft;
		this.setFacingDirection(LEFT);
	}

	public boolean isGoingRight() {
		return isGoingRight;
	}

	public void setGoingRight(boolean isGoingRight) {
		this.isGoingRight = isGoingRight;
		this.setFacingDirection(RIGHT);
	}

	public boolean isGoingUp() {
		return isGoingUp;
	}

	public void setGoingUp(boolean isGoingUp) {
		this.isGoingUp = isGoingUp;
	}

	public boolean isGoingDown() {
		return isGoingDown;
	}

	public void setGoingDown(boolean isGoingDown) {
		this.isGoingDown = isGoingDown;
	}

	public void update(double currentGravity) {

		if (isGoingLeft())
			this.addVelocityX(-this.hAcceleration);

		if (isGoingRight())
			this.addVelocityX(this.hAcceleration);

		if (isGoingUp())
			this.addVelocityY(-this.vAcceleration);

		if (isGoingDown())
			this.addVelocityY(this.vAcceleration);

		super.update();

		if (this.getY() <= 0) {
			this.setY(0);
			this.setyVelocity(0);
		}
		if (this.getY() <= MainApp.SCREEN_SIZE_Y - this.getHeight()) {
			this.addVelocityY(currentGravity);
		}

		if (this.getX() > MainApp.SCREEN_SIZE_X)
			this.setX(0 - this.getWidth());
		if (this.getX() < 0 - this.getWidth())
			this.setX(MainApp.SCREEN_SIZE_X);

		if (this.getxVelocity() > this.getMaxSpeed())
			this.setxVelocity(this.getMaxSpeed());
		if (this.getxVelocity() < -this.getMaxSpeed())
			this.setxVelocity(-this.getMaxSpeed());
		if (this.getyVelocity() > this.getMaximumVerticalSpeed())
			this.setyVelocity(this.getMaximumVerticalSpeed());
		if (this.getyVelocity() < -this.getMaximumVerticalSpeed())
			this.setyVelocity(-this.getMaximumVerticalSpeed());
	}

	public int getMaximumVerticalSpeed() {
		return maximumVerticalSpeed;
	}

	public void setMaximumVerticalSpeed(int maximumVerticalSpeed) {
		this.maximumVerticalSpeed = maximumVerticalSpeed;
	}

	public boolean winsJoust(Character c) {
		return this.getJoustHeight() < c.getJoustHeight();
	}

	public void collideWithEnemy(Character enemy) {
		this.setxVelocity(-this.getxVelocity());
		this.setyVelocity(-this.getyVelocity());
		update();
	}

	public abstract void loseLife();
}
