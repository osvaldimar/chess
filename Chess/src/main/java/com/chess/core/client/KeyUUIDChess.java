package com.chess.core.client;

import java.util.UUID;

public class KeyUUIDChess {

	private UUID uuidW;
	private UUID uuidB;
	
	public KeyUUIDChess(UUID uuidW, UUID uuidB) {
		this.uuidW = uuidW;
		this.uuidB = uuidB;
	}
	
	public UUID getUuidW() {
		return uuidW;
	}
	public UUID getUuidB() {
		return uuidB;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuidB == null) ? 0 : uuidB.hashCode());
		result = prime * result + ((uuidW == null) ? 0 : uuidW.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyUUIDChess other = (KeyUUIDChess) obj;
		if (uuidB != null) {
			if (uuidB.equals(other.uuidB))
				return true;
		}
		if (uuidW != null) {
			if (uuidW.equals(other.uuidW))
				return true;
		}
		return false;
	}
	
	
}
