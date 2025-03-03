import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

class AccessCard {
    private String cardName;
    private String cardId;
    private Set<FloorLevel> allowedFloors;
    private Set<String> allowedRooms;
    private LocalDateTime expiryTime = null;

    public AccessCard(String cardName) {
        this.cardName = cardName;
        this.cardId = UUID.randomUUID().toString() +"_"+ LocalDateTime.now();
        this.allowedFloors = new HashSet<>();
        this.allowedRooms = new HashSet<>();
    }

    public String getCardName() {
        return cardName;
    }
    public String getCardId() {return  cardId;}

    public void addFloorAccess(FloorLevel floorLevel) {
        allowedFloors.add(floorLevel);
        AuditTrail.logActionCard(cardName, cardId, Action.MODIFY, "Added floor access: " + floorLevel);
    }

    public void revokeFloorAccess(FloorLevel floorLevel) {
        allowedFloors.remove(floorLevel);
        AuditTrail.logActionCard(cardName, cardId, Action.REVOKE, "Revoked floor access: " + floorLevel);
    }

    public void addRoomAccess(String roomId) {
        allowedRooms.add(roomId);
        AuditTrail.logActionCard(cardName, cardId, Action.MODIFY, "Added room access: " + roomId);
    }

    public void revokeRoomAccess(String roomId) {
        allowedRooms.remove(roomId);
        AuditTrail.logActionCard(cardName, cardId, Action.REVOKE, "Revoked room access: " + roomId);
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
        AuditTrail.logActionCard(cardName, cardId, Action.SET, "Expiry time set: " + expiryTime);
    }

    public boolean hasAccess(FloorLevel floorLevel, String roomId) {
        if (expiryTime != null) { // Only check expiry if it's set
            if (LocalDateTime.now().isAfter(expiryTime)) {
                return false; // Expired
            }
        } // If expiryTime is null, it skips this block entirely (allowing access)
        return allowedFloors.contains(floorLevel) && allowedRooms.contains(roomId);
    }

    public static void modifyCard(AccessCard card, Scanner input, AccessControlSystem acSystem) {
        System.out.println("-Modifying Card " + card.getCardName()+" - "+card.getCardId());

        int choice;
        do {
            System.out.println();
            System.out.println("1. Add Floor Access");
            System.out.println("2. Revoke Floor Access");
            System.out.println("3. Add Room Access");
            System.out.println("4. Revoke Room Access");
            System.out.println("5. Set Expiry Time");
            System.out.println("0. Done");
            System.out.println();
            System.out.print("Choose option: ");
            choice = input.nextInt();
            input.nextLine(); // Consume newline
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Floor Level (LOW, MEDIUM, HIGH): ");
                    String floorLevelStr = input.nextLine().toUpperCase();
                    try {
                        FloorLevel floorLevel = FloorLevel.valueOf(floorLevelStr);
                        card.addFloorAccess(floorLevel);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Floor Level.");
                    }
                    break;
                case 2:
                    System.out.print("Floor Level to remove (LOW, MEDIUM, HIGH): ");
                    floorLevelStr = input.nextLine().toUpperCase();
                    try {
                        FloorLevel floorLevel = FloorLevel.valueOf(floorLevelStr);
                        card.revokeFloorAccess(floorLevel);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Floor Level.");
                    }
                    break;
                case 3:
                    System.out.print("Room ID: ");
                    String roomId = input.nextLine();
                    card.addRoomAccess(roomId);
                    break;
                case 4:
                    System.out.print("Room ID to remove: ");
                    roomId = input.nextLine();
                    card.revokeRoomAccess(roomId);
                    break;
                case 5:
                    System.out.print("Expiry Time (yyyy-MM-dd HH:mm): ");
                    String expiryTimeStr = input.nextLine();
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime expiryTime = LocalDateTime.parse(expiryTimeStr, formatter);
                        card.setExpiryTime(expiryTime);
                        System.out.println("Expiry time set to: " + formatter.format(expiryTime)); // Confirmation
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date/time format. Please use yyyy-MM-dd HH:mm");
                    }
                    break;
                case 0:
                    System.out.println("Card modifications complete.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println();
        } while (choice != 0);
        acSystem.addAccessCard(card);
    }
}