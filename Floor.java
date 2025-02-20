import java.util.Map;
import java.util.HashMap;

class Floor {
    private FloorLevel floorLevel;
    private Map<String, String> rooms;

    public Floor(FloorLevel floorLevel) {
        this.floorLevel = floorLevel;
        this.rooms = new HashMap<>();
    }

    public void addRoom(String roomId) {
        rooms.put(roomId, roomId);
    }

    public boolean hasRoom(String roomId) {
        return rooms.containsKey(roomId);
    }

    public FloorLevel getFloorLevel() {
        return floorLevel;
    }
}
