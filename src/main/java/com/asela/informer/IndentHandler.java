package com.asela.informer;

public class IndentHandler {
	
	public enum Part {
		EXPAND("+"),
		CONTINUE("|"),
		SUBCONTINUE(":");
		
		private String _string;
		
		private Part(String string) {
			_string = string;
		}
		
		public String toString() {
			return " " + _string;
		}
	}
	
	private String indentStr = Part.EXPAND.toString();
	private String indent = indentStr;
	
	public void extendIndent() {
		indent = indent.substring(0, indent.length() - 2) + Part.CONTINUE.toString() + indentStr;
	}
	
	public void subExtendIndent() {
		indent = indent.substring(0, indent.length() - 2) + Part.SUBCONTINUE.toString() + indentStr;
	}
	
	public void reduceIndent() {
		if(!indent.equals(indentStr)) {
			indent = indent.substring(0, indent.length() - indentStr.length() * 2) + indentStr;
		}
	}
	
	public String getIndent() {
		return indent;
	}

}
