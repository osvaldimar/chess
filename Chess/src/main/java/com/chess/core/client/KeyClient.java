package com.chess.core.client;

import java.util.UUID;

import com.chess.core.enums.TypePlayer;

public class KeyClient {

	private UUID key;
	private TypePlayer type;
		
	public KeyClient(UUID key, TypePlayer type) {
		super();
		this.key = key;
		this.type = type;
	}
	
	public UUID getKey() {
		return key;
	}
	public void setKey(UUID key) {
		this.key = key;
	}
	public TypePlayer getType() {
		return type;
	}
	public void setType(TypePlayer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "KeyClient:["+ key + ", " + type +"]";
	}
}
