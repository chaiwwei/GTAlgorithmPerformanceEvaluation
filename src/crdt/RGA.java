package crdt;

import com.CharOperation;
import com.GTAlgorithm;
import com.OpType;

public class RGA extends GTAlgorithm {
	RGACharObjectSequence os;

	public RGA(int sid, int num, RGACharObjectSequence os) {
		super(sid, num);
		this.os = os;
	}

	@Override
	public CharOperation ROH(String opStr) {
		//System.out.println("ROH:"+opStr);
		CharOperation op = null;

		RGAOperation rop = RGAOperation.fromString(opStr);
		if (rop.type == OpType.ins) {
			RGAInsOperation iop = (RGAInsOperation) rop;
			op = this.os.insert(iop.prevId, iop.pos, iop.id, iop.content);
		} else {
			RGADelOperation dop = (RGADelOperation) rop;
			op = this.os.delete(dop.id, dop.pos);
		}
		this.remoteTimeUpdate(rop.id.sid, rop.vc);
		//System.out.println("ROH:"+op.toString());
		return op;
	}

	@Override
	public String LOH(CharOperation op) {
		//System.out.println("LOH:"+op.toString());

		this.localTimeUpdate();
		RGAOperation rop = null;
		RGAId newId = this.createId();
		
		//this.os.visualize();
		if (op.type == OpType.ins) {
			rop = this.os.insert(op.pos, op.content, newId, this.getNewVClock());
		} else {
			rop = this.os.delete(op.pos, this.getNewVClock());
		}
		
		//this.os.visualize();
		//System.out.println("LOH:"+rop.toString());
		return rop.toString();
	}

	public RGAId createId() {
		return new RGAId(this.getVClock().getSum(), this.sid, this.getSeq(this.sid));
	}

	public int memorySize() {
		return this.os.memorySize();
	}

	@Override
	public void initializeInternalState(String content) {
		this.os.initialize(content);
	}
}
