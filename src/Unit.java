public enum Unit {
    GB, TB, EB, PB;

    public static Unit fromString(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("Cannot process null or empty input");
        }

        for (Unit u : Unit.values()) {
            if (input.equalsIgnoreCase(u.name())) {
                return u;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot find unit %s from input", input));

    }

}
