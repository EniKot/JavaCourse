package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
/**
 * Award 奖项荣誉类
 * 学生老师均有
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="award",
        uniqueConstraints = {
        })
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer awardId;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person persont;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher instructor;

    private String type;
    private String day;
}
