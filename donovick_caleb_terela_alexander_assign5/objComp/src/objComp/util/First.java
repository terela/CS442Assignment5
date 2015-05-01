package objComp.util;

/**
 * First - Models the first object
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
	 * setStringValue - sets the string private data member value
	 *
	 * @return none
	 */
	public void setStringValue(String sIn) {

		StringValue = sIn;

	}

	/**
	 * equals - checks equality
	 *
	 * @return boolean
	 */
    @Override
    public boolean equals(Object o) {
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
        return IntValue + 
            31 * StringValue.length() + 
            37 * StringValue.charAt(0) + 
            41 * StringValue.charAt(StringValue.length()/2) + 
            47 * StringValue.charAt(StringValue.length() - 1) +
            67 * StringValue.charAt(IntValue % StringValue.length());
    }
}
