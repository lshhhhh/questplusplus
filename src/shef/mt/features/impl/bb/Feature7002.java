package shef.mt.features.impl.bb;

import java.util.ArrayList;
import shef.mt.features.impl.Feature;
import shef.mt.features.util.Sentence;

public class Feature7002 extends Feature {
    
    public Feature7002() {
        this.setIndex(7002);
        this.setDescription("Complex word count of target sentence");
        this.addResource("target.simplewords");
    }
    
    @Override
    public void run(Sentence source, Sentence target) {
        ArrayList<String> simpleWords =
            (ArrayList<String>) target.getValue("simplewords");
        String[] tokens = target.getTokens();
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
