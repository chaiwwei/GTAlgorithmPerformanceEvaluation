package crdt;

import com.OpType;
import com.VectorClock;

public abstract class RGAOperation {
	public OpType type;
	public RGAId id;
	public VectorClock vc;
	public int pos; // inner position of object

	public RGAOperation(OpType type, RGAId id, int pos, VectorClock vc) {
		this.type = type;
		this.id = id;
		this.vc = vc;
		this.pos = pos;
	}

	public abstract String toString();

	public static RGAOperation fromString(String opStr) {
		// ins | sum,sid,seq | sum,sid,seq| content | vc
		String[] paras = opStr.split("\\|");
		if (paras[0].equals("ins")) {
			RGAId prevId = null;
			if (!paras[1].isEmpty()) {
				String[] prevIdParas = paras[1].split(",");
				prevId = RGAId.fromString(prevIdParas[0], prevIdParas[1], prevIdParas[2]);
			}
			String[] newIdParas = paras[3].split(",");
			RGAId newId = RGAId.fromString(newIdParas[0], newIdParas[1], newIdParas[2]);
			VectorClock vc = VectorClock.fromString(paras[5]);
			return new RGAInsOperation(prevId, Integer.parseInt(paras[2]), newId, paras[4], vc);
		} else {
			// del | sun,sid,seq |pos| vc
			String[] newIdParas = paras[1].split(",");
			RGAId newId = RGAId.fromString(newIdParas[0], newIdParas[1], newIdParas[2]);
			VectorClock vc = VectorClock.fromString(paras[3]);

			return new RGADelOperation(newId, Integer.parseInt(paras[2]), vc);
		}
	}
}
