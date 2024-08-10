package crdt;

import java.util.HashMap;
import java.util.Map;

import com.CharObject;
import com.CharObjectSequence;
import com.CharOperation;
import com.OpType;
import com.VectorClock;

public class RGACharObjectSequence extends CharObjectSequence {

	public Map<RGAId, RGACharObject> map = new HashMap<>();

	public RGACharObjectSequence(RGACharObject head, RGACharObject tail) {
		super(head, tail);
	}

	public RGAOperation insert(int pos, String content, RGAId newId, VectorClock vc) {
		RGAInsOperation rop = null;

		RGACharObject obj = new RGACharObject(newId, content);
		this.map.put(newId, obj);

		RGACharObject prev = (RGACharObject) findObjBeforePos(pos);
		addAfter(prev, obj);

		RGACharObject headObj = (RGACharObject) this.head;
		if (prev.id.equals(headObj.id)) {
			rop = new RGAInsOperation(null, 0, obj.id, obj.content, vc);
		} else {
			// prevEndPos and prevId together for searching object
			int prevEndPos = prev.pos + prev.content.length() - 1;
			rop = new RGAInsOperation(prev.id, prevEndPos, obj.id, obj.content, vc);
		}

		return rop;
	}

	public CharOperation insert(RGAId prevId, int endPos, RGAId newId, String content) {

		RGACharObject obj = new RGACharObject(newId, content);
		this.map.put(newId, obj);

		if (prevId != null) {
			RGACharObject prev = this.map.get(prevId);
			RGACharObject prevInner = searchPrevObject(prev, prevId, endPos);
			addAfter2(prevInner, obj);
		} else {
			addAfter2((RGACharObject) this.head, obj);
		}

		int pos = indexOf(obj);

		return new CharOperation(OpType.ins, obj.content, pos);
	}

	private RGACharObject searchPrevObject(RGACharObject start, RGAId id, int endPos) {
		RGACharObject cur = start;
		while (cur != this.tail) {
			if (cur.id.equals(id)) {
				if (cur.pos + cur.content.length() - 1 == endPos) {
					break;
				} else if (endPos < cur.pos + cur.content.length() - 1) {
					cur.split(endPos - cur.pos + 1);
					break;
				}
			}
			cur = (RGACharObject) cur.next;
		}
		return cur;
	}

	private RGACharObject searchTargetObject(RGACharObject start, RGAId id, int innerPos) {
		RGACharObject cur = start;
		while (cur != this.tail) {
			if (cur.id.equals(id)) {
				if (cur.pos == innerPos) {
					cur.split(1);
					break;
				} else if (innerPos < cur.pos + cur.content.length()) {
					cur.split(innerPos - cur.pos);
				}
			}
			cur = (RGACharObject) cur.next;
		}
		return cur;
	}

	public RGADelOperation delete(int pos, VectorClock vc) {

		CharObject prev = findObjBeforePos(pos);
		CharObject target = nextVisibleObj(prev.next);
		RGACharObject tar = (RGACharObject) target;
		target.visible = false;

		return new RGADelOperation(tar.id, tar.pos, vc);
	}

	public CharOperation delete(RGAId newId, int innerPos) {
		RGACharObject ob = this.map.get(newId);
		RGACharObject tar = searchTargetObject(ob, newId, innerPos);
		int pos = -1;
		if (tar.visible) {
			pos = indexOf(tar);
			tar.visible = false;
		}

		return new CharOperation(OpType.del, tar.content, pos);
	}

	private void addAfter2(RGACharObject prev, RGACharObject obj) {
		CharObject pre = prev;
		CharObject cur = pre.next;
		for (; cur != this.tail; cur = cur.next) {
			RGACharObject castCur = (RGACharObject) cur;
			if (obj.id.compareTo(castCur.id) > 0) {
				break;
			} else {
				pre = cur;
			}
		}
		addAfter(pre, obj);
	}

	public void visualize() {
		System.out.println("----------------------------------");
		RGACharObject cur = (RGACharObject) this.head.next;
		while (cur != this.tail) {
			System.out.print("{id:" + cur.id + " len:" + cur.content.length() + " visible:" + cur.visible + "}");
			System.out.print("\t");
			cur = (RGACharObject) cur.next;
		}
		System.out.println();
	}

	@Override
	public void initialize(String str) {
		RGACharObject obj = new RGACharObject(new RGAId(0, 0, 2), str);
		this.head.next = obj;
		obj.next = this.tail;
		this.map.put(obj.id, obj);
	}
}