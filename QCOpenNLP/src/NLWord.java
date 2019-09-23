
public class NLWord {

	private String word;
	private String pos;
	private int index;
	
	public NLWord(String word, String pos, int index) {
		super();
		this.word = word;
		this.pos = pos;
		this.index = index;
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
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "[" + word + ", " + pos + "]";
	}
	
	
}
