package chatsegmenter.parser.model;

/**
 * Class used for storing the weight for each factor used in the overall
 * evaluation
 * 
 * @author Mihai Dascalu
 * 
 */

public class Weights {

	private Double noChars;

	private Double noCharsUtterance;

	private Double inDegreeNo;

	private Double outDegreeNo;

	private Double rankNo;

	private Double eigenNo;

	private Double inDegreeMark;

	private Double outDegreeMark;

	private Double rankMark;

	private Double eigenMark;

	public Double getNoChars() {
		return noChars;
	}

	public void setNoChars(Double nrChars) {
		this.noChars = nrChars;
	}

	public Double getNoCharsUtterance() {
		return noCharsUtterance;
	}

	public void setNoCharsUtterance(Double nrCharsUtterance) {
		this.noCharsUtterance = nrCharsUtterance;
	}

	public Double getInDegreeNo() {
		return inDegreeNo;
	}

	public void setInDegreeNo(Double inDegreeNo) {
		this.inDegreeNo = inDegreeNo;
	}

	public Double getOutDegreeNo() {
		return outDegreeNo;
	}

	public void setOutDegreeNo(Double outDegreeNo) {
		this.outDegreeNo = outDegreeNo;
	}

	public Double getRankNo() {
		return rankNo;
	}

	public void setRankNo(Double rankNo) {
		this.rankNo = rankNo;
	}

	public Double getEigenNo() {
		return eigenNo;
	}

	public void setEigenNo(Double eigenNo) {
		this.eigenNo = eigenNo;
	}

	public Double getInDegreeMark() {
		return inDegreeMark;
	}

	public void setInDegreeMark(Double inDegreeMark) {
		this.inDegreeMark = inDegreeMark;
	}

	public Double getOutDegreeMark() {
		return outDegreeMark;
	}

	public void setOutDegreeMark(Double outDegreeMark) {
		this.outDegreeMark = outDegreeMark;
	}

	public Double getRankMark() {
		return rankMark;
	}

	public void setRankMark(Double rankMark) {
		this.rankMark = rankMark;
	}

	public Double getEigenMark() {
		return eigenMark;
	}

	public void setEigenMark(Double eigenMark) {
		this.eigenMark = eigenMark;
	}

}
