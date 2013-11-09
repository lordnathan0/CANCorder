

public final class Signal<X, Y, Z, A> {
	public final X signalID;
	public final Y startBit;
	public final Z length;
	public final A byteOrder;
	public Signal(X x, Y y, Z z, A a) {
		this.signalID = x;
		this.startBit = y;
		this.length = z;
		this.byteOrder = a;
	}
}

