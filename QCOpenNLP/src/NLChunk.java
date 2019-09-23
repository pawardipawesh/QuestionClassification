import java.util.Vector;

public class NLChunk {

	private String chunkString;
	private Vector<NLWord> chunkWords;
	int beginIndex;
	int endIndex;
	String chunkType;
	int chunkIndex;
	
	public NLChunk(String chunkString, Vector<NLWord> chunkWords, int beginIndex, int endIndex, String chunkType,
			int chunkIndex) {
		
		super();
		this.chunkString = chunkString;
		this.chunkWords = chunkWords;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.chunkType = chunkType;
		this.chunkIndex = chunkIndex;
	}

	public String getChunkString() {
		return chunkString;
	}

	public void setChunkString(String chunkString) {
		this.chunkString = chunkString;
	}

	public Vector<NLWord> getChunkWords() {
		return chunkWords;
	}

	public void setChunkWords(Vector<NLWord> chunkWords) {
		this.chunkWords = chunkWords;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getChunkType() {
		return chunkType;
	}

	public void setChunkType(String chunkType) {
		this.chunkType = chunkType;
	}

	public int getChunkIndex() {
		return chunkIndex;
	}

	public void setChunkIndex(int chunkIndex) {
		this.chunkIndex = chunkIndex;
	}

	@Override
	public String toString() {
		return "[" + chunkString + ", " + chunkType + ", " + beginIndex
				+ ", " + endIndex +  ", " + chunkIndex + "]";
	}
	
}
