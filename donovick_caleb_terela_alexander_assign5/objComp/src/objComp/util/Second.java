package objComp.util;

/**
 * Second - Models the second object
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

	}

	/**
	 * setIntValue - sets the integer private data member value
	 *
	 * @return none
	 */
	public void setIntValue(int iIn) {
		
		IntValue = iIn;

	}

	/**
	 * setDoubleValue - sets the double private data member value
	 *
	 * @return none
	 */
	public void setDoubleValue(double dIn) {

		DoubleValue = dIn;

	}

	/**
	 * equals - checks equality
	 *
	 * @return boolean
	 */
    @Override
    public boolean equals(Object o) {
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
        return IntValue + 3 * ((Double) DoubleValue).hashCode();
    }
}
