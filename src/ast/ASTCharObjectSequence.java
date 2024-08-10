package ast;

import java.util.Iterator;

import com.CharObject;
import com.CharObjectSequence;
import com.VectorClock;

public class ASTCharObjectSequence extends CharObjectSequence {

	public ASTCharObjectSequence(ASTCharObject head, ASTCharObject tail) {
		super(head, tail);
	}

	public int remoteInsert(int pos, String str, ASTId newId, VectorClock vc) {
		ASTCharObject obj = new ASTCharObject(newId, str);
		CharObject prev = this.findObjBeforePos(pos, vc);
		ASTCharObject next = this.nextAppeared((ASTCharObject) prev.next, vc);

		add(obj, (ASTCharObject) prev, next);

		return this.indexOf(obj);
	}

	public void localInsert(int pos, String str, ASTId newId) {
		CharObject obj = new ASTCharObject(newId, str);
		CharObject prev = this.findObjBeforePos(pos);
		addAfter(prev, obj);
	}

	public int remoteDelete(int pos, ASTId newId, VectorClock vc) {

		CharObject prev = this.findObjBeforePos(pos, vc);
		ASTCharObject obj = this.nextVisibleObj((ASTCharObject) prev.next, vc);
		int tpos = -1;
		if (obj.visible) {
			tpos = indexOf(obj);
			obj.visible = false;
		}
		obj.dops.add(newId);

		return tpos;
	}

	public void localDelete(int pos, ASTId newId) {
		CharObject prev = this.findObjBeforePos(pos);
		ASTCharObject next = (ASTCharObject) this.nextVisibleObj(prev.next);
		next.dops.add(newId);
		next.visible = false;
	}

	public void add(ASTCharObject newObj, ASTCharObject prev, ASTCharObject next) {
		ASTCharObject cur = (ASTCharObject) prev.next;
		CharObject pre = prev;
		while (cur != next) {
			if (newObj.iop.compareTo(cur.iop) > 0) {
				pre = cur;
			}
			cur = (ASTCharObject) cur.next;
		}
		addAfter(pre, newObj);
	}

	public void initialize(String content) {
		ASTCharObject obj = new ASTCharObject(null, content);
		this.head.next = obj;
		obj.next = this.tail;
	}

	public CharObject findObjBeforePos(int pos, VectorClock vc) {
		if (pos == 0) {
			return this.head;
		}

		int num = 0;
		CharObject obj = this.head.next;
		for (; obj != this.tail; obj = obj.next) {
			if (visible((ASTCharObject) obj, vc)) {
				int len = obj.content.length();
				if (pos == num + len) {
					break;
				} else if (pos < num + len) {
					obj.split(pos - num);
					break;
				} else {
					num = num + len;
				}
			}
		}
		return obj;
	}

	private boolean visible(ASTCharObject obj, VectorClock vc) {
		boolean condition1 = obj.iop == null || obj.iop.seq <= vc.get(obj.iop.sid);
		boolean condition2 = false;
		Iterator<ASTId> iterator = obj.dops.iterator();
		while (iterator.hasNext()) {
			ASTId id = iterator.next();
			if (id.seq <= vc.get(id.sid)) {
				condition2 = true;
				break;
			}
		}

		return condition1 && !condition2;
	}

	private ASTCharObject nextAppeared(ASTCharObject start, VectorClock vc) {
		ASTCharObject cur = start;
		while (cur != this.tail) {
			if (cur.appeared(vc)) {
				break;
			}
			cur = (ASTCharObject) cur.next;
		}
		return cur;
	}

	protected ASTCharObject nextVisibleObj(ASTCharObject start, VectorClock vc) {
		ASTCharObject cur = start;
		while (cur != this.tail) {
			if (visible(cur, vc)) {
				cur.split(1);
				break;
			}
			cur = (ASTCharObject) cur.next;
		}
		return cur;
	}

	public void visualize() {
		System.out.println("----------------------------------");
		ASTCharObject cur = (ASTCharObject) this.head.next;
		while (cur != this.tail) {
			System.out.print("{len:" + cur.content.length() + " visible:" + cur.visible + "}");
			System.out.print("\t");
			cur = (ASTCharObject) cur.next;
		}
		System.out.println();
	}
}