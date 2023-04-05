package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 请假记录实体类
 */



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="absence",
        uniqueConstraints = {
        })
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer absenceId;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    private String reasons;

    private String startTime;

    private String endTime;
}
