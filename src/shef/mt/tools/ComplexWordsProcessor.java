package shef.mt.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import shef.mt.features.util.Doc;
import shef.mt.features.util.Sentence;

public class ComplexWordsProcessor extends ResourceProcessor {
    private ArrayList<String> simpleWords;

    public ComplexWordsProcessor(String path) {
        // Create hash of words.
        this.simpleWords = new ArrayList<>();

        // Read and store words from file.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (reader.ready()) {
                String word = reader.readLine().trim();
                this.simpleWords.add(word);
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
        s.setValue("simplewords", this.simpleWords);
    }

    @Override
    public void processNextDocument(Doc source) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

