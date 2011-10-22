package com.tradecard.informer;

import com.tradecard.informer.base.Descriptor;

public class Descriptors {

	
	public static <T>  Descriptor<T> getToStringDescriptor(Class<T> clazz) {
		return new Descriptor<T>() {
			public String describe(T t) {
				return t.toString();
			}
		};
	}
	
	public static <T>  Descriptor<T> getEmptyDescriptor(Class<T> clazz) {
		return new Descriptor<T>() {
			public String describe(T t) {
				return "";
			}
		};
	} 
}
