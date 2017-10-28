
package shef.mt.features.impl.wce;

import edu.stanford.nlp.util.ArrayUtils;
import shef.mt.features.impl.WordLevelFeature;
import shef.mt.features.util.Sentence;

public class WordLevelFeature1002 extends WordLevelFeature{

    public WordLevelFeature1002() {
        this.setIndex("WCE1002");
        this.setIdentifier("TRGBIGRAMLEFT");
        this.setDescription("Bigram at left of target word.");
    }

    @Override
    public void run(Sentence source, Sentence target) {
        //Create vector of resulting values:
        String[] result = new String[target.getNoTokens()];
        
        //Ge tokens from target sentence:
        String[] tokens = target.getTokens();
        
        //For each token, create ngram:
        for(int i=0; i<tokens.length; i++){
            String ngram = this.getNgram(tokens, i, 1, 0);
            String value = this.getIdentifier()+'='+ngram;
            result[i] = value;
        }
        
        //Save values produced:
        this.setValues(result);
    }

    private String getNgram(String[] tokens, int i, int leftSize, int rightSize) {
        //Setup filler vector:
        String[] fillers = new String[]{"<s>", "<s>", "<s>"};
        
        //Create token vector with fillers at beggining and end:
        String[] allTokens = ArrayUtils.concatenate(fillers, tokens);
        allTokens = ArrayUtils.concatenate(allTokens, fillers);
        
        //Adjust word index:
        int index = i+3;
        
        //Create ngram:
        String ngram = "";
        for(int j=index-leftSize; j<=index+rightSize; j++){
            ngram += allTokens[j]+"/";
        }
        
        //Return ngram:
        return ngram;
    }

}
