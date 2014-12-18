package amicity.graph.pc.gui.util;

import java.util.Map.Entry;

public class Pair<A, B> implements Entry<A, B> {
	A a;
	B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public A getKey() {
		return a;
	}

	@Override
	public B getValue() {
		return b;
	}

	@Override
	public B setValue(B value) {
		b = value;
		return b;
	}
	
}