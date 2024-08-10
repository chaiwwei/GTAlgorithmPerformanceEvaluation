package crdt;

import com.CharObject;

public class RGACharObject extends CharObject {
	public final RGAId id;
	public int pos;

	public RGACharObject(RGAId id, String content) {
		super(content);
		this.id = id;
		this.pos = 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" id: ");
		sb.append(this.id.toString());
		sb.append(" content: ");
		sb.append(this.content);
		sb.append(" visible: ");
		sb.append(this.visible);
		return sb.toString();
	}

	@Override
	public void split(int len) {
		// TODO Auto-generated method stub
		if (len < content.length()) {
			RGACharObject part2 = new RGACharObject(this.id, content.substring(len));
			part2.pos = this.pos + len;
			part2.visible = this.visible;
			part2.next = this.next;
			this.next = part2;
			this.content = content.substring(0, len);
		}
	}

}