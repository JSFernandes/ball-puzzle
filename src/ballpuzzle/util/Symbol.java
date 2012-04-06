package ballpuzzle.util;

public enum Symbol {
	TILE('-'),
	WALL('W'),
	BALL('B'),
	GOAL('X'),
	TELEPORT('T'),
	UNIDIRECTION_LEFT('<'),
	UNIDIRECTION_RIGHT('>'),
	UNIDIRECTION_UP('^'),
	UNIDIRECTION_DOWN('v');
	
	private char symbol_;
	
	Symbol(char symbol) {
		symbol_ = symbol;
	}
	
	public char getSymbol() {
		return symbol_;
	}
	
	public static Symbol fromChar(char symbol) {
		for (Symbol s : Symbol.values()) {
			if (symbol == s.symbol_) {
				return s;
			}
		}
		
		throw new IllegalArgumentException("No constant with symbol " + symbol + " found");
	}
}
