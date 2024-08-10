package ot;

public class OperationId implements Comparable<OperationId>{
	public int sid;
	public int seq;

	public OperationId( int sid, int seq) {
		this.sid = sid;
		this.seq = seq;
	}

	public String toString() {
		return  this.sid + "," + this.seq;
	}

	public static OperationId fromString(String sid, String seq) {
		return new OperationId(Integer.parseInt(sid), Integer.parseInt(seq));
	}

	@Override
	public int compareTo(OperationId id2) {
		// TODO Auto-generated method stub

			if (this.sid == id2.sid) {
				return this.seq - id2.seq;
			} else {
				return this.sid - id2.sid;
			}
		
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OperationId other = (OperationId) obj;

        if (this.sid != other.sid) {
            return false;
        }
        if (this.seq != other.seq) {
            return false;
        }
        return true;
    }
	
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.sid;
        hash = 79 * hash + this.seq;
        return hash;
    }
}

