package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile){

        NGramMap map = new NGramMap(wordFile, countFile);
        Wordnet net = new Wordnet(synsetFile, hyponymFile);
        return new HyponymsHandler(net, map);
    }
}
