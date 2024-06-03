package main;
import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        


        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsets = "./data/wordnet/synsets.txt";
        String hyponyms = "./data/wordnet/hyponyms.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        Wordnet net = new Wordnet(synsets, hyponyms);



        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms",new HyponymsHandler(net,ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
