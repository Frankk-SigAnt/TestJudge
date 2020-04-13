public class ClassRoomRuntimeTest {
    public static void main(String[] args) {
        ClassRoom room = new ClassRoom();
        System.out.println("Test printClassRoom():");
        room.printClassRoom();
        System.out.println("Test printValidForExam():");
        room.printValidForExam(2, 3, 3);
    }
}