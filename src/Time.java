public enum Time {
    SECOND("Seconds"), MINUTE("Minutes"), HOUR("Hours"), DAY("Days"), MONTH("Months");

    public String pluralForm;

    Time(String pluralForm) {
        this.pluralForm = pluralForm;
    }

    public String getPluralForm() {
        return this.pluralForm;
    }
}
