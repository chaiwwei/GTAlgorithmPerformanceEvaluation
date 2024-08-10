package com;

public class CharOperation {

	public String content;
	public int pos; // position in the document
	public OpType type;

	public CharOperation(OpType type, String content, int pos) {
		this.pos = pos;
		this.content = content;
		this.type = type;
	}

	public static CharOperation createOperation(String typeStr, String content, int position) {
		CharOperation op = null;
		if (typeStr.equals("ins")) {
			op = new CharOperation(OpType.ins, content, position);
		} else {
			op = new CharOperation(OpType.del, content, position);
		}
		return op;
	}

	/*
	 * Construction of an insert operation
	 */
	public CharOperation clone() {
		if (this.type == OpType.del) {
			return new CharOperation(OpType.del, null, this.pos);
		} else {
			return new CharOperation(OpType.ins, this.content, this.pos);
		}
	}

	public OpType getType() {
		return this.type;
	}

	public int getPosition() {
		return pos;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.getType() == OpType.ins) {
			sb.append("ins(");
			sb.append(this.pos);
			sb.append(",");
			sb.append(this.content);
			sb.append(")");
		} else {
			sb.append("del(");
			sb.append(this.pos);
			sb.append(")");
		}
		return sb.toString();
	}

	public static CharOperation from(String str) {
		CharOperation op = null;

		String typeStr = str.substring(0, 3); // ins or del

		if (typeStr.equals("ins")) {
			int index = str.indexOf(",");
			int index2 = str.indexOf(")");
			String posStr = str.substring(4, index);
			String content = str.substring(index + 1, index2);
			op = new CharOperation(OpType.ins, content, Integer.parseInt(posStr));
		} else {
			int index = str.indexOf(")");
			String posStr = str.substring(4, index);
			op = new CharOperation(OpType.del, null, Integer.parseInt(posStr));
		}

		return op;
	}

	/*
	 * public static void main(String[] args) { StringOperation sop =
	 * StringOperation.from("ins(0,abc)"); System.out.println(sop.toString());
	 * System.out.println(sop.getPosition()); System.out.println(sop.getContent());
	 * 
	 * StringOperation sop1 = StringOperation.from("del(0,abc)");
	 * System.out.println(sop1.toString()); System.out.println(sop1.getPosition());
	 * System.out.println(sop1.getContent()); System.out.println(sop1.len);
	 * 
	 * 
	 * System.out.println(sop.clone().toString());
	 * System.out.println(sop1.clone().toString());
	 * 
	 * }
	 */
}