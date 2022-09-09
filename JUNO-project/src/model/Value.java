package model;
/**
 * Enumeration which defines a set of 14 values, each with a corresponding int number  
 */
public enum Value {
	ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), REVERSE(10), SKIP(11), DRAW2(12), DRAW4(13), CHANGE(14);
	
	int number;

	Value(int i) {
		number=i;
	}
	/**
	 * return se int number associated to the Value
	 * @return
	 */
	public int toInt() {
		return number;
	}
}
