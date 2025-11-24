package org.connection;

public class RoomInfo {
    String id;
    String name;
    String playerCount;

    RoomInfo(String id, String name, String playerCount) {
        this.id = id;
        this.name = name;
        this.playerCount = playerCount;
    }

    public String getName() {
        return name;
    }
    public String getPlayerCount() {
        return playerCount;
    }

    public String getId() {return id;}
}
