package dao;

import model.Student;
import model.TrainingSession;
import model.enums.Rank;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class StudentDAO extends GenericDAO<Student> {

    public StudentDAO(EntityManager entityManager) {
        super(Student.class, entityManager);
    }

    public List<TrainingSession> sessionsForStudent(Long studentId) {
        return entityManager.createQuery(
                        "SELECT a.session FROM Attendance a WHERE a.student.id = :sid",
                        TrainingSession.class)
                .setParameter("sid", studentId)
                .getResultList();
    }

    public List<Student> findByRank(Rank rank) {
        return entityManager.createQuery(
                        "SELECT s FROM Student s WHERE s.rank = :rank",
                        Student.class)
                .setParameter("rank", rank)
                .getResultList();
    }

    public List<Student> withReportsLastThreeMonths() {
        LocalDate cutoff = LocalDate.now().minusMonths(3);
        return entityManager.createQuery(
                        "SELECT DISTINCT s FROM ProgressReport pr JOIN pr.student s WHERE pr.reportDate >= :cutoff",
                        Student.class)
                .setParameter("cutoff", cutoff)
                .getResultList();
    }

    public List<Student> joinedLastSixMonths() {
        LocalDate cutoff = LocalDate.now().minusMonths(6);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);
        cq.select(root).where(cb.greaterThanOrEqualTo(root.get("joinDate"), cutoff));
        return entityManager.createQuery(cq).getResultList();
    }
}
