import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
        Scanner input = new Scanner(System.in);

        boolean exit = true;
        String choose = "0";

        Floor lowFloor = new Floor(FloorLevel.LOW);
        Floor mediumFloor = new Floor(FloorLevel.MEDIUM);
        Floor highFloor = new Floor(FloorLevel.HIGH);
        List<AccessCard> cards = new ArrayList<>();
        AccessControlSystem acSystem = new AccessControlSystem();

        while (exit){
            System.out.println("-COMPOUND SECURITY-");
            System.out.println("1. Edit Room");
            System.out.println("2. Edit Card");
            System.out.println("3. Access Card/Room");
            System.out.println("4. Log");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Choose your number 0-4: ");
            choose = input.next();
            switch (choose){
                case "1" :
                    String in_choose1 = "0";
                    System.out.println();
                    System.out.println("-EDIT ROOM-");
                    System.out.println("1. Add Room");
                    System.out.println("2. Remove Room");
                    System.out.println("3. List Room");
                    System.out.println("0. Exit/Return");
                    System.out.println();
                    System.out.print("Choose your number 0-3: ");
                    in_choose1 = input.next();
                    System.out.println();
                    switch (in_choose1){
                        case "1" :
                            System.out.println("-ADD ROOM-");
                            System.out.println("Choose floor room");
                            System.out.println("1. Low");
                            System.out.println("2. Medium");
                            System.out.println("3. High");
                            String size1 = input.next();
                            System.out.print("Room id :");
                            String id1 = input.next();
                            switch (size1.toUpperCase()){
                                case "1","LOW": lowFloor.addRoom(id1); break;
                                case "2","MEDIUM": mediumFloor.addRoom(id1); break;
                                case "3","HIGH": highFloor.addRoom(id1); break;
                                default: System.out.println("System: กรุณากรอกข้อมูลให้ถูกต้อง"); break;
                            }
                            System.out.println();
                            break;
                        case "2" :
                            System.out.println("-REMOVE ROOM-");
                            System.out.println("Choose floor room");
                            System.out.println("1. Low");
                            System.out.println("2. Medium");
                            System.out.println("3. High");
                            String size2 = input.next();
                            System.out.print("Room id :");
                            String id2 = input.next();
                            switch (size2.toUpperCase()){
                                case "1","LOW": lowFloor.removeRoom(id2); break;
                                case "2","MEDIUM": mediumFloor.removeRoom(id2); break;
                                case "3","HIGH": highFloor.removeRoom(id2); break;
                                default: System.out.println("System: กรุณากรอกข้อมูลให้ถูกต้อง"); break;
                            }
                            System.out.println();
                            break;
                        case "3" : lowFloor.listRoom(); mediumFloor.listRoom(); highFloor.listRoom();
                            System.out.println();
                            break;
                        case "0" : System.out.println(); break;
                        default: System.out.println("System: กรุณากรอกข้อมูลให้ถูกต้อง"); System.out.println(); break;
                    }
                    break;
                case "2" :
                    String in_choose2 = "0";
                    System.out.println();
                    System.out.println("-EDIT CARD-");
                    System.out.println("1. Add Card");
                    System.out.println("2. Remove Card");
                    System.out.println("3. List Card");
                    System.out.println("4. Modify Card");
                    System.out.println("0. Exit/Return");
                    System.out.println();
                    System.out.print("Choose your number 0-4: ");
                    in_choose2 = input.next();
                    System.out.println();
                    switch (in_choose2) {
                        case "1":
                            System.out.print("Card ID: ");
                            String cardId = input.next();
                            AccessCard newCard = new AccessCard(cardId);
                            cards.add(newCard);
                            acSystem.addAccessCard(newCard);
                            System.out.println("Card added successfully.");
                            System.out.println();
                            break;
                        case "2":
                            System.out.print("Card ID to remove: ");
                            String cardIdToRemove = input.next();
                            boolean removed = cards.removeIf(card -> card.getCardId().equals(cardIdToRemove));
                            if (removed) {
                                acSystem.revokeCard(cardIdToRemove);
                                System.out.println("Card removed successfully.");
                            } else {
                                System.out.println("Card not found.");
                            }
                            System.out.println();
                            break;
                        case "3":
                            if (cards.isEmpty()) {
                                System.out.println("No cards registered.");
                            } else {
                                System.out.println("Registered Cards:");
                                for (AccessCard card : cards) {
                                    System.out.println(card.getCardId());
                                }
                            }
                            System.out.println();
                            break;
                        case "4":
                            boolean checkcardid = false;
                            System.out.print("Card ID to modify: ");
                            String cardIdToModify = input.next();
                            for (AccessCard card : cards) {
                                if (card.getCardId().equals(cardIdToModify)) {
                                    checkcardid = true;
                                    acSystem.revokeCard(card.getCardId());
                                    AccessCard.modifyCard(card, input, acSystem); // Helper function to modify card details
                                    break; // Important: Exit the loop after finding the card
                                }
                            }
                            if(checkcardid == false){
                                System.out.println("Card not found.");
                            }
                            System.out.println();
                            break;
                        case "0": System.out.println();
                            break;
                        default:
                            System.out.println("System: กรุณากรอกข้อมูลให้ถูกต้อง");
                            System.out.println();
                            break;
                    }
                    break;
                case "3":
                    System.out.println();
                    System.out.println("-ACCESS CARD/ROOM-");

                    if (cards.isEmpty()) {
                        System.out.println("No cards registered.");
                        System.out.println();
                        break; // Exit the case if no cards
                    }

                    try {
                        System.out.print("Enter Card ID: ");
                        String cardId = input.next();

                        AccessCard selectedCard = null;
                        for (AccessCard card : cards) {
                            if (card.getCardId().equals(cardId)) {
                                selectedCard = card;
                                break;
                            }
                        }

                        if (selectedCard == null) {
                            System.out.println("Card not found.");
                            break;
                        }
                        //acSystem.addAccessCard(selectedCard);
                        //AccessCard selectedCard = cards.get(cardChoice - 1);

                        System.out.println("Available Floors:");
                        System.out.println("1. Low");
                        System.out.println("2. Medium");
                        System.out.println("3. High");

                        int floorChoice;
                        System.out.print("Choose floor number: ");
                        floorChoice = input.nextInt();
                        input.nextLine(); // Consume newline

                        FloorLevel selectedFloorLevel;
                        switch (floorChoice) {
                            case 1:
                                selectedFloorLevel = FloorLevel.LOW;
                                break;
                            case 2:
                                selectedFloorLevel = FloorLevel.MEDIUM;
                                break;
                            case 3:
                                selectedFloorLevel = FloorLevel.HIGH;
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid floor choice.");
                        }

                        Floor selectedFloor = null;
                        switch (selectedFloorLevel) {
                            case LOW: selectedFloor = lowFloor; break;
                            case MEDIUM: selectedFloor = mediumFloor; break;
                            case HIGH: selectedFloor = highFloor; break;
                        }

                        if (selectedFloor == null) {
                            throw new IllegalArgumentException("Invalid floor level.");
                        }

                        System.out.println("Available Rooms on " + selectedFloorLevel + " Floor:");
                        selectedFloor.listRoom();

                        System.out.print("Enter room ID: ");
                        String roomId = input.nextLine();

                        acSystem.checkAccess(cardId, selectedFloorLevel, roomId);

                        System.out.println();

                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        System.out.println();
                        input.nextLine(); // Clear the invalid input
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                    break;
                case "4" : AuditTrail.printAuditLogs();
                    System.out.println();
                    break;
                case "0" : exit=false; break;

                default: System.out.println("System: กรุณากรอกข้อมูลให้ถูกต้อง"); System.out.println(); break;
            }
        }
        /*
        // สร้าง Floor

        lowFloor.addRoom("L101");
        lowFloor.addRoom("L102");

        highFloor.addRoom("H101");
        highFloor.addRoom("H102");

        // สร้างระบบ AccessControlSystem
        //AccessControlSystem acSystem = new AccessControlSystem();
        acSystem.addFloor(lowFloor);
        acSystem.addFloor(mediumFloor);
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


         */

    }
}
