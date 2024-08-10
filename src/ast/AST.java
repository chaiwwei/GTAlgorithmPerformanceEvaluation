package ast;

import com.GTAlgorithm;
import com.OpType;
import com.CharOperation;
import com.VectorClock;

public class AST extends GTAlgorithm {
	ASTCharObjectSequence os;

	public AST(int sid, int num, ASTCharObjectSequence os) {
		super(sid, num);
		// TODOT Auto-generated constructor stub
		this.os = os;
	}

	@Override
	public CharOperation ROH(String opStr) {// " ins | pos | str | sid | vc "; "del | pos | len | sid | vc "
		
		//System.out.println("ROH Before:"+opStr);
		//this.os.visualize();
		
		String[] paras = opStr.split("\\|");
		CharOperation op = CharOperation.from(paras[0]);
		int sid = Integer.parseInt(paras[1]);
		VectorClock vc = VectorClock.fromString(paras[2]);
		ASTId newId = new ASTId(sid, vc.get(sid));

		if (op.type == OpType.ins) {
			op.pos = this.os.remoteInsert(op.pos, op.content, newId, vc);
		} else {
			op.pos = this.os.remoteDelete(op.pos, newId, vc);
		}

		//System.out.println("ROH After:"+op.toString());
		this.remoteTimeUpdate(sid, vc);
		return op;
	}

	@Override
	public String LOH(CharOperation op) {

		this.localTimeUpdate();
		VectorClock vc = this.getNewVClock();
		ASTId newId = new ASTId(this.sid, vc.get(this.sid));
		ASTOperation io = new ASTOperation(op, vc, sid);

		if (op.type == OpType.ins) {
			this.os.localInsert(op.pos, op.content, newId);
		} else {
			this.os.localDelete(op.pos, newId);
		}

		return io.toString();
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