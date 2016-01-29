import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class PageRank {
	HashMap<Integer, Long> mappedToReal;
	HashMap<Long, Integer> realToMapped;
	int[][] A;

	public PageRank(paper[] papers, float alpha) {
		this.mappedToReal = new HashMap<>();
		this.realToMapped = new HashMap<>();
		CreateA(papers);
		RealMatrix m = modifyP(alpha);
		EigenDecomposition ed = new EigenDecomposition(m, 0);
		RealVector rv = ed.getEigenvector(0);
		printVector(rv);
		// add pageRank to each paper
		

	
	}

	public void printVector(RealVector v) {
		for (int i = 0; i < v.getDimension(); i++) {
			System.out.println(v.getEntry(i) + " ");
		}
	}

	// create probablity matrix
	public void CreateA(paper[] papers) {
		A = new int[papers.length][];

		for (int i = 0; i < A.length; i++) {
			A[i] = new int[papers.length];
			mappedToReal.put(i, papers[i].getId());
			realToMapped.put(papers[i].getId(), i);
		}

		for (int i = 0; i < A.length; i++) {
			ArrayList<Long> tmp = papers[i].getCitees();
			int from = realToMapped.get(papers[i].getId());
			for (int j = 0; j < tmp.size(); j++) {
				A[from][realToMapped.get(tmp.get(j))] = 1;
			}
		}
	}

	public RealMatrix modifyP(float alpha) {
		double[][] P = new double[A.length][];
		for (int i = 0; i < P.length; i++) {
			P[i] = new double[P.length];
			int degree = 0;
			// compute the degree of node
			for (int j = 0; j < P.length; j++) {
				degree += A[i][j];
			}

			if (degree == 0) {
				for (int j = 0; j < P.length; j++) {
					P[i][j] = 1 / P.length;
				}
			} else {
				for (int j = 0; j < P.length; j++) {
					P[i][j] = 1 / degree * (1 - alpha) + alpha / P.length;
				}
			}
		}

		RealMatrix c = new Array2DRowRealMatrix(P);
		return c;
	}

}

class paper {
	long id;
	ArrayList<Long> citee;
	double pageRankValue;

	public paper(long id) {
		this.id = id;
		this.citee = new ArrayList<Long>();
		this.pageRankValue = 0;
	}
	
	public void addCitee(long id){
		this.citee.add(id);
	}
	public long getId() {
		return id;
	}

	public ArrayList<Long> getCitees() {
		return this.citee;
	}

	public void setPageRank(double rank) {
		this.pageRankValue = rank;
	}

	public double getPageRank() {
		return this.pageRankValue;
	}
}