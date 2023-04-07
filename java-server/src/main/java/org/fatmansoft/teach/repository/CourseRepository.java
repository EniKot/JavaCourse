package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Course 数据操作接口，主要实现Course数据的查询操作
 */

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query(value = "select max(courseId) from Course  ")
    Integer getMaxId();

    Optional<Course> findByCourseId(Integer courseId);

    Optional<Course> findByNum(String num);

    List<Course> findByName(String name);

    List<Course> findByTeacherTeacherId(Integer teacherId);

    @Query(value= "select * from course where student_id in (select student_id from student_course where student_id=?1)",nativeQuery = true)
    List<Course> findByStudentId(String studentId);

}
