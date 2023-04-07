package org.fatmansoft.teach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/**
 * Fee 消费流水实体类  保存学生消费流水的基本信息信息，
 * Integer feeId 消费表 fee 主键 fee_id
 * Integer studentId  student_id 对应student 表里面的 student_id
 * String day 日期
 * Double money 金额
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "fee"
)
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feeId;

    private Integer studentId;
    private String day;
    private Double money;


}
