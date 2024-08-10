package com;

import java.util.ArrayList;

public class VectorClock extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
	int size;

	public VectorClock(int num) {
		super();
		for (int i = 0; i < num; i++) {
			this.add(0);
		}
		this.size = num;
	}

	public VectorClock clone() {
		VectorClock new_clock = new VectorClock(size);
		for (int i = 0; i < size; i++) {
			new_clock.set(i, this.get(i));
		}
		return new_clock;
	}

	public int getSum() {
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum = sum + this.get(i);
		}
		return sum;
	}

	public void increment(int sid) {
		this.set(sid, this.get(sid) + 1);
	}

	public static VectorClock fromString(String str) {
		String[] paras = str.split(",");
		VectorClock vc = new VectorClock(paras.length);
		for (int i = 0; i < vc.size; i++) {
			vc.set(i, Integer.parseInt(paras[i]));
		}
		return vc;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size; i++) {
			sb.append(this.get(i));
			if (i < this.size - 1) {
				sb.append(",");
			}
		}

		return sb.toString();
	}

}
