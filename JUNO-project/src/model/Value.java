package model;

public enum Value {
	ZERO(0), UNO(1), DUE(2), TRE(3), QUATTRO(4), CINQUE(5), SEI(6), SETTE(7), OTTO(8), NOVE(9), REVERSE(10), SKIP(11), DRAW2(12), DRAW4(13), CHANGE(14);
	
	int number;

	Value(int i) {
		number=i;
	}
	
	public int toInt() {
		return number;
	}
}
