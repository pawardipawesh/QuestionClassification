import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

public class FeatureExtractor {

	static String	pathToPosModel="path.pos.model";
	static String	pathToChunkerModel="path.chunker.model";
	static String	pathToNERDateModel="path.ner.date.model";
	static String	pathToNERTimeModel="path.ner.time.model";
	static String	pathToNERPersonModel="path.ner.person.model";
	static String	pathToNERLocationModel="path.ner.location.model";
	static String	pathToNEROrganizationModel="path.ner.organization.model";
	public static void main(String[] args) {
	
		BufferedReader inputFile=null;
		BufferedWriter htmlFile=null;
		BufferedWriter trainingFile=null;
	try {
			
			POSModel posTagModel = new POSModelLoader().load(new File(Configuration.getProperty(pathToPosModel)));
			POSTaggerME tagger = new POSTaggerME(posTagModel);
			InputStream is = new FileInputStream(Configuration.getProperty(pathToChunkerModel));
			ChunkerModel chunkingModel = new ChunkerModel(is);
			/*InputStream nerTimeModelFile = new FileInputStream(Configuration.getProperty(pathToNERTimeModel));
			TokenNameFinderModel nerTimeModel=new TokenNameFinderModel(nerTimeModelFile);
			NameFinderME timeFinder=new NameFinderME(nerTimeModel);
			InputStream nerDateModelFile = new FileInputStream(Configuration.getProperty(pathToNERTimeModel));
			TokenNameFinderModel nerDateModel=new TokenNameFinderModel(nerDateModelFile);
			InputStream nerPersonModelFile = new FileInputStream(Configuration.getProperty(pathToNERPersonModel));
			TokenNameFinderModel nerPersonModel=new TokenNameFinderModel(nerPersonModelFile);
			InputStream nerLocationModelFile = new FileInputStream(Configuration.getProperty(pathToNERLocationModel));
			TokenNameFinderModel nerLocationModel=new TokenNameFinderModel(nerLocationModelFile);
			InputStream nerOrganizationModelFile = new FileInputStream(Configuration.getProperty(pathToNEROrganizationModel));
			TokenNameFinderModel nerOrganizationModel=new TokenNameFinderModel(nerOrganizationModelFile);
			NameFinderME dateFinder=new NameFinderME(nerDateModel);
			NameFinderME personFinder=new NameFinderME(nerPersonModel);
			NameFinderME locationFinder=new NameFinderME(nerLocationModel);
			NameFinderME organizationFinder=new NameFinderME(nerOrganizationModel);
			*///D:\\jobs\\QuestionCategorization\\LabelledData.txt
			//D:\\jobs\\QuestionCategorization\\chunks.html
			inputFile=new BufferedReader(new FileReader(new File(args[0])));
			//htmlFile=new BufferedWriter(new FileWriter(new File(args[1])));
			trainingFile=new BufferedWriter(new FileWriter(new File(args[1])));
			
			String line,htmlString="";
			//prepareHTMLHeader(htmlFile);
			prepareTrainingFileHeader(trainingFile);
			int counter=1;
			while((line=inputFile.readLine())!=null){
				
				
				String lineContent[]=line.split(",,,");
				Vector<NLChunk> chunks=new Vector<NLChunk>();
				Vector<NLWord> words=new Vector<NLWord>();
				extractFeatures(tagger,chunkingModel,lineContent[0],words,chunks);
				//System.out.println(chunks.toString());
				htmlString=htmlString+"<p>\n";
				htmlString=htmlString+"<div class=\"tooltip\"><font color=red>"+counter+" :: "+lineContent[0]+"</font>\n\t<span class=\"tooltiptext\">"+chunks.toString()+"</span>\n</div>\n";
				String sentenceElements[]=lineContent[0].split("\\s+");
				String chunkSequenceString=getChunkSequence(chunks);
				if(chunks.size()<1){
					System.out.println("Hi");
				}
				String firstPhrase=chunks.get(0).getChunkString();
				String lastPhrase=chunks.get(chunks.size()-1).getChunkString();
				String posTagSequence=getPosTagSequence(words);
				/*Span timeSpans[]=timeFinder.find(sentenceElements);
				Span dateSpans[]=dateFinder.find(sentenceElements);
				Span personSpans[]=personFinder.find(sentenceElements);
				Span locationSpans[]=locationFinder.find(sentenceElements);
				Span organizationSpans[]=organizationFinder.find(sentenceElements);*/
				
				//boolean isTimePresentInSentence=(hasTimeElements(sentenceElements,words));
				int howManyVPs=getNoOfVps(chunkSequenceString);
				boolean isPPPresent=chunkSequenceString.contains(" PP ");
				boolean stratsWithADVP=chunkSequenceString.startsWith("ADVP ");
				boolean startsWithVerb=(posTagSequence.startsWith("VB")||posTagSequence.startsWith("MD "))?true:false;
				String firstWord="'"+words.get(0).getWord().replaceAll("'|\\.", "")+"'";
				String firstPos=words.get(0).getPos();
				String firstChunkType=chunks.get(0).getChunkType();
				String lastChunkType=chunks.get(chunks.size()-1).getChunkType();
				//boolean isNamedEntityPresent=(personSpans.length>0||locationSpans.length>0||organizationSpans.length>0)?true:false;
				//if(isNamedEntityPresent)System.out.println("Sentence: "+lineContent[0]);
				
				String strfeatures[]={firstWord.replaceAll("'", ""),"'"+posTagSequence+"'","'"+chunkSequenceString+"'"};
				int numericFeatures[]={howManyVPs};
				boolean biNominalFeatures[]={isPPPresent,stratsWithADVP,startsWithVerb};
				printTrainingFileContent(strfeatures,numericFeatures,biNominalFeatures,firstPos,firstChunkType,lastChunkType,lineContent[1].trim(),trainingFile);
				
				counter++;
			}
			//htmlFile.write(htmlString);
			//htmlFile.write("</body>\n</html>");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try{
				inputFile.close();
				//htmlFile.close();
				trainingFile.close();
			}catch(IOException ie){
				System.out.println("Error in file closing"+ie);
			}
		}
	}
	private static void printTrainingFileContent(String[] strfeatures, int[] numericFeatures,
			boolean[] biNominalFeatures, String firstPos, String firstChunkType, String lastChunkType, String className, BufferedWriter trainingFile) {
		try{
			String finalFeatureString="";
			for(String s:strfeatures){
				
				finalFeatureString+=s+",";
			}
			
			for(int i:numericFeatures){
				finalFeatureString+=i+",";
			}
			
			for(boolean b:biNominalFeatures){
				
				if(b)finalFeatureString+=1+",";
				else finalFeatureString+=0+",";
			}
			
			finalFeatureString+=firstPos+","+firstChunkType+","+lastChunkType+","+className;
			
			trainingFile.write(finalFeatureString+"\n");
			
		}catch(IOException ie){
			
			System.out.println("Error in writing to training file"+ie);
		}
		
		
	}
	private static int getNoOfVps(String chunkSequenceString) {
		
		String vpRegex=" VP ";
		Pattern p=Pattern.compile(vpRegex,Pattern.DOTALL);
		Matcher m=p.matcher(chunkSequenceString);
		int count=0;
		
		while(m.find()){
			count++;
			
		}
		return count;
	}
	
	private static boolean hasTimeElements(String[] sentenceElements, Vector<NLWord> words) {
		
		String timeRegex="time|year|month|day|morning|evening|afternoon|tomorrow|yesterday|january|jan|february|feb|march|mar|april|"
				+ "may|june|july|august|sep|september|october|november|nov|december|sun|sunday|mon|monday|tuesday|wednesday|friday|thrusday|saturday|week|winter|summer|rainy|tonight|midnight";
		
		for(int i=0;i<sentenceElements.length;i++){
			
			if(i-2 < 0)continue;
			boolean previousToPreviousWordIsPrep=words.get(i-2).getPos().equals("IN");
			boolean previousWordIsWP=words.get(i-2).getPos().equals("WP");
			if(sentenceElements[i].toLowerCase().matches(timeRegex) && previousWordIsWP && previousToPreviousWordIsPrep)return true;
		}
		
		return false;
	}
	private static String getPosTagSequence(Vector<NLWord> words) {
		
		String posTagSequence="";
		for(NLWord word:words){
			
			posTagSequence+=word.getPos()+" ";
		}
		return posTagSequence.trim().replaceAll("'|,|\\.", "");
		
	}
	private static String getChunkSequence(Vector<NLChunk> chunks) {
		
		String chunkSequence="";
		for(NLChunk chunk:chunks){
			
			chunkSequence+=chunk.getChunkType()+" ";
		}
		return chunkSequence.trim().replaceAll("'|,|\\.", "");
		
	}
	private static void prepareTrainingFileHeader(BufferedWriter trainingFile) {
		
		try {
			
			trainingFile.write("@relation questionClassification\n\n");
			trainingFile.write("@attribute firstWord String\n");
			trainingFile.write("@attribute posTagSequence String\n");
			trainingFile.write("@attribute chunkSequence String\n");
			trainingFile.write("@attribute howManyVps Numeric \n");
			trainingFile.write("@attribute isPPPresent {0,1}\n");
			trainingFile.write("@attribute stratsWithADVP {0,1}\n");
			trainingFile.write("@attribute startsWithVerb {0,1}\n");
			
			trainingFile.write("@attribute firstWordPOS {CC,CD,DT,EX,FW,IN,JJ,JJR,JJS,LS,MD,NN,NNS,NNP,NNPS,PDT,POS,PRP,PRP$,RB,RBR,RBS,RP,SYM,TO,UH,VB,VBG,VBD,VBN,VBP,VBZ,WDT,WP,WP$,WRB}\n");
			trainingFile.write("@attribute firstChunkType {NP,VP,ADVP,PP,ADJP,SBAR,PRT,INTJ}\n");
			trainingFile.write("@attribute lastChunkType {NP,VP,ADVP,PP,ADJP,SBAR,PRT,INTJ}\n");
			trainingFile.write("@attribute class {unknown, who, when, what, affirmation}\n\n");
			trainingFile.write("@data\n");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void prepareHTMLHeader(BufferedWriter htmlFile) {
		
		String htmlString=new String();
		
		htmlString=htmlString+"<html>\n<style>\n";
		htmlString=htmlString+".tooltip{\n\tposition: relative;\n\tdisplay:inline-block;\n}\n";
		htmlString=htmlString+".tooltip .tooltiptext {\n\tvisibility:hidden;\n\twidth:relative;\n\ttext-align:relative;\n\tbackground-color:#555;\n\tcolor:#fff;\n\tborder-radius:relative;\n\tpadding:relative;\n\tposition:absolute;\n\tz-index=1;\n\tbottom:relative;\n\tright:relative;\n\tleft:relative;\n\ttop:relative;\n\tmargin-left:relative;\n\topacity:0;\n\ttransition:opacity 1s;\n}\n";	
		htmlString=htmlString+".tooltip .tooltiptext::after {\n\tcontent: \"\";\n\tposition:absolute; \n\ttop:100%;\n\tleft:100%\n\t margin-left: -5px;\n\tborder-width: 5px;\n\tborder-style:solid;\n\tborder-color: #555 transparent transparent;\n }\n";
		htmlString=htmlString+".tooltip:hover .tooltiptext {\n\tvisibility:visible;\n\t opacity:1;\n}\n";
		htmlString=htmlString+"</style>\n<body style=\"text-align:left;\">\n";
		
		try {
			htmlFile.write(htmlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void extractFeatures(POSTaggerME tagger, ChunkerModel chunkingModel, String sentence, Vector<NLWord> words, Vector<NLChunk> chunks) throws IOException {
		
		String whitespaceTokenizerLine[] = null;
		String[] tags = null;
		
		whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
		tags = tagger.tag(whitespaceTokenizerLine);
 
		POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
		words.addAll(getWords(sample));
		chunks.addAll(getChunks(chunkingModel,whitespaceTokenizerLine, tags,words));
		
	}
	
	private static Vector<NLChunk> getChunks(ChunkerModel cModel, String[] whitespaceTokenizerLine, String[] tags, Vector<NLWord> nlwords) {
		
		Vector<NLChunk> nlchunks=new Vector<NLChunk>();
		ChunkerME chunkerME = new ChunkerME(cModel);
		Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
		int chunkIndex=0;
		for (Span s : span){
			
			Vector<NLWord> chunkWords=new Vector<NLWord>();
			String chunkType=s.getType();
			int startIndex=s.getStart();
			int endIndex=s.getEnd();
			String chunkString="";
			
			for(int i=startIndex;i<endIndex;i++){
				
				NLWord ithWord=nlwords.get(i);
				chunkWords.add(ithWord);
				chunkString=chunkString+ithWord.getWord().trim()+" ";
			}
			
			NLChunk nlc=new NLChunk(chunkString.trim(), chunkWords, startIndex, endIndex, chunkType, chunkIndex);
			nlchunks.add(nlc);
			chunkIndex++;
		}
			
		return nlchunks;
	}
	private static Vector<NLWord> getWords(POSSample sample) {
		
		Vector<NLWord> nlwords=new Vector<NLWord>();
		String word_tags[]=sample.toString().split("\\s+");
		int wordIndex=0;
		
		for(String word_tag:word_tags){
			
			int splitAt=word_tag.lastIndexOf("_");
			String word=word_tag.substring(0,splitAt);
			String tag=word_tag.substring(splitAt+1);
			NLWord nlw=new NLWord(word, tag, wordIndex);
			nlwords.add(nlw);
			wordIndex++;
		}
		
		return nlwords;
		
	}

}
