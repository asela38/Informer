package com.tradecard.informer;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tradecard.informer.base.Condition;
import com.tradecard.informer.base.Descriptor;
import com.tradecard.informer.base.StringCondition;

public class Informer {

	private List<Object> accessedObjects;
	private BoundedCounter counter;
	private IndentHandler indenter;
	private List<StringCondition> conditions;
	private PrintStream printStream;
	private Map<Class<?>, Descriptor<Object>> descriptors;

	private Informer(int depth, PrintStream out, List<StringCondition> methodConditions, Map<Class<?>, Descriptor<Object>> descriptorMap) {
		counter = BoundedCounter.getNaturalCounter(depth);
		indenter = new IndentHandler();
		conditions = new ArrayList<StringCondition>();
		accessedObjects = new ArrayList<Object>();
		descriptors = new HashMap<Class<?>,Descriptor<Object>>();
		printStream = out;
		conditions.addAll(methodConditions);
		descriptors.putAll(descriptorMap);
		conditions.add(StringConditions.not(StringConditions.equals("getClass")));
	}

	private <T> void debug0(T t, Messenger messenger) {

		if (counter.isHitUpper()) {
			messenger.newLine();
			return;
		}

		counter.increment();

		accessedObjects.add(t);
		Class<? extends Object> class1 = t.getClass();
		messenger.append(class1.toString()).newLine();
		Method[] methods = class1.getMethods();

		for (Method method : methods) {
			if (isANeededMethod(method)) {
				try {
					messenger.indent().append("MS ").append(method.getName()).append(" : ");
					Object invoke = method.invoke(t);
					
					if (accessedObjects.contains(invoke)) {
						messenger.append(invoke.getClass()).append(" * bidirectional ( referenceing the Holder)").newLine();
						continue;
					}
					getObjectInfomation(messenger, invoke);
					
				} catch (Exception e) {
					messenger.newLine();
					e.printStackTrace();
				} finally {
					messenger.indent().append("ME ").newLine();
				}
			}
		}

		counter.decrement();

	}

	private void getObjectInfomation(Messenger sb, Object invoke) {

		if (invoke == null) {
			sb.append("<null>").newLine();
			return;
		}

		if (invoke instanceof CharSequence) {
			sb.append(invoke).newLine();
		} else if (invoke instanceof Number) {
			sb.append(String.valueOf(invoke)).newLine();
		} else if (invoke.getClass().isArray() || invoke instanceof Collection<?> || invoke instanceof Map<?, ?>) {
			handle(sb, invoke);
		} else if (descriptors.containsKey(invoke.getClass())){
			sb.append(descriptors.get(invoke.getClass()).describe(invoke)).newLine();
		} else {
		
			indenter.extendIndent();
			debug0(invoke, sb);
			indenter.reduceIndent();
		}
	}

	private void handle(Messenger sb, Object invoke) {
		indenter.extendIndent();
		sb.append(invoke.getClass()).append(":");
		sb.newLine().indent().append("[").newLine();
		indenter.subExtendIndent();

		if (invoke instanceof Map) {
			handleMap(sb, invoke);
		} else if (invoke.getClass().isArray()) {
			handleArray(sb, invoke);
		} else if (invoke instanceof Collection<?>) {
			handleCollection(sb, invoke);
		}

		indenter.reduceIndent();
		sb.indent().append("]").newLine();
		indenter.reduceIndent();
	}

	private void handleMap(Messenger sb, Object invoke) {
		Map<?, ?> map = (Map<?, ?>) invoke;
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			sb.indent().append(entry.getKey()).append("->");
			getObjectInfomation(sb, entry.getValue());
		}
	}

	private void handleCollection(Messenger sb, Object invoke) {
		Collection<?> collection = (Collection<?>) invoke;
		for (Object object : collection) {
			sb.indent().append("->");
			getObjectInfomation(sb, object);
		}
	}

	private void handleArray(Messenger sb, Object invoke) {
		int length = Array.getLength(invoke);
		for (int i = 0; i < length; i++) {
			sb.indent().append(i).append("->");
			getObjectInfomation(sb, Array.get(invoke, i));
		}
	}

	private boolean isANeededMethod(Method method) {

		boolean isNeeded = method.getParameterTypes().length == 0;
		
		for (Condition<String> condition : conditions) {
			isNeeded = isNeeded && condition.evaluate(method.getName());
		}

		return isNeeded;
	}

	public <T> void getInformation(T t) {
		Messenger messenger = new Messenger(indenter);
		debug0(t, messenger);
		printStream.println(messenger.toString());
	}

	public static Informer newInformer() {		
		return Informer.getBuilder().addMethodCondition(StringConditions.startsWith("get")).build();
	}


	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {

		private int _depth = 3;
		private PrintStream _out= System.out;
		private List<StringCondition> methodConditions = new ArrayList<StringCondition>();
		private Map<Class<?>, Descriptor<Object>> descriptors = new HashMap<Class<?>, Descriptor<Object>>();
		
		public Builder newBuilder() {
			return newBuilder();
		}

		public Builder depth(int depth) {
			_depth = depth;
			return this;
		}
		
		public Builder out(PrintStream out) {
			_out = out;
			return this;
		}
		
		public Builder addMethodCondition(StringCondition condition) {
			methodConditions.add(condition);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public <T> Builder putDescriptor(Class<?> clazz, Descriptor<? extends Object> descriptor) {
			descriptors.put(clazz, (Descriptor<Object>) descriptor);
			return this;
		}		

		public Informer build() {
			Informer informer = new Informer(_depth, _out, methodConditions, descriptors);			
			return informer;
		}
	}

}
