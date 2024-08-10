package com;

public class Parameters {
	public int siteOpNum;
	public int ratio;
	public int iniSize;
	public String algName;

	public Parameters(int siteOpNum, int ratio, int iniSize, String algName) {
		this.siteOpNum = siteOpNum;
		this.algName = algName;
		this.ratio = ratio;
		this.iniSize = iniSize;
	}

	public String toString() {
		return "" + algName + "_R_" + siteOpNum + "_P_" + ratio + "_Sini_" + iniSize;
	}
}