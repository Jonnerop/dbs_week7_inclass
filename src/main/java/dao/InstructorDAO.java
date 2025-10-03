package dao;

import model.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public class InstructorDAO extends GenericDAO<Instructor> {

    public InstructorDAO(EntityManager entityManager) {
        super(Instructor.class, entityManager);
    }

    public List<Instructor> findBySpecialization(String technique) {
        return entityManager.createQuery(
                        "SELECT i FROM Instructor i WHERE LOWER(i.specialization) = LOWER(:tech)",
                        Instructor.class)
                .setParameter("tech", technique)
                .getResultList();
    }

    public List<Instructor> experienceMoreThanFive() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Instructor> cq = cb.createQuery(Instructor.class);
        Root<Instructor> root = cq.from(Instructor.class);
        cq.select(root).where(cb.greaterThan(root.get("experienceYears"), 5));
        return entityManager.createQuery(cq).getResultList();
    }
}
