package enums;

public enum CourseCategories {
	CAT1("Kategorie 1"),
	CAT2("Kategorie 2"),
	CAT3("Kategorie 3");
	private final String label;
	
	CourseCategories(String label) {
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static CourseCategories valueOfLabel(String label) {
	for(CourseCategories c : values())
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
