import java.util.HashMap;
import java.util.Map;

public class RegionPricingService {
    private static Map<Region, Map<Double, Double>> regionToPricingRules = new HashMap<>();

    static {
        Map<Double, Double> pricingMap = new HashMap<>();
        pricingMap.put(500., 0.029);
        pricingMap.put(1500., 0.025);
        pricingMap.put(3000., 0.02);

        // same prices for virginia, ohio AND oregon
        regionToPricingRules.put(Region.VIRGINIA, pricingMap);
        regionToPricingRules.put(Region.OHIO, pricingMap);
        regionToPricingRules.put(Region.OREGON, pricingMap);

        pricingMap.put(500., 0.035);
        pricingMap.put(1500., 0.03);
        pricingMap.put(3000., 0.024);

        regionToPricingRules.put(Region.NORTHERN_CALIFORNIA, pricingMap);

        pricingMap.put(500., 0.034);

        regionToPricingRules.put(Region.MUMBAI, pricingMap);

        pricingMap.put(500., 0.036);
        pricingMap.put(1500., 0.031);
        pricingMap.put(3000., 0.025);

        regionToPricingRules.put(Region.SEOUL, pricingMap);

        pricingMap.put(500., 0.037);
        pricingMap.put(1500., 0.032);
        regionToPricingRules.put(Region.SINGAPORE, pricingMap);
        regionToPricingRules.put(Region.SYNDEY, pricingMap);

        pricingMap.put(500., 0.036);
        pricingMap.put(1500., 0.031);
        regionToPricingRules.put(Region.TOKYO, pricingMap);

        pricingMap.put(500., 0.032);
        pricingMap.put(1500., 0.028);
        pricingMap.put(3000., 0.022);

        regionToPricingRules.put(Region.CANADA, pricingMap);

        pricingMap.put(500., 0.033);
        pricingMap.put(1500., 0.029);
        pricingMap.put(3000., 0.023);
        regionToPricingRules.put(Region.FRANKFURT, pricingMap);

        pricingMap.put(500., 0.031);
        pricingMap.put(1500., 0.027);
        pricingMap.put(3000., 0.022);
        regionToPricingRules.put(Region.IRELAND, pricingMap);

        pricingMap.put(500., 0.035);
        pricingMap.put(1500., 0.03);
        pricingMap.put(3000., 0.024);
        regionToPricingRules.put(Region.LONDON, pricingMap);
        regionToPricingRules.put(Region.PARIS, pricingMap);

        pricingMap.put(500., 0.02945);
        pricingMap.put(1500., 0.02565);
        pricingMap.put(3000., 0.0209);
        regionToPricingRules.put(Region.STOCKHOLM, pricingMap);

        pricingMap.put(500., 0.058);
        pricingMap.put(1500., 0.05);
        pricingMap.put(3000., 0.04);
        regionToPricingRules.put(Region.SAO_PAULO, pricingMap);

        pricingMap.put(500., 0.035);
        pricingMap.put(1500., 0.03);
        pricingMap.put(3000., 0.024);
        regionToPricingRules.put(Region.US_WEST, pricingMap);
        regionToPricingRules.put(Region.US_EAST, pricingMap);
    }

    public static double retrievePricingForRegion(String region, double storageAmount) {

        if (region == null) {
            throw new IllegalArgumentException("Region input given is null: " + region);
        }

        Region r;

        try {
            r = Region.fromString(region);
        } catch (Exception e) {
            throw new IllegalArgumentException("Region given is invalid: " + region);
        }



        if (storageAmount <= 500) {
            double price = storageAmount * regionToPricingRules.get(r).get(500.);
            return price;
        }


        if (storageAmount > 500 && storageAmount <= 2000) {
            double price = 500. * regionToPricingRules.get(r).get(500.);
            storageAmount -= 500;
            price += storageAmount * regionToPricingRules.get(r).get(1500.);
            return price;
        }

        if (storageAmount > 2000) {
            double price = 500. * regionToPricingRules.get(r).get(500.);
            storageAmount -= 500;
            price += storageAmount * regionToPricingRules.get(r).get(1500.);
            storageAmount -= 1500;

            if (storageAmount > 0) {
                price += storageAmount * regionToPricingRules.get(r).get(3000.);
                return price;
            } else return price;

        }

        throw new IllegalArgumentException("Unable to retrieve pricing for the inputs: " + region + " " + storageAmount);
    }
}
