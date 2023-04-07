package org.fatmansoft.teach.models;

/**
 * 作业记录实体类
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentId;

    @OneToOne
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String contents;

    private String deadline;

    private String day;

    @OneToMany
    @JoinTable(name="assignment_absence",
            joinColumns = @JoinColumn(name="assignment_id"),
            inverseJoinColumns = @JoinColumn(name="student_id"))
    private List<Student> absence;
}
