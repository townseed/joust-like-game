package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListener implements ActionListener {

	private GameComponent g;

	public GameAdvanceListener(GameComponent g) {
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	public void advanceOneTick() {
		this.g.update();
		this.g.drawLevel();

	}
}
