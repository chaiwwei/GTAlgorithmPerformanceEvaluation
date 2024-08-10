package com;

public class TextDocument {
	private StringBuilder sb;
	int length;

	public TextDocument(String content) {
		this.sb = new StringBuilder(content);
		this.length = content.length();
	}

	public void insert(int pos, String str) {
		this.sb.insert(pos, str);
		this.length = this.length + str.length();
	}

	public void delete(int pos, int len) {
		// TODO Auto-generated method stub
		this.sb.delete(pos, pos + len);
		this.length = this.length - len;
	}

	public String get(int pos, int len) {
		return sb.substring(pos, pos + len);
	}

	public void execute(CharOperation op) {
		try {
			if (op.type == OpType.ins) {

				this.insert(op.pos, op.content);

			} else {
				this.delete(op.pos, 1);
			}
		} catch (Exception ex) {
			System.out.println("doc length:" + this.length);
			ex.printStackTrace();
		}
	}

	public int getLength() {
		// TODO Auto-generated method stub
		return this.length;
	}

	public void replace(String content) {
		this.sb.replace(0, this.length, content);
		this.length = content.length();
	}

	public String toString() {
		return this.sb.toString();
	}
}
