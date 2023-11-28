package mainApp;

public class Platform extends GameObject {
	private int friction;

	public int getFriction() {
		return friction;
	}

	public void setFriction(int friction) {
		this.friction = friction;
	}

	public Platform(GameComponent gameComponent, int row, int col) {
		super(gameComponent, row, col);
		this.setFriction(2);
	}

	@Override
	public void collideWith(Character g) {
		if (g.getxVelocity() < 0)
			g.addVelocityX(friction);
		if (g.getxVelocity() > 0)
			g.addVelocityX(-friction);

		if (g.getY() > this.getY() + this.getHeight() / 2) {
			g.setY(this.getY() + this.getHeight());
			g.setyVelocity(0);
			return;
		}

		if (g.getY() + g.getHeight() / 2 < this.getY()) {
			g.setY(this.getY() - g.getHeight());
			g.setyVelocity(0);
			return;
		}

		if (g.getX() > this.getX() + this.getWidth() / 2) {
			g.setX(this.getX() + this.getWidth());
			g.setxVelocity(0);
			return;
		}

		if (g.getX() + g.getWidth() / 2 < this.getX()) {
			g.setX(this.getX() - g.getWidth());
			g.setxVelocity(0);
			return;
		}
	}
}
