import java.util.ArrayList;
import java.util.HashSet;


public class Scheduler {
	ArrayList<String> urlQ;
	ArrayList<String> SeenUrl;
	
	public Scheduler() {
		this.urlQ = new ArrayList<>();
		this.SeenUrl = new ArrayList<>();
		setURLs();
	}
	
	
	public String getNextUrl(){
		if (urlQ.size() > 0)
			return urlQ.remove(0);
		else
			return "";
	}
	
	public void addUrl(String url){
		if (SeenUrl.contains(url))
			return;
		urlQ.add(url);

	}
	
	public void addUrl(ArrayList<String> urls) {
		for (int i = 0; i < urls.size(); i++) {
			addUrl(urls.get(i));
		}
	}
	
	private void setURLs() {
		
		urlQ.add("https://www.researchgate.net/publication/285458515_A_General_Framework_for_Constrained_Bayesian_Optimization_using_Information-based_Search");
		urlQ.add("https://www.researchgate.net/publication/284579255_Parallel_Predictive_Entropy_Search_for_Batch_Global_Optimization_of_Expensive_Objective_Functions");
		urlQ.add("https://www.researchgate.net/publication/283658712_Sandwiching_the_marginal_likelihood_using_bidirectional_Monte_Carlo");
		urlQ.add("https://www.researchgate.net/publication/281895707_Dirichlet_Fragmentation_Processes");
		urlQ.add("https://www.researchgate.net/publication/273488773_Variational_Infinite_Hidden_Conditional_Random_Fields");
		urlQ.add("https://www.researchgate.net/publication/279633530_Subsampling-Based_Approximate_Monte_Carlo_for_Discrete_Distributions?ev=auth_pub");
		urlQ.add("https://www.researchgate.net/publication/279309917_An_Empirical_Study_of_Stochastic_Variational_Algorithms_for_the_Beta_Bernoulli_Process?ev=auth_pub");
		urlQ.add("https://www.researchgate.net/publication/278332447_MCMC_for_Variationally_Sparse_Gaussian_Processes");
		urlQ.add("https://www.researchgate.net/publication/278048012_Neural_Adaptive_Sequential_Monte_Carlo");
		urlQ.add("https://www.researchgate.net/publication/277959103_Dropout_as_a_Bayesian_Approximation_Appendix?ev=auth_pub");
		
	}
	
	
}
