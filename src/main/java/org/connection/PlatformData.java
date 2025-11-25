package org.connection;

public class PlatformData {
    public String id;
    public float x;
    public float y;
    public float width;
    public float height;
    public int type;
    public int direction;
    public boolean isMoving;
    public int playersOnPlatform;
    public int requiredPlayers;
    public int playersNeeded;

    public PlatformData(String id, float x, float y, float width, float height, int type, int direction, boolean isMoving) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.direction = direction;
        this.isMoving = isMoving;
        this.playersOnPlatform = 0;
        this.requiredPlayers = 0;
        this.playersNeeded = 0;
    }
}
