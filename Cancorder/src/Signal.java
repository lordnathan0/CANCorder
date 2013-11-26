

public final class Signal<A, B, C, D, E> {
	public A signalID;
	public B startBit;
	public C length;
	public D byteOrder;
	public E dataType;
	public Signal(A a, B b, C c, D d, E e) {
		this.signalID = a;
		this.startBit = b;
		this.length = c;
		this.byteOrder = d;
		this.dataType = e;
	}
}

