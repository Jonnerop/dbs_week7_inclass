package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "training_sessions")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int duration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private boolean loaded;

    public TrainingSession() {}

    public TrainingSession(LocalDate date, String location, int duration, Instructor instructor) {
        this.date = date;
        this.location = location;
        this.duration = duration;
        this.instructor = instructor;
    }

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
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public int getDuration() { return duration; }
    public Instructor getInstructor() { return instructor; }
    public List<Attendance> getAttendances() { return attendances; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isLoaded() { return loaded; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public void setAttendances(List<Attendance> attendances) { this.attendances = attendances; }
}
