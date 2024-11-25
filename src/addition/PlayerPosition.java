package addition;

public class PlayerPosition {
	
	private int square;
	private int x;
	private int y;

	
	public PlayerPosition(int square, int x, int y) {
		this.square = square;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return (String.valueOf(square) + " " + String.valueOf(x) + " " + String.valueOf(y));
	}
	
	public int getSquare() {
		return square;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
