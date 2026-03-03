import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static double calculateAverageGpa(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Student student : students) {
            sum += student.getGpa();
        }
        return sum / students.size();
    }

    public static void main(String[] args) {
        StudentManager<Student> manager = new StudentManager<>();

        manager.add(new Student("SV001", "Nguyen Van A", 3.8));
        manager.add(new Student("SV002", "Tran Thi B", 3.5));
        manager.add(new Student("SV003", "Le Van C", 3.0));
        manager.add(new Student("SV004", "Pham Thi D", 3.2));

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return calculateAverageGpa(manager.getAll());
        }).thenAccept(avgGpa -> {
            System.out.println("Điểm trung bình GPA của hệ thống là: " + avgGpa);
        }).exceptionally(ex -> {
            System.out.println("Lỗi trong quá trình tính toán: " + ex.getMessage());
            return null;
        });

        System.out.println("Luồng chính vẫn đang tiếp tục chạy mà không bị chặn...");