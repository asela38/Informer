package com.asela.informer;

import com.asela.informer.base.BoundedCounterInterface;

/**
 * BounderCounter is a Counter which continues it's counting between the
 * configured Range
 * 
 * @author AIllayapparachchi
 *
 */
public class BoundedCounter implements BoundedCounterInterface {
	
	private int _counter;
	private int _lower;
	private int _upper;
	
	public BoundedCounter(int lower, int upper){
		if(lower >= upper) {
			throw new IllegalArgumentException("\" upper > lower\".");
		}
		this._lower = lower;
		this._upper = upper;
		this._counter = (this._lower>>1) + (this._upper>> 1);
	}
	
	/* 
	 * Checks whether counter reached or hit the upper limit.
	 * @see com.asela.BoundedCounterInterface#isHitUpper()
	 */
	@Override
	public boolean isHitUpper() {
		return _upper == _counter;
	}
	
	/* Checks whether counter reached or hit the lower limit.
	 * @see com.asela.BoundedCounterInterface#isHitLower()
	 */
	@Override
	public boolean isHitLower() {
		return _lower == _counter;
	}
	
	/* Increments the counter by one.
	 * @see com.asela.BoundedCounterInterface#increment()
	 */
	@Override
	public BoundedCounter increment() {
		if(!isHitUpper()) {
			_counter++;
		}
		return this;
	}
	
	/* Decrements the counter by two.
	 * @see com.asela.BoundedCounterInterface#decrement()
	 */
	@Override
	public BoundedCounter decrement() {
		if(!isHitLower()) {
			_counter--;
		}
		return this;
	}
	
	
	public static BoundedCounter getNaturalCounter(int upper) {		
		BoundedCounter boundedCounter = new BoundedCounter(1, upper);		
		boundedCounter._counter = 1;		
		return boundedCounter;
	}
	
	@Override
	public String toString() {
		return "[ Upper = " + _upper + ", Lower = " + _lower + ", Counter = " + _counter + " ]";
	}
}
