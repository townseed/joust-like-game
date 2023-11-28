package mainApp;

public class IncorrectNumberOfLinesException extends Exception {
	private String message;

	IncorrectNumberOfLinesException(int totalExpected, int lines) {
		this.message = "File should have " + totalExpected + " lines, this file had " + lines + " lines.";
	}

	public String getMessage() {
		return message;

	}
}
