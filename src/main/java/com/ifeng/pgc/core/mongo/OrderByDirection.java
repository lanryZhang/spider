package com.ifeng.pgc.core.mongo;

public enum OrderByDirection {
	ASC(1),DESC(-1);
	private int value;
	OrderByDirection(int v){
		this.value = v;
	}
	public int value() {
		return this.value;
	}
}
