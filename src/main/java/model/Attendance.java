package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import model.enums.AttendanceStatus;
import model.converters.AttendanceStatusConverter;

@Entity
@Table(name = "attendances", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","session_id"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private TrainingSession session;

    @Convert(converter = AttendanceStatusConverter.class)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private boolean loaded;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostLoad
    protected void afterLoad() {
        loaded = true;
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public TrainingSession getSession() { return session; }
    public AttendanceStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isLoaded() { return loaded; }

    public void setStudent(Student student) { this.student = student; }
    public void setSession(TrainingSession session) { this.session = session; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
}
