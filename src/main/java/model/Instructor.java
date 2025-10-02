package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private int experienceYears;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<TrainingSession> trainingSessions;

    public Instructor() {}

    public Instructor(String name, String specialization, int experienceYears) {
        this.name = name;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
    }

    public boolean getName() {
        return name != null;
    }

    public Object getSpecialization() {
        return specialization;
    }

    // Getters and Setters
}
