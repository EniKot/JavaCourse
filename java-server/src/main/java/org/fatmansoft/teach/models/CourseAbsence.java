package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 上课缺勤记录类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="assignment_absence",
        uniqueConstraints = {
        })
public class CourseAbsence  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    @OneToOne
    @JoinColumn(name="student_id")
    private Student student;
    @OneToOne
    @JoinColumn(name="course_record_id")
    private CourseRecord courseRecord;
}

