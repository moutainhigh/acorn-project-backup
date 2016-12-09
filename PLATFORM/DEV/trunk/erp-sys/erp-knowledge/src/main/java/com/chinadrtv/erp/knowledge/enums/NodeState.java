package com.chinadrtv.erp.knowledge.enums;

public enum NodeState {
	open,
	closed;
	
	public static NodeState byName(String name) {
		for (NodeState state : NodeState.values()) {
			if (state.name().equals(name)) return state;
		}
		return null;
	}
}
