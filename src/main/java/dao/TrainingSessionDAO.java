package dao;

import model.TrainingSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public class TrainingSessionDAO extends GenericDAO<TrainingSession> {

    public TrainingSessionDAO(EntityManager entityManager) {
        super(TrainingSession.class, entityManager);
    }

    public List<TrainingSession> inLocation(String location) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingSession> cq = cb.createQuery(TrainingSession.class);
        Root<TrainingSession> root = cq.from(TrainingSession.class);
        cq.select(root).where(cb.equal(cb.lower(root.get("location")), location.toLowerCase()));
        return entityManager.createQuery(cq).getResultList();
    }
}
