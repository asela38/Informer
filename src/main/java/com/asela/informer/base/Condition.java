package com.asela.informer.base;

public interface Condition<T> {

	public boolean evaluate(T t);
}
