package chatsegmenter.parser.model;

import java.util.ArrayList;

/**
 * Class used for storing important information for each participant in a chat
 * 
 * @author Mihai Dascalu
 * 
 */

public class Participant {

	private int id;

	private String name;

	private ArrayList<Utterance> utteranceList;

	// attributes with p in front of name represent the percentage from the
	// overall chat evaluation

	private int noChars;

	private double pNoChars;

	private double noCharsPerUtterance, pNoCharsPerUtterance;

	// social network metrics
	private double inDegree, inDegreeUtteranceGrade, pInDegree,
			pInDegreeUtteranceGrade;

	private double outDegree, outDegreeUtteranceGrade, pOutDegree,
			pOutDegreeUtteranceGrade;

	private double rank, rankUtteranceGrade, pRank, pRankUtteranceGrade;

	private double eigen, eigenUtteranceGrade, pEigen, pEigenUtteranceGrade;

	private double closenessCentrality, closenessCentralityUtteranceGrade,
			pClosenessCentrality, pClosenessCentralityUtteranceGrade;

	private double graphCentrality, graphCentralityUtteranceGrade,
			pGraphCentrality, pGraphCentralityUtteranceGrade;

	private double finalGrade;

	private int roundedFinalGrade;

	public Participant(int id, String name) {
		this.id = id;
		this.name = name;
		utteranceList = new ArrayList<Utterance>();
	}

	public void addUtterance(Utterance u) {
		utteranceList.add(u);
		noChars += u.getText().length();
	}

	public boolean searchUtterance(int index) {
		return utteranceList.contains(new Utterance(index));
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the utteranceList
	 */
	public ArrayList<Utterance> getUtteranceList() {
		return utteranceList;
	}

	/**
	 * @param utteranceList
	 *            the utteranceList to set
	 */
	public void setUtteranceList(ArrayList<Utterance> utteranceList) {
		this.utteranceList = utteranceList;
	}

	/**
	 * @return the noChars
	 */
	public int getNoChars() {
		return noChars;
	}

	/**
	 * @param noChars
	 *            the noChars to set
	 */
	public void setNoChars(int noChars) {
		this.noChars = noChars;
	}

	/**
	 * @return the pNoChars
	 */
	public double getPNoChars() {
		return pNoChars;
	}

	/**
	 * @param noChars
	 *            the pNoChars to set
	 */
	public void setPNoChars(double noChars) {
		pNoChars = noChars;
	}

	/**
	 * @return the noCharsPerUtterance
	 */
	public double getNoCharsPerUtterance() {
		return noCharsPerUtterance;
	}

	/**
	 * @param noCharsPerUtterance
	 *            the noCharsPerUtterance to set
	 */
	public void setNoCharsPerUtterance(double noCharsPerUtterance) {
		this.noCharsPerUtterance = noCharsPerUtterance;
	}

	/**
	 * @return the pNoCharsPerUtterance
	 */
	public double getPNoCharsPerUtterance() {
		return pNoCharsPerUtterance;
	}

	/**
	 * @param noCharsPerUtterance
	 *            the pNoCharsPerUtterance to set
	 */
	public void setPNoCharsPerUtterance(double noCharsPerUtterance) {
		pNoCharsPerUtterance = noCharsPerUtterance;
	}

	/**
	 * @return the inDegree
	 */
	public double getInDegree() {
		return inDegree;
	}

	/**
	 * @param inDegree
	 *            the inDegree to set
	 */
	public void setInDegree(double inDegree) {
		this.inDegree = inDegree;
	}

	/**
	 * @return the inDegreeUtteranceGrade
	 */
	public double getInDegreeUtteranceGrade() {
		return inDegreeUtteranceGrade;
	}

	/**
	 * @param inDegreeUtteranceGrade
	 *            the inDegreeUtteranceGrade to set
	 */
	public void setInDegreeUtteranceGrade(double inDegreeUtteranceGrade) {
		this.inDegreeUtteranceGrade = inDegreeUtteranceGrade;
	}

	/**
	 * @return the pInDegree
	 */
	public double getPInDegree() {
		return pInDegree;
	}

	/**
	 * @param inDegree
	 *            the pInDegree to set
	 */
	public void setPInDegree(double inDegree) {
		pInDegree = inDegree;
	}

	/**
	 * @return the pInDegreeUtteranceGrade
	 */
	public double getPInDegreeUtteranceGrade() {
		return pInDegreeUtteranceGrade;
	}

	/**
	 * @param inDegreeUtteranceGrade
	 *            the pInDegreeUtteranceGrade to set
	 */
	public void setPInDegreeUtteranceGrade(double inDegreeUtteranceGrade) {
		pInDegreeUtteranceGrade = inDegreeUtteranceGrade;
	}

	/**
	 * @return the outDegree
	 */
	public double getOutDegree() {
		return outDegree;
	}

	/**
	 * @param outDegree
	 *            the outDegree to set
	 */
	public void setOutDegree(double outDegree) {
		this.outDegree = outDegree;
	}

	/**
	 * @return the outDegreeUtteranceGrade
	 */
	public double getOutDegreeUtteranceGrade() {
		return outDegreeUtteranceGrade;
	}

	/**
	 * @param outDegreeUtteranceGrade
	 *            the outDegreeUtteranceGrade to set
	 */
	public void setOutDegreeUtteranceGrade(double outDegreeUtteranceGrade) {
		this.outDegreeUtteranceGrade = outDegreeUtteranceGrade;
	}

	/**
	 * @return the pOutDegree
	 */
	public double getPOutDegree() {
		return pOutDegree;
	}

	/**
	 * @param outDegree
	 *            the pOutDegree to set
	 */
	public void setPOutDegree(double outDegree) {
		pOutDegree = outDegree;
	}

	/**
	 * @return the pOutDegreeUtteranceGrade
	 */
	public double getPOutDegreeUtteranceGrade() {
		return pOutDegreeUtteranceGrade;
	}

	/**
	 * @param outDegreeUtteranceGrade
	 *            the pOutDegreeUtteranceGrade to set
	 */
	public void setPOutDegreeUtteranceGrade(double outDegreeUtteranceGrade) {
		pOutDegreeUtteranceGrade = outDegreeUtteranceGrade;
	}

	/**
	 * @return the rank
	 */
	public double getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(double rank) {
		this.rank = rank;
	}

	/**
	 * @return the rankUtteranceGrade
	 */
	public double getRankUtteranceGrade() {
		return rankUtteranceGrade;
	}

	/**
	 * @param rankUtteranceGrade
	 *            the rankUtteranceGrade to set
	 */
	public void setRankUtteranceGrade(double rankUtteranceGrade) {
		this.rankUtteranceGrade = rankUtteranceGrade;
	}

	/**
	 * @return the pRank
	 */
	public double getPRank() {
		return pRank;
	}

	/**
	 * @param rank
	 *            the pRank to set
	 */
	public void setPRank(double rank) {
		pRank = rank;
	}

	/**
	 * @return the pRankUtteranceGrade
	 */
	public double getPRankUtteranceGrade() {
		return pRankUtteranceGrade;
	}

	/**
	 * @param rankUtteranceGrade
	 *            the pRankUtteranceGrade to set
	 */
	public void setPRankUtteranceGrade(double rankUtteranceGrade) {
		pRankUtteranceGrade = rankUtteranceGrade;
	}

	/**
	 * @return the eigen
	 */
	public double getEigen() {
		return eigen;
	}

	/**
	 * @param eigen
	 *            the eigen to set
	 */
	public void setEigen(double eigen) {
		this.eigen = eigen;
	}

	/**
	 * @return the eigenUtteranceGrade
	 */
	public double getEigenUtteranceGrade() {
		return eigenUtteranceGrade;
	}

	/**
	 * @param eigenUtteranceGrade
	 *            the eigenUtteranceGrade to set
	 */
	public void setEigenUtteranceGrade(double eigenUtteranceGrade) {
		this.eigenUtteranceGrade = eigenUtteranceGrade;
	}

	/**
	 * @return the pEigen
	 */
	public double getPEigen() {
		return pEigen;
	}

	/**
	 * @param eigen
	 *            the pEigen to set
	 */
	public void setPEigen(double eigen) {
		pEigen = eigen;
	}

	/**
	 * @return the pEigenUtteranceGrade
	 */
	public double getPEigenUtteranceGrade() {
		return pEigenUtteranceGrade;
	}

	/**
	 * @param eigenUtteranceGrade
	 *            the pEigenUtteranceGrade to set
	 */
	public void setPEigenUtteranceGrade(double eigenUtteranceGrade) {
		pEigenUtteranceGrade = eigenUtteranceGrade;
	}

	/**
	 * @return the closenessCentrality
	 */
	public double getClosenessCentrality() {
		return closenessCentrality;
	}

	/**
	 * @param closenessCentrality
	 *            the closenessCentrality to set
	 */
	public void setClosenessCentrality(double closenessCentrality) {
		this.closenessCentrality = closenessCentrality;
	}

	/**
	 * @return the closenessCentralityUtteranceGrade
	 */
	public double getClosenessCentralityUtteranceGrade() {
		return closenessCentralityUtteranceGrade;
	}

	/**
	 * @param closenessCentralityUtteranceGrade
	 *            the closenessCentralityUtteranceGrade to set
	 */
	public void setClosenessCentralityUtteranceGrade(
			double closenessCentralityUtteranceGrade) {
		this.closenessCentralityUtteranceGrade = closenessCentralityUtteranceGrade;
	}

	/**
	 * @return the pClosenessCentrality
	 */
	public double getPClosenessCentrality() {
		return pClosenessCentrality;
	}

	/**
	 * @param closenessCentrality
	 *            the pClosenessCentrality to set
	 */
	public void setPClosenessCentrality(double closenessCentrality) {
		pClosenessCentrality = closenessCentrality;
	}

	/**
	 * @return the pClosenessCentralityUtteranceGrade
	 */
	public double getPClosenessCentralityUtteranceGrade() {
		return pClosenessCentralityUtteranceGrade;
	}

	/**
	 * @param closenessCentralityUtteranceGrade
	 *            the pClosenessCentralityUtteranceGrade to set
	 */
	public void setPClosenessCentralityUtteranceGrade(
			double closenessCentralityUtteranceGrade) {
		pClosenessCentralityUtteranceGrade = closenessCentralityUtteranceGrade;
	}

	/**
	 * @return the graphCentrality
	 */
	public double getGraphCentrality() {
		return graphCentrality;
	}

	/**
	 * @param graphCentrality
	 *            the graphCentrality to set
	 */
	public void setGraphCentrality(double graphCentrality) {
		this.graphCentrality = graphCentrality;
	}

	/**
	 * @return the graphCentralityUtteranceGrade
	 */
	public double getGraphCentralityUtteranceGrade() {
		return graphCentralityUtteranceGrade;
	}

	/**
	 * @param graphCentralityUtteranceGrade
	 *            the graphCentralityUtteranceGrade to set
	 */
	public void setGraphCentralityUtteranceGrade(
			double graphCentralityUtteranceGrade) {
		this.graphCentralityUtteranceGrade = graphCentralityUtteranceGrade;
	}

	/**
	 * @return the pGraphCentrality
	 */
	public double getPGraphCentrality() {
		return pGraphCentrality;
	}

	/**
	 * @param graphCentrality
	 *            the pGraphCentrality to set
	 */
	public void setPGraphCentrality(double graphCentrality) {
		pGraphCentrality = graphCentrality;
	}

	/**
	 * @return the pGraphCentralityUtteranceGrade
	 */
	public double getPGraphCentralityUtteranceGrade() {
		return pGraphCentralityUtteranceGrade;
	}

	/**
	 * @param graphCentralityUtteranceGrade
	 *            the pGraphCentralityUtteranceGrade to set
	 */
	public void setPGraphCentralityUtteranceGrade(
			double graphCentralityUtteranceGrade) {
		pGraphCentralityUtteranceGrade = graphCentralityUtteranceGrade;
	}

	/**
	 * @return the finalGrade
	 */
	public double getFinalGrade() {
		return finalGrade;
	}

	/**
	 * @param finalGrade
	 *            the finalGrade to set
	 */
	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * @return the roundedFinalGrade
	 */
	public int getRoundedFinalGrade() {
		return roundedFinalGrade;
	}

	/**
	 * @param roundedFinalGrade
	 *            the roundedFinalGrade to set
	 */
	public void setRoundedFinalGrade(int roundedFinalGrade) {
		this.roundedFinalGrade = roundedFinalGrade;
	}

	public boolean equals(Object o)
	{
		Participant p = (Participant)o;
		if (this.getId() == p.getId()) return true;
		else return false;
	}
}
