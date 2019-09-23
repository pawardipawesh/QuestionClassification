
public class Features {

	String stringFeatures[];
	int numericFeatures[];
	boolean biNominalFeatures[];
	
	
	public Features(String[] stringFeatures, int[] numericFeatures, boolean[] biNominalFeatures) {
		super();
		this.stringFeatures = stringFeatures;
		this.numericFeatures = numericFeatures;
		this.biNominalFeatures = biNominalFeatures;
	}
	
	public String[] getStringFeatures() {
		return stringFeatures;
	}
	public void setStringFeatures(String[] stringFeatures) {
		this.stringFeatures = stringFeatures;
	}
	public int[] getNumericFeatures() {
		return numericFeatures;
	}
	public void setNumericFeatures(int[] numericFeatures) {
		this.numericFeatures = numericFeatures;
	}
	public boolean[] getBiNominalFeatures() {
		return biNominalFeatures;
	}
	public void setBiNominalFeatures(boolean[] biNominalFeatures) {
		this.biNominalFeatures = biNominalFeatures;
	}
	
	
			
}
