package com.tradecard.informer;

public class Messenger {
	
	private StringBuilder _message = new StringBuilder();
	
	private IndentHandler _indenter;
	
	public Messenger(IndentHandler indenter) {
		_indenter = indenter;
	}
	
	public Messenger newLine() {
		return append("\n");
	}
	
	public Messenger indent() {
		return append(_indenter.getIndent());
	}
	
	public Messenger append(Object message) {
		_message.append(message.toString());
		return this;
	}	
	
	public String toString() {		
		return _message.toString();
	}


}
