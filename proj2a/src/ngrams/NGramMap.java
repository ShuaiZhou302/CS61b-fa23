package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    TimeSeries totalEachyear;
    private Map<String, TimeSeries> wordMap;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In in1 = new In(wordsFilename);
        In in2 = new In(countsFilename);
        wordMap = new HashMap<>(); // store the map to each word;
        totalEachyear = new TimeSeries();
        while (!in1.isEmpty()) {
            String nextLine = in1.readLine();
            String[] splitLine = nextLine.split("\t");
            String key = splitLine[0];
            if (!wordMap.containsKey(key)) { // if no keys there , store it
                TimeSeries newWord = new TimeSeries();
                newWord.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
                wordMap.put(key, newWord);
            } else { // else the map contains the key
                wordMap.get(key).put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
            }
        }
        while (!in2.isEmpty()) {  // this relates to the divide method, to calculate the ratio as denominator
            String nextLine2 = in2.readLine();
            String[] splitLine2 = nextLine2.split(",");
            int key = Integer.parseInt(splitLine2[0]);
            totalEachyear.put(Integer.parseInt(splitLine2[0]), Double.parseDouble(splitLine2[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        return  new TimeSeries(wordMap.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordMap.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalEachyear, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries temp1 = new TimeSeries(wordMap.get(word), startYear, endYear);
        TimeSeries temp2 = new TimeSeries(totalEachyear, startYear, endYear);
        return temp1.dividedBy(temp2);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries temp1 = new TimeSeries(wordMap.get(word), MIN_YEAR, MAX_YEAR);
        return temp1.dividedBy(totalCountHistory());
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word: words) {
            if (!wordMap.containsKey(word)) {
                continue;
            }
            sum = sum.plus(weightHistory(word, startYear, endYear));
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();
        for (String word: words) {
            if (!wordMap.containsKey(word)) {
                continue;
            }
            sum = sum.plus(weightHistory(word));
        }
        return sum;
    }
}
