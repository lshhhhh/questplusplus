package shef.mt.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;
import shef.mt.features.util.Doc;
import shef.mt.features.util.Sentence;

public class Word2vecProcessor extends ResourceProcessor {
    private HashMap<String, List<Float>> word2vec;

    public Word2vecProcessor(String path) {
        this.word2vec = new HashMap<String, List<Float>>();

        // Read and store word2vec from file.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (reader.ready()) {
                String line = reader.readLine().trim();
                String[] elements = line.split(" ");
                String word = elements[0];
                List<String> values = 
                    Arrays.asList(Arrays.copyOfRange(elements, 1, elements.length));
                List<Float> vector = values.stream()
                                           .map(Float::parseFloat)
                                           .collect(Collectors.toList());
                this.word2vec.put(word, vector);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StopWordsProcessor.class.getName())
                .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StopWordsProcessor.class.getName())
                .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processNextSentence(Sentence s) {
        s.setValue("word2vec", this.word2vec);
    }

    @Override
    public void processNextDocument(Doc source) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

