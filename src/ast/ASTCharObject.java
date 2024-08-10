package ast;

import java.util.ArrayList;
import java.util.List;

import com.CharObject;
import com.VectorClock;

public class ASTCharObject extends CharObject {

	public ASTId iop;
	public List<ASTId> dops;

	public ASTCharObject() {
		super("");
		this.iop = null;
		this.dops = new ArrayList<>();
	}

	public ASTCharObject(ASTId iop, String content) {
		super(content);
		this.iop = iop;
		this.dops = new ArrayList<>();
	}

	@Override
	public void split(int len) {
		// TODO Auto-generated method stub
		if (len < content.length()) {
			ASTCharObject part2 = new ASTCharObject(this.iop, content.substring(len));
			part2.visible = this.visible;
			part2.next = this.next;
			this.next = part2;
			this.content = content.substring(0, len);
		}
	}

	public boolean appeared(VectorClock vc) {

		boolean result = false;

		if ((this.iop == null) || (vc.get(this.iop.sid) >= this.iop.seq)) { // The object is there before the op with vc
																			// happens
			result = true;
		}

		return result;
	}

}