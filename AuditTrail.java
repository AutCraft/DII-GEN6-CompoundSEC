import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

class AuditTrail {
    private static List<String> logs = new ArrayList<>();

    // บันทึกการกระทำ
    public static void logActionCard(String cardName, String cardId, Action action, String details) {
        String censoredCardId = cardId.substring(0, 3) + "********"; // สร้าง cardId ที่เซ็นเซอร์ไว้
        String cardIdToLog = censoredCardId; // ค่าเริ่มต้นคือใช้แบบเซ็นเซอร์
        String log;

        if (action == Action.GRANT) {
            cardIdToLog = cardId; // ถ้า action เป็น GRANT ให้ใช้ cardId แบบเต็ม
            //System.out.println("Card Id: "+cardId); // แสดง cardId เต็มใน Console (ตามโค้ดเดิม)
            log = LocalDateTime.now() + " - Card Name: " + cardName + " Card Id: " + cardIdToLog + " Action: " + action + " " + details;
            System.out.println(log); // แสดง log ใน Console
            cardIdToLog = censoredCardId; // ค่าเริ่มต้นคือใช้แบบเซ็นเซอร์
            log = LocalDateTime.now() + " - Card Name: " + cardName + " Card Id: " + cardIdToLog + " Action: " + action + " " + details;
        } else {
            log = LocalDateTime.now() + " - Card Name: " + cardName + " Card Id: " + cardIdToLog + " Action: " + action + " " + details;
            System.out.println(log); // แสดง log ใน Console
        }
        logs.add(log);
    }

    // บันทึกการกระทำ
    public static void logActionRoom(String Room, Action action, String details) {
        String log = LocalDateTime.now() + " - Name: " + Room +" Action: " + action + " " + details;
        logs.add(log);
        System.out.println(log); // แสดงใน Console
    }

    // แสดงการบันทึกทั้งหมด
    public static void printAuditLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
}