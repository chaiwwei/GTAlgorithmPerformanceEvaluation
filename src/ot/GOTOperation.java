package ot;

import java.util.HashMap;
import com.CharOperation;
import com.VectorClock;

public class GOTOperation {

	private HashMap<OperationId, CharOperation> versions;

	public GOTOperation(CharOperation op, VectorClock vc, int sid) {
		this.op = op;
		this.sid = sid;
		this.vc = vc;
		this.versions = new HashMap<>();
		this.oid = new OperationId(this.sid, this.getSeq(this.sid));
	}

	private GOTOperation(CharOperation op, VectorClock vc, int sid, OperationId oid,
			HashMap<OperationId, CharOperation> versions) {
		this.op = op;
		this.sid = sid;
		this.vc = vc;
		this.versions = versions;
		this.oid = oid;
	}

	public VectorClock vc;
	public int sid;
	public CharOperation op;
	public OperationId oid;

	public GOTOperation deepCopy() {
		return new GOTOperation(this.op.clone(), this.vc, this.sid, this.oid, this.versions);
	}

	public CharOperation copyCharOP() {
		return this.op.clone();
	}

	public boolean isCausal(GOTOperation op2) {
		boolean result = false;
		if (this.getSeq(this.sid) > op2.getSeq(this.sid) && this.getSeq(op2.sid) >= op2.getSeq(op2.sid)) {
			result = true;
		}
		return result;
	}

	public boolean isConcurrent(GOTOperation op2) {
		boolean result = true;
		if (this.getSeq(this.sid) <= op2.getSeq(this.sid)) {// op1->op2
			result = false;
		} else if (this.getSeq(op2.sid) >= op2.getSeq(op2.sid)) {// op2->op1
			result = false;
		}
		return result;
	}

	public CharOperation getSOP() {
		return this.op;
	}

	public int getSeq(int sid) {
		return this.vc.get(sid);
	}

	public void recordVersion(OperationId oid, CharOperation op) {
		this.versions.put(oid, op);
	}

	public void setCurrentVerison(OperationId oid) {
		this.op = this.versions.get(oid).clone();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(op.toString());
		sb.append("|");
		sb.append(this.sid);
		sb.append("|");
		sb.append(this.vc.toString());

		return sb.toString();
	}

	public static GOTOperation fromString(String opStr) {
		String[] paras = opStr.split("\\|");
		int sid = Integer.parseInt(paras[1]);
		VectorClock vc = VectorClock.fromString(paras[2]);

		return new GOTOperation(CharOperation.from(paras[0]), vc, sid);
	}
}