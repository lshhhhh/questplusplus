package shef.mt.features.impl.bb;

import java.util.ArrayList;
import shef.mt.features.impl.Feature;
import shef.mt.features.util.Sentence;

public class Feature7001 extends Feature {
    
    public Feature7001() {
        this.setIndex(7001);
        this.setDescription("Complex word count of source sentence");
        this.addResource("source.simplewords");
    }
    
    @Override
    public void run(Sentence source, Sentence target) {
        ArrayList<String> simpleWords =
            (ArrayList<String>) source.getValue("simplewords");
        String[] tokens = source.getTokens();
        int complexWords = 0;
        for (String token : tokens) {
            if (!simpleWords.contains(token)) {
                complexWords += 1;
            }
        }
        //defining value for the feature
        this.setValue(((float) complexWords) / tokens.length);
    }
}
