package crdt;

import com.OpType;
import com.VectorClock;

public class RGADelOperation extends RGAOperation {

	public RGADelOperation(RGAId id, int pos, VectorClock vc) {
		super(OpType.del, id, pos, vc);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("del|");
		sb.append(this.id.toString());
		sb.append("|");
		sb.append(this.pos);
		sb.append("|");
		sb.append(this.vc.toString());

		return sb.toString();
	}

}
