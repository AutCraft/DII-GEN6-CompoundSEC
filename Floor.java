import java.util.Map;
import java.util.HashMap;
import java.util.Set;

class Floor {
    private FloorLevel floorLevel;
    private Map<String, String> rooms;

    public Floor(FloorLevel floorLevel) {
        this.floorLevel = floorLevel;
        this.rooms = new HashMap<>();
    }

    public void addRoom(String roomId) {
        rooms.put(roomId, roomId);
        System.out.println("Room add/put "+roomId);
        AuditTrail.logActionRoom(roomId , Action.CREATE, "Create room in " + floorLevel + " floor");
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId, roomId);
        System.out.println("Room remove "+roomId);
        AuditTrail.logActionRoom(roomId , Action.REMOVE, "Remove room in " + floorLevel + " floor");
    }

    public void listRoom() {
        // Get the set of keys (room IDs)
        Set<String> roomIds = rooms.keySet();

        if (roomIds.isEmpty()) {
            System.out.println("No rooms on this floor.");
            return; // Exit early if no rooms
        }

        System.out.println("Rooms on Floor " + floorLevel + ":"); // Indicate the floor

        for (String roomId : roomIds) {
            System.out.println(roomId); // Print each room ID
        }
    }

    public boolean hasRoom(String roomId) {
        return rooms.containsKey(roomId);
    }

    public FloorLevel getFloorLevel() {
        return floorLevel;
    }
}
