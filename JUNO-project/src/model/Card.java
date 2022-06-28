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
		if (this.VALUE.equals(Value.CHANGE)||this.VALUE.equals(Value.DRAW4)) this.color = color;
	}
	
	/**
	 * ritorna il nome dell'immagine jpg corrispondente nella cartella cardImages
	 */
	@Override
	public String toString() {
		String c=  color.toString();
		if (VALUE.toInt()<10) return c.charAt(0)+c.toLowerCase().subSequence(1, color.toString().length()).toString()+"_"+VALUE.toInt()+".jpg";
		else {
			String v=VALUE.toString();
			return c.charAt(0)+c.toLowerCase().subSequence(1, color.toString().length()).toString()+"_"+v.charAt(0)+v.toLowerCase().subSequence(1, VALUE.toString().length()).toString()+".jpg";
		}
	}
	
}
