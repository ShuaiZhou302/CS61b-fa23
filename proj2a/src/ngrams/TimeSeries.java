package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i = startYear; i <= endYear; i++) {
            if (!ts.containsKey(i)) {
                continue;
            }
            this.put(i, ts.get(i));
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet()); // return a list of set sequence organized
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> returnList = new ArrayList<>();
        for (Integer key : this.keySet()) {
            Double value = this.get(key);
            returnList.add(value);
        }
        return returnList;   // return a list of data organized as years()
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        if (this.size() == 0 && ts.size() == 0) {
            return new TimeSeries();
        } // first scenario return empty Time series
        TimeSeries returnSeries = new TimeSeries();
        for (Integer key1: this.keySet()) {
            returnSeries.put(key1, this.get(key1)); // use.keySet and get to avoid destructive move
        } // check the first Time series
        for (Integer key2: ts.keySet()) {
            if (returnSeries.get(key2) == null) {
                returnSeries.put(key2, ts.get(key2)); // if the first Time series doesn't have this key
                continue;
            }
            returnSeries.put(key2, returnSeries.get(key2) + ts.get(key2)); // sum the key's value
        }
        return returnSeries;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries returnSeries = new TimeSeries();
        for (Integer key1: this.keySet()) {
            if (ts.get(key1) == null || ts.get(key1) == 0) {
                throw new IllegalArgumentException();
            }
            returnSeries.put(key1, this.get(key1) / ts.get(key1));
        }
        return returnSeries;
    }
}
