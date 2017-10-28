/**
 *
 */
package shef.mt.features.impl.doclevel;

import java.util.HashSet;
import shef.mt.features.impl.DocLevelFeature;
import shef.mt.features.util.Doc;
import shef.mt.features.util.Sentence;

/**
 * PCFG Parse log likelihood
 *
 * @author Eleftherios Avramidis
 */
public class DocLevelFeature9300 extends DocLevelFeature {
    
    private float logLikelihood;

    /* (non-Javadoc)
     * @see wlv.mt.features.impl.Feature#run(wlv.mt.features.util.Sentence, wlv.mt.features.util.Sentence)
     */
    public DocLevelFeature9300() {
        setIndex(9300);
        setDescription("Source PCFG Parse log-likelihood");
       // this.addResource("BParser");
         this.addResource("source.bparser.grammar");
    }

    public void run(Sentence source, Sentence target) {
        //   System.out.println((Float)source.getValue("bparser.loglikelihood"));
        

    }

    @Override
    public void run(Doc source, Doc target) {
        double likelihood = 0;
        for (int i=0;i<source.getSentences().size();i++){
            likelihood +=(double) source.getSentence(i).getValue("bparser.loglikelihood");
        }
        
        setValue(new Float((Double) likelihood/source.getSentences().size()));
    }

}
