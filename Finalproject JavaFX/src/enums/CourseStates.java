package enums;

public enum CourseStates {
	planned("geplant"),
	scheduled("bestätigt"),
	running("laufend"),
	completed("beendet"),
	canceled("storniert");
	
	private final String label;
	CourseStates(String label) {
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static CourseStates valueOfLabel(String label) {
	for(CourseStates c : values())
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
