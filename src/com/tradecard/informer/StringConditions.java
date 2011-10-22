package com.tradecard.informer;

import com.tradecard.informer.base.StringCondition;

public class StringConditions {

	
	public static StringCondition startsWith(final String starter) {
		return new StringCondition() {
			
			@Override
			public boolean evaluate(String string) {
				return string.startsWith(starter);
			}
		};
	}
	
	public static StringCondition contains(final String word) {
		return new StringCondition() {
			
			@Override
			public boolean evaluate(String string) {
				return string.contains(word);
			}
		};
	}
	
	
	public static StringCondition equals(final String starter) {
		return new StringCondition() {
			
			@Override
			public boolean evaluate(String string) {
				return starter.equals(string);
			}
		};
	}
	
	
	public static StringCondition not(final StringCondition condition) {
		return new StringCondition() {
			
		@Override
		public boolean evaluate(String string) {
				return !condition.evaluate(string);
			}
		};
	}
	
	public static StringCondition or(final StringCondition... conditions) {
		return new StringCondition() {
			
		@Override
		public boolean evaluate(String string) {
				boolean result = false;
				
				for(StringCondition condition : conditions) {
					result = result || condition.evaluate(string);
				}
				return result;
			}
		};
	}
}
