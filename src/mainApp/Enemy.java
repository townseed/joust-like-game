package mainApp;

public abstract class Enemy extends Character {
	private int score;
	private boolean remove;

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Enemy(int row, int col, GameComponent gameComponent) {
		super(row, col, 20, gameComponent);
		this.setRemove(false);
	}

	public boolean canJoust(Character h) {
		if (this.getFacingDirection() == h.getFacingDirection())
			return false;
		if (this.getFacingDirection() == RIGHT && this.getX() > h.getX())
			return false;
		if (this.getFacingDirection() == LEFT && this.getX() < h.getX())
			return false;
		return true;
	}

	public void update(double currentGravity, Character c) {
		this.update(currentGravity);
	}

}
