import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        //expected: 1991: 0,
        //        1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears1 = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears1);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }


        TimeSeries Tyler = new TimeSeries();
        Tyler.put(2013, 0.0);
        Tyler.put(2014, 900.0);
        Tyler.put(2015, 1500.0);

        TimeSeries Frank = new TimeSeries();
        Frank.put(2012, 2.0);
        Frank.put(2013, 2.0);
        Frank.put(2014, 2.0);
        Frank.put(2015, 2.0);

        TimeSeries dividePopulation = Tyler.dividedBy(Frank);
        // expected: 2013: 0,0
        //           2014: 450.0
        //           2015: 750.0

        List<Integer> expectedYears2 = new ArrayList<>
                (Arrays.asList(2013, 2014, 2015));

        assertThat(dividePopulation.years()).isEqualTo(expectedYears2);

        List<Double> expectedTota2 = new ArrayList<>
                (Arrays.asList(0.0, 450.0, 750.0));

        for (int i = 0; i < expectedTota2.size(); i += 1) {
            assertThat(dividePopulation.data().get(i)).isWithin(1E-10).of(expectedTota2.get(i));
        }
    }
} 