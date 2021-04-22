package com.example.spring_security_demo.datasource;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "team")
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "manager_name")
    private String managerName;

    public Team(String name, String country, String coachName, String managerName) {
        this.name = name;
        this.country = country;
        this.coachName = coachName;
        this.managerName = managerName;
    }
}
