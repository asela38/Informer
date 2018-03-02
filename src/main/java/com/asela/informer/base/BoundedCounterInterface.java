package com.asela.informer.base;

import com.asela.informer.BoundedCounter;

public interface BoundedCounterInterface {

	public abstract boolean isHitUpper();

	public abstract boolean isHitLower();

	public abstract BoundedCounter increment();

	public abstract BoundedCounter decrement();

}