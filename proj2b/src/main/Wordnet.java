package main;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;
import ngrams.NGramMap;
import java.util.*;

public class Wordnet {

    private HashMap<String, HashSet<Integer>> wiMap;
    private HashMap<Integer, HashSet<String>> iwMap;

    private HashMap<Integer, HashSet<Integer>> iiMap;


    public Wordnet(String synset, String hyponyms) {
        wiMap = new HashMap<>(); // word to id map
        iwMap = new HashMap<>(); // id to word map
        iiMap = new HashMap<>(); // id to id map
        In in1 = new In(synset);
        In in2 = new In(hyponyms);

        // input synset
        while (!in1.isEmpty()) {
            String nextLine = in1.readLine();
            String[] splitLine = nextLine.split(",");
            String[] names = splitLine[1].split("\\s+");
            int id = Integer.parseInt(splitLine[0]);
            // part 1 deal with WImap
            for (String name: names) {
                HashSet<Integer> tmp;
                if (wiMap.containsKey(name)) {
                    tmp = wiMap.get(name);
                } else {
                    tmp = new HashSet<>();
                }
                tmp.add(id);
                wiMap.put(name, tmp);
                // part2 deal with IWmap
                HashSet<String> tMP;
                if (iwMap.containsKey(id)) {
                    tMP = iwMap.get(id);
                } else {
                    tMP = new HashSet<>();
                }
                tMP.add(name);
                iwMap.put(id, tMP);
            }
        }

        // deal with second file
        while (!in2.isEmpty()) {
            String nextLine = in2.readLine();
            String[] splitLine = nextLine.split(",");
            int par = Integer.parseInt(splitLine[0]);
            HashSet<Integer> chi;
            if (iiMap.containsKey(par)) {
                chi = iiMap.get(par);
            } else {
                chi = new HashSet<>();
            }
            for (int i = 1; i < splitLine.length; i++) {
                chi.add(Integer.parseInt(splitLine[i]));
            }
            iiMap.put(par, chi);
        }
    }

    // k = 0 hyponyms only one word
    public TreeSet<String> hyponyms(String word) {
        TreeSet<String> re = new TreeSet<>(); // Because it will make sequence by itself
        if (!wiMap.containsKey(word)) {
            return re;
        }
        HashSet<Integer> ids = wiMap.get(word);
        for (int id: ids) {
            hypoHelper(id, re);
        }
        return re;
    }

    private void hypoHelper(int id, TreeSet<String> re) {
        HashSet<String> words = iwMap.get(id);
        re.addAll(words);
        HashSet<Integer> chis = iiMap.get(id);
        if (chis == null) {
            return;
        }
        for (int chi: chis) {
            hypoHelper(chi, re);
        }
    }


    // hyponyms
    public TreeSet<String> hyponyms(List<String> words) {
        TreeSet<String> re = new TreeSet<>();
        if (words == null) {
            return re;
        }
        TreeSet<String>[] all = new TreeSet[words.size()];
        for (int i = 0; i < words.size(); i++) {
            all[i] = hyponyms(words.get(i));
        }
        // if all[0] be null then the set must be null; else we check each set and combine share part.
        if (!(all[0] == null)) {
            re = all[0];
            for (int i = 1; i < all.length; i++) {
                re.retainAll(all[i]);
            }
        }
        return re;
    }


    public TreeSet<String> hyponymsKPop(List<String> words, NGramMap map, int startyear, int endyear, int k) {
        TreeSet<String> re = hyponyms(words);
        if (re == null) {
            return new TreeSet<>();
        }
        if (k <= 0) {
            return re;
        }
        TreeSet<String> kPopularHyponyms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        MaxPQ<WordWeight> all = new MaxPQ<>();
        for (String word: re) {
            Collection<Double> weight = map.countHistory(word, startyear, endyear).values();
            if (weight.isEmpty()) {
                continue;
            }
            double weIght = 0;
            for (Double part: weight) {
                weIght += part;
            }
            all.insert(new WordWeight(word, weIght));
        }
        Iterator<WordWeight> it = all.iterator();
        int i = 0;
        while (it.hasNext() && i < k) {
            kPopularHyponyms.add(it.next().word());
            i += 1;
        }
        return kPopularHyponyms;
    }

    public static class WordWeight implements Comparable<WordWeight> {
        private String word;
        private double weight;

        private WordWeight(String word, double weight) {
            this.word = word;
            this.weight = weight;
        }

        public String word() {
            return word;
        }

        public double weight() {
            return weight;
        }
        @Override
        public int compareTo(WordWeight other) {
            return Double.compare(this.weight(), other.weight());
        }
    }
}
