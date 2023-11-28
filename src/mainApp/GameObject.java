package mainApp;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class GameObject {

	public final static double GRID_CONSTANT_X = MainApp.SCREEN_SIZE_X / 20;
	public final static double GRID_CONSTANT_Y = MainApp.SCREEN_SIZE_Y / 8;

	public final static int RIGHT = 0;
	public final static int LEFT = 1;

	private double x, y;
	private double yVelocity;
	private double xVelocity;
	protected GameComponent gameComponent;
	private ArrayList<Image> images;
	private Image currentImage;

	public GameObject(GameComponent gameComponent, int row, int col) {
		this.x = col * GRID_CONSTANT_X;
		this.y = row * GRID_CONSTANT_Y;
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.gameComponent = gameComponent;
		this.images = new ArrayList<>();
	}

	public void addImage(String filename) {
		Image temp = null;
		try {
			temp = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("Image incorrect");
		}
		this.images.add(temp);
		this.setImage(RIGHT);
	}

	public Image getImage() {
		return currentImage;
	}

	public void setImage(int state) {
		this.currentImage = this.images.get(state);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void addVelocityY(double accelY) {
		this.yVelocity = yVelocity + accelY;
	}

	public void addVelocityX(double accelX) {
		this.xVelocity = xVelocity + accelX;
	}

	public double getWidth() {
		return this.getImage().getWidth(null);
	}

	public double getHeight() {
		return this.getImage().getHeight(null);
	}

	public abstract void collideWith(Character g);

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, this.getWidth(), this.getHeight());
	}

	public boolean overlaps(GameObject other) {
		return this.getBoundingBox().intersects(other.getBoundingBox());
	}

	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.drawImage(getImage(), (int) this.getX(), (int) this.getY(), null);
	}

	public void update() {
		this.x += this.xVelocity;
		this.y += this.yVelocity;
	}

}
