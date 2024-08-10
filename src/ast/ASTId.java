package ast;

public class ASTId {
	public int sid;
	public int seq;

	public ASTId(int sid, int seq) {
		this.sid = sid;
		this.seq = seq;
	}

	public int compareTo(ASTId id) {

		if (this.sid == id.sid) {
			return this.seq - id.seq;
		} else {
			return this.sid - id.sid;
		}
	}
}
