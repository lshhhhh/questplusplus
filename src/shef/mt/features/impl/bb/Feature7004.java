package shef.mt.features.impl.bb;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import shef.mt.features.impl.Feature;
import shef.mt.features.util.Sentence;

public class Feature7004 extends Feature {
    
    public Feature7004() {
        this.setIndex(7004);
        this.setDescription("Average word vector of source sentence");
        this.addResource("source.word2vec");
    }
    
    @Override
    public void run(Sentence source, Sentence target) {
        HashMap<String, List<Float>> word2vec = 
            (HashMap<String, List<Float>>) source.getValue("word2vec");
        List<String> tokens = Arrays.asList(source.getTokens());
        
        long wordCount = tokens.stream()
                               .filter(token -> word2vec.containsKey(token))
                               .count();
        double sumVector = tokens.stream()
                                .filter(token -> word2vec.containsKey(token))
                                .map(token -> word2vec.get(token))
                                .map(vector -> vector.stream()
                                                     .mapToDouble(d->d)
                                                     .sum())
                                .mapToDouble(d->d)
                                .sum();

        //defining value for the feature
        this.setValue((float) sumVector / wordCount);
    }
}
