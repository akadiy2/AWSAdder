import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

// The standard for the conversions is TB/MONTH
public class Adder {

    /**
     * each region has a set of pricings for exmaple in virginia:
     * The first 500 TB per month is $0.029 per GB (0 to 500000 GB)
     * The next 1500 TB / MONTH is $0.025
     * 3 more PB is $.02
     *
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

                double storageUsageRate = numericalAmountOfStorage / (AWSUtility.convertToMonths(t.name()));

                // convert time range to months
                //double timeRangeMonths = AWSUtility.convertTimeRangeToMonths(Double.parseDouble(timeRangeTokenized[0]), timeRangeTokenized[1]);

                // Enter a region to specify the pricing tiers for the data being streamed
                System.out.print(name + ": Enter a region this data is to be serviced in: ");

                String region = reader.readLine();

                // Retrieve the pricing for the region`
                double price = RegionPricingService.retrievePricingForRegion(region, storageUsageRate);
                DecimalFormat df = new DecimalFormat("0.00");
                System.out.println(String.format("%s, based on your input of %s in %s you will expect to be paying at least: $%s per month", name, storageInput, region, df.format((price / 0.001))));

            } while (!reader.readLine().equalsIgnoreCase("quit"));
        } catch (Exception e) {
            System.out.println("Unable to run application " + e.getMessage());
        }

    }
}
