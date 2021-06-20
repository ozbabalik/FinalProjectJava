package enums;


/**
 * Diese Klasse modelliert die Status der Buchungen
 *
 */
public enum BookingStates {
	interested("interessent"),
	confirmed("bestätigt"),
	running("laufend"),
	completed("beendet"),
	canceled("storniert");
	
	private final String label;
	BookingStates(String label) {
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static BookingStates valueOfLabel(String label) {
	for(BookingStates c : values())
		if (c.label.equals(label)) {
            return c;
        }
	return null;
	}
	
	@Override
    public String toString() {
        return this.label;
    }
}
