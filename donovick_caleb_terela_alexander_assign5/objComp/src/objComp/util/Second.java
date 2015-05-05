package objComp.util;

import objComp.util.MyLogger;

/**
 * Second - models the second object
 */
public class Second {

	// private data members

	private double DoubleValue;
	private int IntValue;

	/**
	 * Second Constructor - constructs an empty second object
	 *
	 * @return second the second object
	 */
	public Second() {

		MyLogger.printToStdout(2, "Constructor in Second called.");

	}

	/**
	 * setIntValue - sets the integer private data member value
	 *
	 * @return none
	 */
	public void setIntValue(int iIn) {

		MyLogger.printToStdout(3, "setIntValue() in Second called.");

		IntValue = iIn;

	}

	/**
	 * setIntValue - sets the integer private data member value
	 *
	 * @return none
	 */
	public void setIntValue(Integer iIn) {

		MyLogger.printToStdout(3, "setIntValue() in Second called.");

		IntValue = (int) iIn;
	}

	/**
	 * setDoubleValue - sets the double private data member value
	 *
	 * @return none
	 */
	public void setDoubleValue(double dIn) {

		MyLogger.printToStdout(3, "setIntValue() in Second called.");

		DoubleValue = dIn;

	}

	/**
	 * setDoubleValue - sets the double private data member value
	 *
	 * @return none
	 */
	public void setDoubleValue(Double dIn) {

		MyLogger.printToStdout(3, "setIntValue() in Second called.");

		DoubleValue = (double) dIn;
	}

	/**
	 * equals - checks equality
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {

		MyLogger.printToStdout(3, "equals() in Second called.");

		return ((o instanceof Second) && 
				(IntValue == ((Second) o).IntValue) && 
				(DoubleValue == ((Second) o).DoubleValue));
	}


	/**
	 * hashCode - generates a hashcode
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {

		MyLogger.printToStdout(3, "hashCode() in Second called.");

		return IntValue + 3 * ((Double) DoubleValue).hashCode();
	}	

}
