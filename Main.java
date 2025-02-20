import java.time.LocalDateTime;

enum FloorLevel {
    LOW,
    MEDIUM,
    HIGH
}

enum Action {
    GRANT, REVOKE, MODIFY, ACCESS
}

public class Main {
    public static void main(String[] args) {
        // สร้าง Floor
        Floor lowFloor = new Floor(FloorLevel.LOW);
        lowFloor.addRoom("L101");
        lowFloor.addRoom("L102");

        Floor highFloor = new Floor(FloorLevel.HIGH);
        highFloor.addRoom("H101");

        // สร้างระบบ AccessControlSystem
        AccessControlSystem acSystem = new AccessControlSystem();
        acSystem.addFloor(lowFloor);
        acSystem.addFloor(highFloor);

        // สร้าง AccessCard
        AccessCard card1 = new AccessCard("C001");
        card1.addFloorAccess(FloorLevel.LOW);
        card1.addRoomAccess("L101");
        card1.setExpiryTime(LocalDateTime.now().plusHours(1));

        // เพิ่มการ์ดในระบบ
        acSystem.addAccessCard(card1);

        // ทดสอบการเข้าถึง
        acSystem.checkAccess("C001", FloorLevel.LOW, "L101"); // ควรอนุญาต
        acSystem.checkAccess("C001", FloorLevel.HIGH, "H101"); // ควรปฏิเสธ

        // แสดงบันทึก Audit
        AuditTrail.printAuditLogs();
    }
}
