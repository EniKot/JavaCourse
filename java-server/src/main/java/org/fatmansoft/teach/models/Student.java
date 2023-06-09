package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Student学生表实体类 保存每个学生的信息，
 * Integer studentId 用户表 student 主键 student_id
 * Person person 关联到该用户所用的Person对象，账户所对应的人员信息 person_id 关联 person 表主键 person_id
 * String major 专业
 * String className 班级
 *
 */
@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(	name = "student",
        uniqueConstraints = {
        })
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    @Size(max = 20)
    private String major;

    @Size(max = 50)
    private String className;

    private String politicalStatus;

    @ManyToMany(mappedBy = "students")
    @JoinTable(name="student_course",joinColumns = @JoinColumn(name= "student_id"),inverseJoinColumns = @JoinColumn(name="course_id"))
    List<Course> courses;
    //关系人1的信息 ：名字、与学生本人的关系、联系方式
    private String firstRelName;
    private String firstRel;
    private String firstTel;
    //关系人2的信息 ：名字、与学生本人的关系、联系方式
    private String secondRelName;
    private String secondRel;
    private String secondTel;
}
