package ast;

import com.CharOperation;
import com.VectorClock;

public class ASTOperation {
	CharOperation op;
	VectorClock vc;
	int sid;

	public ASTOperation(CharOperation op, VectorClock vc, int sid) {
		this.op = op;
		this.vc = vc;
		this.sid = sid;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(op.toString());
		sb.append("|");
		sb.append(this.sid);
		sb.append("|");
		sb.append(vc.toString());
		return sb.toString();
	}
}
