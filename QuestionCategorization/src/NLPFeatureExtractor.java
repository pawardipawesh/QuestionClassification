import java.util.List;
import java.util.Vector;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;



public class NLPFeatureExtractor {

	public static void main(String[] args) {
		
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(
				PropertiesUtils.asProperties(
					"annotators", "tokenize,ssplit,pos",
					"ssplit.isOneSentence", "true",
					"parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz",
					"tokenize.language", "en"));

			// read some text in the text variable
			String text = "Dipawesh is a hard working student"; // Add your text here!
			Annotation document = new Annotation(text);

			// run all Annotators on this text
			pipeline.annotate(document);
			

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for(CoreMap sentence: sentences) {
				
				Vector<NLWord> words=new Vector<NLWord>();
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    
			    NLWord nlw=new NLWord(word, pos);
			    words.add(nlw);
			  }

			  System.out.println(words.toString());
			  
			}

			
	}
	
}
