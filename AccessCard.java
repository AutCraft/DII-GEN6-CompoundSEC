import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

class AccessCard {
    private String cardId;
    private Set<FloorLevel> allowedFloors;
    private Set<String> allowedRooms;
    private LocalDateTime expiryTime;

    public AccessCard(String cardId) {
        this.cardId = cardId;
        this.allowedFloors = new HashSet<>();
        this.allowedRooms = new HashSet<>();
    }

    public String getCardId() {
        return cardId;
    }

    public void addFloorAccess(FloorLevel floorLevel) {
        allowedFloors.add(floorLevel);
        AuditTrail.logAction(cardId, Action.MODIFY, "Added floor access: " + floorLevel);
    }

    public void revokeFloorAccess(FloorLevel floorLevel) {
        allowedFloors.remove(floorLevel);
        AuditTrail.logAction(cardId, Action.REVOKE, "Revoked floor access: " + floorLevel);
    }

    public void addRoomAccess(String roomId) {
        allowedRooms.add(roomId);
        AuditTrail.logAction(cardId, Action.MODIFY, "Added room access: " + roomId);
    }

    public void revokeRoomAccess(String roomId) {
        allowedRooms.remove(roomId);
        AuditTrail.logAction(cardId, Action.REVOKE, "Revoked room access: " + roomId);
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean hasAccess(FloorLevel floorLevel, String roomId) {
        if (LocalDateTime.now().isAfter(expiryTime)) {
            return false;
        }
        return allowedFloors.contains(floorLevel) && allowedRooms.contains(roomId);
    }
}