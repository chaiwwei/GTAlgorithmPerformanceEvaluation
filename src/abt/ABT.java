package abt;

import java.util.ArrayList;

import com.CharOperation;
import com.GTAlgorithm;
import com.OpType;
import ot.GOTOControl;
import ot.GOTOperation;
import ot.Transformer;

public class ABT extends GTAlgorithm {

	private ArrayList<CharOperation> delHistory;
	private ArrayList<GOTOperation> insHistory;
	private Transformer transformer = new Transformer();

	public ABT(int sid, int num) {
		super(sid, num);
		this.delHistory = new ArrayList<>();
		this.insHistory = new ArrayList<>();
	}

	@Override
	public CharOperation ROH(String aop) {
		GOTOperation gop = GOTOperation.fromString(aop);
		GOTOControl.transform(gop, this.insHistory, transformer);
		CharOperation op = gop.getSOP();
		if (op.type == OpType.ins) {
			this.insHistory.add(gop);
			op = gop.copyCharOP();
		}
		remoteUpdateHD(transformer, op);
		return op;
	}

	@Override
	public String LOH(CharOperation op) {
		localUpdateHD(transformer, op);
		this.localTimeUpdate();
		GOTOperation gop = new GOTOperation(op, this.getNewVClock(), this.sid);
		if (op.type == OpType.ins) {
			this.insHistory.add(gop);
		}
		return gop.toString();

	}

	private void localUpdateHD(Transformer transformer, CharOperation op) {
		if (op.type == OpType.del) {
			CharOperation temp = op.clone();
			LET(transformer, op, this.delHistory);
			this.delHistory.add(temp);
		} else {
			LSWAP(transformer, op, this.delHistory);
		}
	}

	private void remoteUpdateHD(Transformer transformer, CharOperation op) {
		if (op.type == OpType.del) {
			LIT(transformer, op, this.delHistory);
			this.delHistory.add(op);
		} else {
			LSIT(transformer, op, this.delHistory);
		}
	}

	@Override
	public void initializeInternalState(String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public int memorySize() {
		// TODO Auto-generated method stub
		return this.delHistory.size() + this.insHistory.size();
	}

	// op is an insert operation
	private void LSIT(Transformer transformer, CharOperation op, ArrayList<CharOperation> deList) {
		for (CharOperation opx : deList) {
			transformer.SIT_ID(op, opx);
		}
	}

	// op is a delete operation
	private void LIT(Transformer transformer, CharOperation op, ArrayList<CharOperation> deList) {
		for (CharOperation opx : deList) {
			transformer.IT_DD(op, opx);
		}
	}

	// op is a delete operation
	private void LET(Transformer transformer, CharOperation op, ArrayList<CharOperation> deList) {
		for (int i = deList.size() - 1; i >= 0; i--) {
			CharOperation opx = deList.get(i);
			transformer.ET_DD(op, opx);
		}
	}

	// op is an insert operation
	private void LSWAP(Transformer transformer, CharOperation op, ArrayList<CharOperation> deList) {
		for (int i = deList.size() - 1; i >= 0; i--) {
			CharOperation opx = deList.get(i);
			transformer.SWAP_ID(op, opx);
		}
	}
}
