package enums;

/**
 * Diese Klasse modelliert die Geschlecht der Personen
 *
 */
public enum Genders {
	male("Herr"),
	female("Frau");

	private final String label;
	Genders(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Genders valueOfLabel(String label) {
	for(Genders g : values())
		if (g.label.equals(label)) {
            return g;
        }
	return null;
	}
	
	@Override
    public String toString() {
        return this.label;
    }
	
}
