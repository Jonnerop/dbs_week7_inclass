import dao.StudentDAO;
import dao.InstructorDAO;
import dao.TrainingSessionDAO;
import model.Student;
import model.Instructor;
import model.TrainingSession;
import model.Attendance;
import model.ProgressReport;
import model.enums.Rank;
import model.enums.AttendanceStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aikidoPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Instructor instructor = new Instructor("Sensei Aki", "Aikido Throws", 10);
        em.persist(instructor);

        String uniq = String.valueOf(System.currentTimeMillis());
        Student s1 = new Student("John Doe", "john+" + uniq + "@example.com", Rank.KYU_6, LocalDate.now());
        Student s2 = new Student("Jane Smith", "jane+" + uniq + "@example.com", Rank.KYU_5, LocalDate.now().minusMonths(2));
        em.persist(s1);
        em.persist(s2);

        TrainingSession session = new TrainingSession(LocalDate.now(), "Helsinki Dojo", 90, instructor);
        em.persist(session);

        Attendance a1 = new Attendance();
        a1.setStudent(s1);
        a1.setSession(session);
        a1.setStatus(AttendanceStatus.PRESENT);
        a1.setNotes("Good effort today");
        em.persist(a1);

        Attendance a2 = new Attendance();
        a2.setStudent(s2);
        a2.setSession(session);
        a2.setStatus(AttendanceStatus.LATE);
        a2.setNotes("Arrived a bit late");
        em.persist(a2);

        ProgressReport pr = new ProgressReport(s1, LocalDate.now(), "Making steady progress", "Work on balance");
        em.persist(pr);

        em.getTransaction().commit();

        StudentDAO studentDAO = new StudentDAO(em);
        InstructorDAO instructorDAO = new InstructorDAO(em);
        TrainingSessionDAO sessionDAO = new TrainingSessionDAO(em);

        List<TrainingSession> s1Sessions = studentDAO.sessionsForStudent(s1.getId());
        System.out.println("Sessions for John: " + s1Sessions.size());

        List<Student> kyus = studentDAO.findByRank(Rank.KYU_6);
        System.out.println("Students with KYU_6: " + kyus.size());

        List<Instructor> spec = instructorDAO.findBySpecialization("Aikido Throws");
        System.out.println("Instructors specialized in Aikido Throws: " + spec.size());

        List<Student> recentReports = studentDAO.withReportsLastThreeMonths();
        System.out.println("Students with reports in last 3 months: " + recentReports.size());

        List<Student> joined6m = studentDAO.joinedLastSixMonths();
        System.out.println("Students joined last 6 months: " + joined6m.size());

        List<TrainingSession> inHki = sessionDAO.inLocation("Helsinki Dojo");
        System.out.println("Sessions in Helsinki Dojo: " + inHki.size());

        List<Instructor> exp5 = instructorDAO.experienceMoreThanFive();
        System.out.println("Instructors with > 5 years: " + exp5.size());

        em.close();
        emf.close();
    }
}
