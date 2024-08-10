package com;

import java.util.ArrayList;

public class MatrixClock {
	// Replica identifier
	private ArrayList<VectorClock> mclock;
	private int size;
	private VectorClock minVC;

	/*
	 * Constructor
	 */
	public MatrixClock(int num) {
		this.size = num + 1;
		this.mclock = new ArrayList<VectorClock>();
		this.minVC = new VectorClock(this.size);
		for (int i = 0; i < this.size; i++) {
			VectorClock vc = new VectorClock(this.size);
			this.mclock.add(vc);
		}
	}

	public VectorClock getMinVClock() {
		int min = 0;
		for (int col = 1; col < this.size; col++) {
			min = mclock.get(1).get(col);
			for (int row = 2; row < this.size; row++) {
				int seq = mclock.get(row).get(col);
				if (seq < min) {
					min = seq;
				}
			}
			minVC.set(col, min);
		}
		return this.minVC;
	}

	public VectorClock getVClock(int sid) {
		return this.mclock.get(sid);
	}

	public void updateSiteVClock(int sid, int sid2) {
		VectorClock vclock = this.mclock.get(sid);
		int seq = vclock.get(sid2);
		vclock.set(sid2, seq + 1);
	}

	public void updateSiteVClock(int sid, VectorClock vc) {
		VectorClock local = this.mclock.get(sid);
		for (int i = 1; i < this.size; i++) {
			local.set(i, vc.get(i));
		}
	}
}