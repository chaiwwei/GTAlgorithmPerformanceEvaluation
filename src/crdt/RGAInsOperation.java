package crdt;

import com.OpType;
import com.VectorClock;

public class RGAInsOperation extends RGAOperation {

	public RGAId prevId = null;
	public String content;
	public VectorClock vc;

	public RGAInsOperation(RGAId prevId, int pos, RGAId id, String content, VectorClock vc) {
		super(OpType.ins, id, pos, vc);
		this.prevId = prevId;
		this.content = content;
		this.vc = vc;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("ins|");
		if (this.prevId == null) {
			sb.append("");
		} else {
			sb.append(this.prevId.toString());
		}
		sb.append("|");
		sb.append(this.pos);
		sb.append("|");
		sb.append(this.id.toString());
		sb.append("|");
		sb.append(this.content);
		sb.append("|");
		sb.append(this.vc.toString());

		return sb.toString();

	}

}
