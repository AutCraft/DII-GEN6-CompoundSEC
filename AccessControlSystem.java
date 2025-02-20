import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

class AccessControlSystem {
    private List<Floor> floors;
    private Map<String, AccessCard> accessCards;

    public AccessControlSystem() {
        floors = new ArrayList<>();
        accessCards = new HashMap<>();
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void addAccessCard(AccessCard card) {
        accessCards.put(card.getCardId(), card);
        AuditTrail.logAction(card.getCardId(), Action.GRANT, "Card created and added.");
    }

    public boolean checkAccess(String cardId, FloorLevel floorLevel, String roomId) {
        AccessCard card = accessCards.get(cardId);
        if (card == null) {
            System.out.println("Card not found!");
            return false;
        }
        boolean accessGranted = card.hasAccess(floorLevel, roomId);
        if (accessGranted) {
            AuditTrail.logAction(cardId, Action.ACCESS, "Access granted to floor: " + floorLevel + ", room: " + roomId);
        } else {
            AuditTrail.logAction(cardId, Action.ACCESS, "Access denied to floor: " + floorLevel + ", room: " + roomId);
        }
        return accessGranted;
    }

    public void revokeCard(String cardId) {
        if (accessCards.containsKey(cardId)) {
            accessCards.remove(cardId);
            AuditTrail.logAction(cardId, Action.REVOKE, "Card revoked.");
        }
    }
}
