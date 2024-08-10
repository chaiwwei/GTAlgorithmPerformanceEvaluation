package com;

public abstract class GTAlgorithm {
	// Replica identifier
	protected int sid;
	protected MatrixClock mclock;

	/*
	 * Constructor
	 */
	public GTAlgorithm(int sid, int num) {
		this.sid = sid;
		this.mclock = new MatrixClock(num);
	}

	public abstract CharOperation ROH(String aop);

	public abstract String LOH(CharOperation op);

	protected void localTimeUpdate() {
		this.mclock.updateSiteVClock(this.sid, this.sid);
	}

	protected void remoteTimeUpdate(int sid, VectorClock vclock) {
		this.mclock.updateSiteVClock(sid, vclock);
		this.mclock.updateSiteVClock(this.sid, sid);
	}

	public VectorClock getVClock() {
		return this.mclock.getVClock(this.sid);
	}

	public VectorClock getNewVClock() {
		return this.mclock.getVClock(this.sid).clone();
	}

	public int getSeq(int sid) {
		VectorClock vclock = this.mclock.getVClock(sid);
		return vclock.get(sid);
	}

	public abstract int memorySize();

	public void initialize(int sid, int num, String content) {
		this.sid = sid;
		this.mclock = new MatrixClock(num);
		this.initializeInternalState(content);
	}

	public abstract void initializeInternalState(String content);
	
	public int getSid()
	{
		return this.sid;
	}
}