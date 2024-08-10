package ttf;

import com.CharObject;
import com.CharObjectSequence;
import com.CharOperation;
import com.OpType;

public class TTFObjectSequence extends CharObjectSequence {

	public TTFObjectSequence(TTFCharObject head, TTFCharObject tail) {
		super(head, tail);
	}

	public void applyLocal(CharOperation op) {
		CharObject obj = null;
		if (op.getType() == OpType.ins) {
			CharObject prev = findObjBeforePos(op.pos);
			obj = new TTFCharObject(op.content);
			addAfter(prev, obj);
		} else {
			CharObject prev = findObjBeforePos(op.pos);
			obj = nextVisibleObj(prev.next);
			obj.visible = false;
		}
		op.pos = indexOf2(obj);
	}

	public void applyRemote(CharOperation op) {
		CharObject obj = null;
		if (op.getType() == OpType.ins) {
			CharObject prev = findObjectBeforePos2(op.pos);
			obj = new TTFCharObject(op.content);
			addAfter(prev, obj);
			op.pos = indexOf(obj);
		} else {
			obj = findObjectBeforePos2(op.pos);
			obj = obj.next;
			obj.split(1);
			if (obj.visible) {
				obj.visible = false;
				op.pos = indexOf(obj);
			} else {
				op.pos = -1;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		CharObject ob = head.next;
		int count = 1;
		sb.append("OS information: ");
		sb.append("\n");
		while (ob != tail) {
			sb.append("第" + count + "个 object:");
			sb.append("\n");
			sb.append(ob.toString());
			sb.append("\n");
			count++;
			ob = ob.next;
		}

		return sb.toString();
	}

	public static void main(String[] args) {

	}

	@Override
	public void initialize(String str) {
		TTFCharObject obj = new TTFCharObject(str);
		this.head.next = obj;
		obj.next = this.tail;
	}

	public void visualize() {
		System.out.println("----------------------------------");
		TTFCharObject cur = (TTFCharObject) this.head.next;
		while (cur != this.tail) {
			System.out.print("{len:" + cur.content.length() + " visible:" + cur.visible + "}");
			System.out.print("\t");
			cur = (TTFCharObject) cur.next;
		}
		System.out.println();
	}

}
