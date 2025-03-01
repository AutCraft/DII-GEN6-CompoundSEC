import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

class AuditTrail {
    private static List<String> logs = new ArrayList<>();

    // บันทึกการกระทำ
    public static void logActionCard(String cardName, String cardId, Action action, String details) {
        String log = LocalDateTime.now() + " - Card Name: " + cardName + " Card Id: " + cardId + " Action: " + action + " " + details;
        logs.add(log);
        System.out.println(log); // แสดงใน Console
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