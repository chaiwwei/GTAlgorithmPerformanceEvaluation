package com;

public abstract class CharObject {
	public CharObject next;
	public String content;
	public boolean visible;

	protected CharObject(String content) {
		this.next = null;
		this.content = content;
		this.visible = true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("object information: \n");
		sb.append("char: " + this.content);
		if (this.visible) {
			sb.append("visible: true\n");
		} else {
			sb.append("visible: false\n");
		}
		return sb.toString();
	}

	public abstract void split(int len);

}
