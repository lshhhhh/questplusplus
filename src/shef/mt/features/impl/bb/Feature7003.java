package shef.mt.features.impl.bb;

import java.util.ArrayList;
import shef.mt.features.impl.Feature;
import shef.mt.features.util.Sentence;

public class Feature7003 extends Feature {
    
    public Feature7003() {
        this.setIndex(7003);
        this.setDescription("Ratio of complex word of source and target");
        this.addResource("source.simplewords");
        this.addResource("target.simplewords");
    }
    
    @Override
    public void run(Sentence source, Sentence target) {
        ArrayList<String> sourceSimpleWords =
            (ArrayList<String>) source.getValue("simplewords");
        String[] sourceTokens = source.getTokens();
        int sourceComplexWords = 0;
        for (String token : sourceTokens) {
            if (!sourceSimpleWords.contains(token)) {
                sourceComplexWords += 1;
            }
        }

        ArrayList<String> targetSimpleWords =
            (ArrayList<String>) target.getValue("simplewords");
        String[] targetTokens = target.getTokens();
        int targetComplexWords = 0;
        for (String token : targetTokens) {
            if (!targetSimpleWords.contains(token)) {
                targetComplexWords += 1;
            }
        }

        //defining value for the feature
        this.setValue(((float) sourceComplexWords) / targetComplexWords);
    }
}
