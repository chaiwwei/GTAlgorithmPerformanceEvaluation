package ttf;

import java.util.ArrayList;

import com.CharOperation;
import com.GTAlgorithm;
import com.OpType;

import ot.GOTOControl;
import ot.GOTOperation;
import ot.Transformer;

public class TTF extends GTAlgorithm {
	TTFObjectSequence os;
	private ArrayList<GOTOperation> insHistory;
	private Transformer transformer = new Transformer();

	public TTF(int sid, int num, TTFObjectSequence os) {
		super(sid, num);
		// TODO Auto-generated constructor stub
		this.os = os;
		this.insHistory = new ArrayList<GOTOperation>();
	}

	@Override
	public CharOperation ROH(String opStr) {
		GOTOperation gop = GOTOperation.fromString(opStr);
		GOTOControl.transform(gop, this.insHistory, transformer);
		CharOperation op = gop.getSOP();
		os.applyRemote(op);
		return op;
	}

	@Override
	public String LOH(CharOperation op) {
		// TODO Auto-generated method stub
		//System.out.println("LOH Before :"+op.toString());
		this.localTimeUpdate();
		this.os.applyLocal(op);
		GOTOperation gop = new GOTOperation(op, this.getNewVClock(), this.sid);
		if (op.type == OpType.ins) {
			this.insHistory.add(gop);
		}
		//System.out.println("LOH After :"+op.toString());
		return gop.toString();
	}

	public int memorySize() {
		return this.os.memorySize();
	}

	@Override
	public void initializeInternalState(String content) {
		// TODO Auto-generated method stub
		this.os.initialize(content);
	}
}
