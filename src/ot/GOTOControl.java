package ot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.VectorClock;

public class GOTOControl {

	private GOTOControl() {

	}

	public static void transform(GOTOperation gop, List<GOTOperation> history, Transformer transformer) {

		ArrayList<GOTOperation> cons = new ArrayList<>();
		GOTOperation lastCausalBeforeOp = null;
		for (GOTOperation gopx : history) {
			if (gop.isConcurrent(gopx)) {
				cons.add(gopx.deepCopy());
			} else {
				lastCausalBeforeOp = gopx;
			}

		}

		for (GOTOperation temp : cons) {
			if (lastCausalBeforeOp != null) {
				temp.setCurrentVerison(lastCausalBeforeOp.oid);
			}
			transformer.SIT(gop, temp);
			gop.recordVersion(temp.oid, temp.copyCharOP());
			temp.recordVersion(gop.oid, temp.copyCharOP());
		}
	}

	public static void clear(List<GOTOperation> history, VectorClock minVC) {
		Iterator<GOTOperation> iterator = history.iterator();
		while (iterator.hasNext()) {
			GOTOperation opx = iterator.next();
			if (opx.getSeq(opx.sid) > minVC.get(opx.sid)) {
				break;
			} else {
				iterator.remove();
			}
		}
	}
}
