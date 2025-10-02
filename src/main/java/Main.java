
import dao.StudentDAO;
import dao.InstructorDAO;
import model.Student;
import model.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aikidoPU");
        EntityManager em = emf.createEntityManager();

        // Start a transaction
        em.getTransaction().begin();

        // Initialize DAO classes
        StudentDAO studentDAO = new StudentDAO(em);
        InstructorDAO instructorDAO = new InstructorDAO(em);

        // Add sample students
        Student student1 = new Student("John Doe", "john@example.com", "White Belt", LocalDate.now());
        Student student2 = new Student("Jane Smith", "jane@example.com", "Yellow Belt", LocalDate.now());

        // Save students using the DAO (no need to call em.persist directly)
        studentDAO.save(student1);
        studentDAO.save(student2);

        // Add an instructor
        Instructor instructor = new Instructor("Sensei Aki", "Aikido Throws", 10);
        instructorDAO.save(instructor);

        // Commit the transaction
        em.getTransaction().commit();

        // Fetch and print students
        List<Student> students = studentDAO.findAll();
        System.out.println("Students:");
        students.forEach(s -> {
            System.out.println(s.getName() + " - " + s.getRank());
            System.out.println("Created At: " + s.getCreatedAt());
            System.out.println("Membership Duration: " + s.getMembershipDuration() + " years");
        });

        // Fetch and print instructors
        List<Instructor> instructors = instructorDAO.findAll();
        System.out.println("Instructors:");
        instructors.forEach(i -> System.out.println(i.getName() + " - " + i.getSpecialization()));

        // Close the EntityManager and EntityManagerFactory
        em.close();
        emf.close();
    }
}
