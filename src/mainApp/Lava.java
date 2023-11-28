package mainApp;

public class Lava extends Platform {

	@Override
	public void collideWith(Character g) {
		super.collideWith(g);
		g.loseLife();
	}

	public Lava(GameComponent gameComponent, int row, int col) {
		super(gameComponent, row, col);
	}

}
