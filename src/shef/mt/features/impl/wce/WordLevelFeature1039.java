package shef.mt.features.impl.wce;

import edu.stanford.nlp.util.ArrayUtils;
import java.util.ArrayList;
import shef.mt.features.impl.WordLevelFeature;
import shef.mt.features.util.Sentence;
import shef.mt.tools.LanguageModel;

public class WordLevelFeature1039 extends WordLevelFeature {

    public WordLevelFeature1039() {
        this.setIndex("WCE1039");
        this.setIdentifier("LTPNL");
        this.setDescription("Longest target POS n-gram length.");
        this.addResource("target.POSModel");
        this.addResource("target.posngram");
    }

    @Override
    public void run(Sentence source, Sentence target) {
        //Create vector of resulting values:
        String[] result = new String[target.getNoTokens()];

        //Get language model object:
        LanguageModel lm = (LanguageModel) target.getValue("posngramcount");

        //Ge tokens from target sentence:
        String[] targetTokens = target.getTokens();
        
        //Get pos tags or target sentence:
        ArrayList<String> targetPOSTags = (ArrayList<String>) target.getValue("postags");

        //Output word occurrences:
        for (int i = 0; i < targetTokens.length; i++) {
            //Get ngrams:
            String[] ngrams = this.getNgrams(targetPOSTags.toArray(new String[targetPOSTags.size()]), i);
            String w3w2w1w = ngrams[0];
            String w2w1w = ngrams[1];
            String w1w = ngrams[2];
            String w = ngrams[3];
            
            //Get ngram frequencies:
            Integer w3w2w1wi = lm.getFreq(w3w2w1w, 4);
            Integer w2w1wi = lm.getFreq(w2w1w, 3);
            Integer w1wi = lm.getFreq(w1w, 2);
            Integer wi = lm.getFreq(w, 1);
            
            //Estimate backoff value:
            int backoff;
            if(w3w2w1wi!=0){
                backoff = 4;
            }else if(w2w1wi!=0){
                backoff = 3;
            }else if(w1wi!=0){
                backoff = 2;
            }else if(wi!=0){
                backoff = 1;
            }else{
                backoff = 0;
            }
            
            //Create value:
            String value = this.getIdentifier() + "=" + backoff;

            //Save value:
            result[i] = value;
        }

        //Save values produced:
        this.setValues(result);
    }

    private String[] getNgrams(String[] tokens, int i) {
        //Create fillers:
        String[] fillers = new String[]{"<s>", "<s>", "<s>"};
        String[] allTokens = ArrayUtils.concatenate(fillers, tokens);
        
        //Ajust word index:
        int index = i + 3;
        
        //Create array of ngrams:
        String[] result = new String[4];
        result[0] = allTokens[index-3] + " " + allTokens[index-2] + " " + allTokens[index-1] + " " + allTokens[index];
        result[1] = allTokens[index-2] + " " + allTokens[index-1] + " " + allTokens[index];
        result[2] = allTokens[index-1] + " " + allTokens[index];
        result[3] = allTokens[index];
        //Return array of ngrams:
        return result;
    }
}
