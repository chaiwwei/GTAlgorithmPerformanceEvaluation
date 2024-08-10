package ot;

import com.OpType;
import com.CharOperation;

public class Transformer {

	public void SIT(GOTOperation op1, GOTOperation op2) {
		CharOperation sop1 = op1.getSOP();
		CharOperation sop2 = op2.getSOP();

		if (sop1.type == OpType.ins && sop2.type == OpType.ins) {
			SIT_II(sop1, sop2, op1.sid - op2.sid);
		} else if (sop1.type == OpType.del && sop2.type == OpType.ins) {
			IT_DD(sop1, sop2);
			IT_DD(sop2, sop1);
		}
	}

	public void SIT_II(CharOperation op1, CharOperation op2, int pri) {
		if (op1.pos > op2.pos || (op1.pos == op2.pos && pri > 0)) {
			op1.pos = op1.pos + 1;
		} else {
			op2.pos = op2.pos + 1;
		}
	}

	public void SIT_ID(CharOperation op1, CharOperation op2) {
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos - 1;
		} else {
			op2.pos = op2.pos + 1;
		}
	}

	public void IT_II(CharOperation op1, CharOperation op2, int pri) {
		if (op1.pos > op2.pos || (op1.pos == op2.pos && pri > 0)) {
			op1.pos = op1.pos + 1;
		}
	}

	public void IT_DD(CharOperation op1, CharOperation op2) {
		if (op1.pos == -1) {
			// do nothing
		} else if (op1.pos == op2.pos) {
			op1.pos = -1;
		} else if (op1.pos > op2.pos) {
			op1.pos = op1.pos - 1;
		}
	}

	public void IT_ID(CharOperation op1, CharOperation op2) {
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos - 1;
		}
	}

	public void IT_DI(CharOperation op1, CharOperation op2) {
		if (op1.pos >= op2.pos) {
			op1.pos = op1.pos + 1;
		}
	}

	public void ET_DD(CharOperation op1, CharOperation op2) {
		if (op1.pos >= op2.pos) {
			op1.pos = op1.pos + 1;
		}
	}

	public void ET_ID(CharOperation op1, CharOperation op2) {
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos + 1;
		}
	}

	public void SWAP_ID(CharOperation op1, CharOperation op2) {
		if (op1.pos > op2.pos) {
			op1.pos = op1.pos + 1;
		} else {
			op2.pos = op2.pos + 1;
		}
	}
}
