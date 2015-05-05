package objComp.util;

import objComp.util.MyLogger;

/**
 * First - models the first object
 */
public class First {

	// private data members

	private int IntValue;
	String StringValue;

	/**
	 * First Constructor - constructs an empty first object
	 *
	 * @return first the first object
	 */
	public First() {
		MyLogger.printToStdout(2, "Constructor in First called.");
	}

	/**
	 * setIntValue - sets the integer private data member value
	 *
	 * @return none
	 */
	public void setIntValue(int iIn) {

		MyLogger.printToStdout(3, "setIntValue() in First called.");

		IntValue = iIn;

	}

	/**
	 * setIntValue - sets the integer private data member value
	 *
	 * @return none
	 */
	public void setIntValue(Integer iIn) {

		MyLogger.printToStdout(3, "setIntValue() in First called.");

		IntValue = (int) iIn;

	}

	/**
	 * setStringValue - sets the string private data member value
	 *
	 * @return none
	 */
	public void setStringValue(String sIn) {

		MyLogger.printToStdout(3, "setStringValue() in First called.");

		StringValue = sIn;

	}

	/**
	 * equals - checks equality
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {

		MyLogger.printToStdout(3, "equals() in First called.");

		return ((o instanceof First) && 
				(IntValue == ((First) o).IntValue) && 
				(StringValue.equals(((First) o).StringValue)));
	}


	/**
	 * hashCode - generates a hashcode
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {

		MyLogger.printToStdout(3, "hashCode() in First called.");

		return IntValue + 
			31 * StringValue.length() + 
			37 * StringValue.charAt(0) + 
			41 * StringValue.charAt(StringValue.length()/2) + 
			47 * StringValue.charAt(StringValue.length() - 1) +
			67 * StringValue.charAt(IntValue % StringValue.length());
	}

}
