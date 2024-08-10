package ttf;

import com.CharObject;

public class TTFCharObject extends CharObject {
	public TTFCharObject(String content) {
		super(content);
	}

	@Override
	public void split(int len) {
		if (len < content.length()) {
			TTFCharObject part2 = new TTFCharObject(content.substring(len));
			part2.visible = this.visible;
			part2.next = this.next;
			this.next = part2;
			this.content = content.substring(0, len);
		}
	}
}
