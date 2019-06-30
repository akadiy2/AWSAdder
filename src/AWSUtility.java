public class AWSUtility {
    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration, String search) {
        if (search != null && !search.isEmpty()) {
            for (T each : enumeration.getEnumConstants()) {
                int result = each.name().compareToIgnoreCase(search);
                if (result == 0) {
                    return each;
                }
            }
        }

        return null;
    }


    /**
     * The Unit to be parsed from the input.
     *
     * @param input - The string that is used as the lookup in the enum
     * @return - THe {@link Unit} derived from the input.
     * @throws Exception
     */
    public static Unit getUnits(String input) throws Exception {
        Unit u = AWSUtility.searchEnum(Unit.class, input);
        if (u == null) {
            throw new Exception("Invalid unit type found");
        }

        return u;
    }

    /**
     * The time to be returned by parsing the given input and using it to search the enum
     *
     * @param input - the lookup string to retrieve the corresponding {@link Time}
     * @return - The {@link Time} associated with the given input.
     * @throws Exception
     */
    public static Time getTime(String input) throws Exception {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Time string given is null or empty");
        }

        return AWSUtility.searchEnum(Time.class, input);
    }

    public static Double parseAmountToDouble(String input) throws Exception {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            throw new Exception("Unable to process input");
        }
    }

    // 30 MB TO TB

    // 40 GB / 3 days

    /**
     * 1 gb = 0.001 TB
     * 1 mb = 0.000001 TB
     * 1 TB = 1 TB
     * 1 PB = 1024 TB
     */
    public static double convertToTB(double numericalAmount, String units) {
        if (numericalAmount == 0) {
            return 0;
        }

        if (units == null || units.isEmpty()) {
            throw new IllegalArgumentException("Units given is null or empty");
        }

        if (units.equalsIgnoreCase("TB")) {
            return numericalAmount;
        }

        if (units.equalsIgnoreCase("GB")) {
            return numericalAmount * 0.001;
        }

        if (units.equalsIgnoreCase("MB")) {
            return numericalAmount * 0.000001;
        }

        if (units.equalsIgnoreCase("PB")) {
            return numericalAmount * 1024;
        }

        throw new IllegalArgumentException("An error occurred with the numerical amount given");
    }

    /**
     * 1 second = 0.00000038 months
     * 1 minute = 0.000023 months
     * 1 hour = 0.00136986 months
     * 1 day = 0.033 months
     * 1 month = 1 month
     * 1 year = 12 months
     *
     * @param timeUnits - the unit of time for the numerical amount
     * @return - The time amount in months
     */
    public static double convertToMonths(String timeUnits) {
        if (timeUnits.equalsIgnoreCase("SECOND")) {
            return 0.00000038;
        }

        if (timeUnits.equalsIgnoreCase("MINUTE")) {
            return 0.000023;
        }

        if (timeUnits.equalsIgnoreCase("HOUR")) {
            return .00136986;
        }

        if (timeUnits.equalsIgnoreCase("DAY")) {
            return 0.033;
        }

        if (timeUnits.equalsIgnoreCase("MONTH")) {
            return 1;
        }

        if (timeUnits.equalsIgnoreCase("YEAR")) {
            return 12.0;
        }

        throw new IllegalArgumentException("Invalid timeUnit given");

    }

    public static double convertTimeRangeToMonths(double time, String timeUnits) {
        if (time == 0) {
            throw new IllegalArgumentException("Cannot convert zero time");
        }

        String timeUnitsFormatted = timeUnits.toLowerCase().trim();
        String timeUnit;

        if (timeUnitsFormatted.contains("second")) {
            timeUnit = "SECOND";
        } else if (timeUnitsFormatted.contains("minute")) {
            timeUnit = "MINUTE";
        } else if (timeUnitsFormatted.contains("hour")) {
            timeUnit = "HOUR";
        } else if (timeUnitsFormatted.contains("day")) {
            timeUnit = "DAY";
        } else if (timeUnitsFormatted.contains("month")) {
            timeUnit = "MONTH";
        } else if (timeUnitsFormatted.contains("year")) {
            timeUnit = "YEAR";
        } else {
            throw new IllegalArgumentException("Invalid unit of time was given: " + timeUnits);
        }

        return time * 1.0 * convertToMonths(timeUnit);

    }
}
