import com.vividsolutions.jts.awt.PointShapeFactory.Square;



public class Clusterer {
	final static int DOC_NUMBER = 1000;
	final static int MAX_K = 10;
	MyVector[][] means = new MyVector[MAX_K + 1][MAX_K];
	MyVector[] docs = new MyVector[DOC_NUMBER];
	int[][] clusters = new int[MAX_K + 1][DOC_NUMBER];
	
	
	public float residualSumOfSquares(int k) {
		float residue = 0;
		for(int i = 0; i < DOC_NUMBER; i++) {
			int index = clusters[k][i];
			residue += MyVector.squareDistance(docs[i], means[k][index]);
		}
		
		return residue;
	}
	
	public void KMeans(int k) {
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < DOC_NUMBER; i++) {
				int newClass = findNearestMeans(docs[i], k);
				clusters[k][i] = newClass;
			}
			setMeans(k);
		}
	}
	
	private void initialMeans() {
		for(int i = 2; i <= MAX_K; i++) {
			for(int j = 0; j < i; j++) {
				int rand = (int) Math.ceil(Math.random() * DOC_NUMBER); 
				means[i][j] = docs[rand];
			}
		}
	}
	
	private int findNearestMeans(MyVector v, int k) {
		int nearestIndex = 0;
		float min = Float.MAX_VALUE;
		for(int i = 0; i < k; i++) {
			float dist = MyVector.similarityDistance(v, means[k][i]);
			if (dist < min) {
				min = dist;
				nearestIndex = i; 
			}
		}
		
		return nearestIndex;
	}
	
	private void setMeans(int k) {
		MyVector[] tmp  = new MyVector[MAX_K];
		int[] num = new int[MAX_K];
		for (int i = 0; i < DOC_NUMBER; i++) {
			int cluster = clusters[k][i];
			tmp[cluster] = MyVector.add(tmp[cluster], docs[i]);
			num[cluster]++;
		}
		
		means[k] = MyVector.divide(tmp, num);
	}
}
