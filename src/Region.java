public enum Region {
    VIRGINIA, OHIO, NORTHERN_CALIFORNIA, OREGON, MUMBAI, SEOUL, SINGAPORE, SYNDEY, TOKYO, CANADA, FRANKFURT, IRELAND, LONDON, PARIS, STOCKHOLM, SAO_PAULO, US_WEST, US_EAST;

    public static Region fromString(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("Cannot process null or empty input");
        }

        for (Region r : Region.values()) {
            if (input.equalsIgnoreCase(r.name())) {
                return r;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot find region %s from input", input));

    }
}
