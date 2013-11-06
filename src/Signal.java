

public final class Signal<X, Y, Z> {
	public final X signalID;
	public final Y startBit;
	public final Z length;
	public Signal(X x, Y y, Z z) {
		this.signalID = x;
		this.startBit = y;
		this.length = z;
	}
}

