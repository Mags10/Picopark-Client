package org.connection;

public class KeyData {
    public float x;
    public float y;
    public boolean isCollected;
    public String carriedByPlayerId;
    public float floatOffset;
    public boolean isOpeningDoor;

    public KeyData(float x, float y, boolean isCollected, String carriedByPlayerId) {
        this.x = x;
        this.y = y;
        this.isCollected = isCollected;
        this.carriedByPlayerId = carriedByPlayerId;
        this.floatOffset = 0;
        this.isOpeningDoor = false;
    }
}