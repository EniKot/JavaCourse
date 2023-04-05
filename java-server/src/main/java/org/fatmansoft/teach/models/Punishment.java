package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 记录学生处分信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="punishment")
public class Punishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer punishmentId;

    @OneToOne
    @JoinColumn(name="student_id")
    private Student student;

    private String reason;

    private String kind;
}
