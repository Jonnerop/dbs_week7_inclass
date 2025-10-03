package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_reports")
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private LocalDate reportDate;

    @Column(nullable = false, length = 2000)
    private String achievements;

    @Column(nullable = false, length = 2000)
    private String areasForImprovement;

    @Version
    private int version;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private boolean loaded;

    public ProgressReport() {}

    public ProgressReport(Student student, LocalDate reportDate, String achievements, String areasForImprovement) {
        this.student = student;
        this.reportDate = reportDate;
        this.achievements = achievements;
        this.areasForImprovement = areasForImprovement;
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
    public Student getStudent() { return student; }
    public LocalDate getReportDate() { return reportDate; }
    public String getAchievements() { return achievements; }
    public String getAreasForImprovement() { return areasForImprovement; }
    public int getVersion() { return version; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isLoaded() { return loaded; }

    public void setStudent(Student student) { this.student = student; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
    public void setAchievements(String achievements) { this.achievements = achievements; }
    public void setAreasForImprovement(String areasForImprovement) { this.areasForImprovement = areasForImprovement; }
}
