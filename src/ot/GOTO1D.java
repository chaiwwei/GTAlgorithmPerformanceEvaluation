package ot;

import java.util.ArrayList;

import com.GTAlgorithm;
import com.CharOperation;

public class GOTO1D extends GTAlgorithm {
	protected ArrayList<GOTOperation> history;
	
	public GOTO1D(int sid, int num) {
		super(sid, num);
		this.history = new ArrayList<>();
	}

	@Override
	public CharOperation ROH(String aop) {
		GOTOperation gop = GOTOperation.fromString(aop);

		GOTOControl.transform(gop, this.history, new Transformer());
		this.history.add(gop);
		this.remoteTimeUpdate(gop.sid, gop.vc);

		return gop.getSOP();
	}

	
	@Override
	public String LOH(CharOperation op) {
		this.localTimeUpdate();
		GOTOperation gop = new GOTOperation(op, this.getNewVClock(), this.sid);
		this.history.add(gop);
		return gop.toString();
	}

	@Override
	public int memorySize() {
		return history.size();
	}

	@Override
	public void initializeInternalState(String content) {
		this.history = new ArrayList<>();
	}

}
