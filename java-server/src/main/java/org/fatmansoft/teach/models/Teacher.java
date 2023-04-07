package org.fatmansoft.teach.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "teacher",
        uniqueConstraints = {
        })
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    private String school;

    private String institute;

    @OneToMany(mappedBy = "teacher")
    private List<Course> teaches;
}
