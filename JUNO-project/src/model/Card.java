package model;

class Card {

	Card(Color color, Value VALUE ) {
		this.color = color;
		this.VALUE = VALUE;
	}
	
	private Color color;
	public final Value VALUE;
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		if (this.VALUE.equals(Value.CAMBIACOLORE)||this.VALUE.equals(Value.PIUQUATTRO)) this.color = color;
	}
}
