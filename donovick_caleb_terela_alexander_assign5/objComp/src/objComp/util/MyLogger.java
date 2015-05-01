package objComp.util;

/**
 * MyLogger - simulates the java logger class
 */
public class MyLogger {

	/**
	 *
	 */
	private static int LOGGER_VALUE;

	/**
	 * Constructor - constructs a myLogger object
	 *
	 * @return the myLogger object
	 */
	public MyLogger(int myLoggerValueIn) {

		MyLogger.printToStdout(2, "Constructor in MyLogger called.");

		setMyLoggerValue(myLoggerValueIn);

	}

	/**
	 * setMyLoggerValue - sets the myLogger value
	 *
	 * @return none
	 */
	public static void setMyLoggerValue(int myLoggerValueIn) {

		MyLogger.printToStdout(3, "setMyLoggerValue() in MyLogger called.");

		LOGGER_VALUE = myLoggerValueIn;

	}

	/**
	 * getMyLoggerValue - gets the myLogger value
	 *
	 * @return LOGGER_VALUE the current logger value
	 */
	public static int getMyLoggerValue() {

		MyLogger.printToStdout(3, "getMyLoggerValue() in MyLogger called.");

		return LOGGER_VALUE;

	}

	/**
	 * printToStdout - prints out the myLogger value and message
	 *
	 * @return none
	 */
	public static void printToStdout(int level, String myLoggerMessage) {

		if (LOGGER_VALUE == level  && LOGGER_VALUE != 4) {

			System.out.println("[" + level + "]:" + myLoggerMessage);

		}

	}


	/**
	 * toString - returns a string representation of the MyLogger object
	 *
	 * @return string representation of the Logger object
	 */
	public String toString() {

		return "MyLogger";

	}

}
