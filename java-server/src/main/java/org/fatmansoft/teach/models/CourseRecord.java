package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="course_record",
        uniqueConstraints = {
        })
public class CourseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    @OneToOne
    @JoinColumn(name="course_id")
    private Course course;
    private String day;


    @OneToMany
    @JoinTable(name="course_absence",
            joinColumns = @JoinColumn(name="record_id"),
            inverseJoinColumns = @JoinColumn(name="student_id"))
    List<Student> absence;

}
