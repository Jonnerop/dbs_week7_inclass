package dao;



import model.TrainingSession;
import jakarta.persistence.EntityManager;

public class TrainingSessionDAO extends GenericDAO<TrainingSession> {
    public TrainingSessionDAO(EntityManager entityManager) {
        super(TrainingSession.class, entityManager);
    }
}
