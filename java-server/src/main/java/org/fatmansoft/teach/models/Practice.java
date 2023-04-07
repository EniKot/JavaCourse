package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 创新实践类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="practice")
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer practiceId;

    private String title;
    private String content;

    @OneToMany
    @JoinColumn(name="student_id")
    private List<Student> students;
}
