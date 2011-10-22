package com.tradecard.informer.base;

import com.tradecard.informer.BoundedCounter;

public interface BoundedCounterInterface {

	public abstract boolean isHitUpper();

	public abstract boolean isHitLower();

	public abstract BoundedCounter increment();

	public abstract BoundedCounter decrement();

}