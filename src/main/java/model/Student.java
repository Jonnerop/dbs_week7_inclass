package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import model.enums.Rank;
import model.converters.RankConverter;
import model.Attendance;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Convert(converter = RankConverter.class)
    @Column(nullable = false)
    private Rank rank;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private int membershipDuration;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    public Student() {}

    public Student(String name, String email, Rank rank, LocalDate joinDate) {
        this.name = name;
        this.email = email;
        this.rank = rank;
        this.joinDate = joinDate;
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
    protected void calculateMembershipDuration() {
        this.membershipDuration = LocalDate.now().getYear() - joinDate.getYear();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Rank getRank() { return rank; }
    public LocalDate getJoinDate() { return joinDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public int getMembershipDuration() { return membershipDuration; }
    public List<Attendance> getAttendances() { return attendances; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRank(Rank rank) { this.rank = rank; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public void setAttendances(List<Attendance> attendances) { this.attendances = attendances; }
}
