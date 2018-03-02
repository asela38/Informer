package com.asela.informer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Test;

import com.asela.informer.BoundedCounter;

public class BoundedCounterTest {

	@Test
	public void boundedCounterVisualizer() {
	//	fail("Not yet implemented");
		
//		BoundedCounterInterface b = createDynamicProxy(BoundedCounterInterface.class, new BoundedCounter(0, 10));
//		b.decrement();
//		b.decrement();
//		b.decrement();
//		b.decrement();
//		b.decrement();
//		b.decrement();
//		b.decrement();
//		b.increment();
//		b.increment();
		
	}
	
	@Test
	public void natualCounterTest() {
		BoundedCounter naturalCounter = BoundedCounter.getNaturalCounter(10);
		for(int i = 0; i < 20; i++) {
			if(!naturalCounter.isHitUpper()) {
				System.out.println(naturalCounter.increment());
			}
		}
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void isUpperAlwaysBiggerThanLowerConditionWorks(){
		new BoundedCounter(10, 9);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isUpperNeverEqualsToLowerConditionWorks(){
		new BoundedCounter(10, 10);
	}
	
	@Test 
	public void isFromZeroCounterStartsFromZero() {
		BoundedCounter counter = BoundedCounter.getNaturalCounter(10);		
		Assert.assertTrue(counter.isHitLower());
	}

	@SuppressWarnings("unchecked")
	public <T> T createDynamicProxy(final Class<T> classT,final T t) {
		
		return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{classT}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object arg0, Method arg1, Object[] arg2)
					throws Throwable {
				
				System.out.println(arg0.getClass());
				System.out.println(arg1.getName());
				printInternals(t);
				
				return arg1.invoke(t, arg2);
			}
			
			public void printInternals(T t) {
				Field[] declaredFields = t.getClass().getDeclaredFields();
				
				System.out.print("[");
				for (Field field : declaredFields) {
					field.setAccessible(true);
					try {
						System.out.print(field.getName() +":" + field.getType() +":"+field.get(t) + ", ");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("]");
			}
		});
	}
}
