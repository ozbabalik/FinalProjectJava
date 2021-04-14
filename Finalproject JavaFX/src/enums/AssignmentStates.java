package enums;

public enum AssignmentStates {
	assigned("zugeordnet"),
	canceled("storniert");
	private final String label;
	
	AssignmentStates(String label) {
		// TODO Auto-generated constructor stub
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static AssignmentStates valueOfLabel(String label) {
	for(AssignmentStates c : values())
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
