import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

// The standard for the conversions is TB/MONTH
public class Adder {

    /**
     * The first 500000 GB per month is $0.029 per GB (0 to 500000 GB)
     * The next 1500000 GB / MONTH is $0.025 (the next 1500 TB / MONTH) so from 500001 GB to 1500000 GB
     * 3000000 more GB/ MOnTH is $.02 1500001 to 3000000 GB
     * each region has a set of pricings
     *
     * @param args - arguments to be given to the program
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Starting up AWS Kinesis Pricing Tool. You can get an estimate price per month based on how much data you will be streaming.");
        System.out.print("Enter your name: ");

        // Initialize the reader for console input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String name = reader.readLine();
        try {
            do {
                // Ask for the numerical amount of data
                System.out.print(name + ": Enter an amount: ");

                double numericalAmountOfStorage = AWSUtility.parseAmountToDouble(reader.readLine());

                // Ask for the unit of storage
                System.out.print(name + ": Enter a unit of byte size (Options: GB, TB, PB, EB): ");
                Unit u = AWSUtility.getUnits(reader.readLine());

                numericalAmountOfStorage = AWSUtility.convertToTB(numericalAmountOfStorage, u.name());

                // Ask for a rate of time (per second, per minute)
                System.out.print(name + ": Enter a rate of time: (SECOND, MINUTE, HOUR, DAY) ");

                Time t = AWSUtility.getTime(reader.readLine());

                String storageInput = numericalAmountOfStorage + " TB per " + t.name();

                // Enter a time period (Ex: 3 GB / Sec for 2 days or 4 months)
                System.out.print(name + ": Enter a time period (Ex: 2 days or 4 months) ");

                String timeRange = reader.readLine();
                String[] timeRangeTokenized = timeRange.split(" ");

                // Now I have the amount per unit for x amount of time
                // Convert everything to TB/Month
                // Retrieve the region
                // According to the region, convert TB/Month to pricing accordingly

                double storageUsageRate = numericalAmountOfStorage / (AWSUtility.convertToMonths(t.name()));

                // convert time range to months
                //double timeRangeMonths = AWSUtility.convertTimeRangeToMonths(Double.parseDouble(timeRangeTokenized[0]), timeRangeTokenized[1]);

                System.out.print(name + ": Enter a region this data is to be serviced in: ");

                String region = reader.readLine();
                double price = RegionPricingService.retrievePricingForRegion(region, storageUsageRate);
                DecimalFormat df = new DecimalFormat("0.00");
                System.out.println(String.format("%s, based on your input of %s in %s you will expect to be paying at least: $%s per month", name, storageInput, region, df.format((price / 0.001))));

            } while (!reader.readLine().equalsIgnoreCase("quit"));
        } catch (Exception e) {
            System.out.println("Unable to run application " + e.getMessage());
        }

    }
}
