package ot;

import com.CharOperation;
import com.OpType;

public class TTFTransformer {
	public void SIT(GOTOperation op1, GOTOperation op2) {
		CharOperation sop1 = op1.getSOP();
		CharOperation sop2 = op2.getSOP();

		if (sop1.type == OpType.ins && sop2.type == OpType.ins) {
			SIT_II(op1, op2);
		}  else if (sop1.type == OpType.ins && sop2.type == OpType.del) {
			SIT_ID(sop1, sop2);
		} else if (sop1.type == OpType.del && sop2.type == OpType.ins) {
			SIT_ID(sop1, sop2);
		} 
	}

	private void SIT_II(GOTOperation o1, GOTOperation o2) {
		CharOperation op1 = o1.getSOP();
		CharOperation op2 = o2.getSOP();
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos + 1;
		} else if (op1.pos == op2.pos) {
			int pri = o1.sid - o2.sid;
			if (pri > 0) { // op1.sid > op2.sid
				op1.pos = op1.pos + 1;
			} else {
				op2.pos = op2.pos + 1;
			}
		} else {
			op2.pos = op2.pos + 1;
		}

	}

	public void SIT_ID(CharOperation op1, CharOperation op2) {
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos - 1;
		} else {
			op2.pos = op2.pos - 1;
		}
	}
}
