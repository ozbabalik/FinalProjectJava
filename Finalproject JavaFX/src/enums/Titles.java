package enums;

public enum Titles {
	nA("keine"),
	BSc("BSc"),
	BA("BA"),
	MSc("MSc"),
	MA("MA"),
	Dr("Dr"),
	Prof("Prof");
	
	private final String label;
	
	private Titles(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Titles valueOfLabel(String label) {
	for(Titles t : values())
		if (t.label.equals(label)) {
            return t;
        }
	return null;
	}
	
	@Override
    public String toString() {
        return this.label;
    }
}
