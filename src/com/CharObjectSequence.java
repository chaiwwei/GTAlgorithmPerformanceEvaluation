package com;

public abstract class CharObjectSequence {
	protected CharObject head;
	protected CharObject tail;

	protected CharObjectSequence(CharObject head, CharObject tail) {
		this.head = head;
		this.tail = tail;
		this.head.next = tail;
	}

	public abstract void initialize(String str);

	public void addAfter(CharObject obj1, CharObject obj2) {
		obj2.next = obj1.next;
		obj1.next = obj2;
	}

	public CharObject findObjBeforePos(int pos) {
		if (pos == 0) {
			return this.head;
		}

		int num = 0;
		CharObject obj = this.head.next;
		for (; obj != this.tail; obj = obj.next) {
			if (obj.visible) {
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

	protected int indexOf(CharObject endObject) {

		int num = 0;

		for (CharObject cur = this.head.next; cur != endObject; cur = cur.next) {
			if (cur.visible) {
				num = num + cur.content.length();
			}
		}

		return num;
	}

	protected int indexOf2(CharObject endObject) {

		int num = 0;

		for (CharObject cur = this.head.next; cur != endObject; cur = cur.next) {
			num = num + cur.content.length();
		}

		return num;
	}

	protected CharObject findObjectBeforePos2(int pos) {
		if (pos == 0) {
			return this.head;
		}

		int num = 0;
		CharObject obj = this.head.next;
		for (; obj != this.tail; obj = obj.next) {
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
		return obj;
	}

	protected CharObject nextVisibleObj(CharObject start) {
		CharObject cur = start;
		while (cur != this.tail) {
			if (cur.visible) {
				cur.split(1);
				break;
			}
			cur = cur.next;
		}
		return cur;
	}

	public int memorySize() {
		int size = 0;
		CharObject cur = this.head.next;
		while (cur != this.tail) {

			size = size + 1;

			cur = cur.next;
		}
		return size;
	}

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
}
