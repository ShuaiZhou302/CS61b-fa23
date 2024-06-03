package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {

    private Wordnet graph;
    private NGramMap map;

    public HyponymsHandler(Wordnet graph, NGramMap map) {
        this.graph = graph;
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startyear = q.startYear();
        int endyear = q.endYear();
        int k = q.k();
        TreeSet<String> tmp = new TreeSet<>();
        if (k <= 0) {
            if (words.size() == 1) {
                tmp = graph.hyponyms(words.get(0));
            } else {
                tmp = graph.hyponyms(words);
            }
            return tmp.toString();
        } else {
            tmp = graph.hyponymsKPop(words, map, startyear, endyear, k);
            return  tmp.toString();
        }
    }
}
