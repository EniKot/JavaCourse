package org.fatmansoft.teach.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 活动记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="activity",
        uniqueConstraints = {
        })
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    private String location;

    private String content;

    private String startTime;

    private String endTime;
}
