
public class NLWord {

	private String word;
	private String	pos;
	
	public NLWord(String word, String pos) {
		super();
		this.word = word;
		this.pos = pos;
	}
	
	@Override
	public String toString() {
		return "NLWord [word=" + word + ", pos=" + pos + "]";
	}

	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	
}
