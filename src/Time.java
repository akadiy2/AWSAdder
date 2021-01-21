public enum Time {
    SECOND("Seconds"), MINUTE("Minutes"), HOUR("Hours"), DAY("Days"), MONTH("Months");

    public String pluralForm;

    Time(String pluralForm) {
        this.pluralForm = pluralForm;
    }

    public String getPluralForm() {
        return this.pluralForm;
    }

    public static Time fromString(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("Cannot process null or empty input");
        }

        for (Time t : Time.values()) {
            if (input.equalsIgnoreCase(t.name())) {
                return t;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot find time %s from input", input));

    }
}
