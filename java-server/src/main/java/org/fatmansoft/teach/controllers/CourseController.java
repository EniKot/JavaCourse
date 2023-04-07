package org.fatmansoft.teach.controllers;


import org.fatmansoft.teach.models.Course;
import org.fatmansoft.teach.models.Person;
import org.fatmansoft.teach.models.Teacher;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.CourseRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.repository.UserTypeRepository;
import org.fatmansoft.teach.util.ComDataUtil;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;  //学生数据操作自动注入
    @Autowired
    private UserTypeRepository userTypeRepository; //用户类型数据操作自动注入
    @Autowired
    private PasswordEncoder encoder;  //密码服务自动注入



    public synchronized Integer getNewCourseId(){  //synchronized 同步方法
        Integer  id = courseRepository.getMaxId();  // 查询最大的id
        if(id == null)
            id = 1;
        else
            id = id+1;
        return id;
    }

    public Map getMapFromCourse(Course c){
        Map m = new HashMap();

        m.put("name",c.getName());
        m.put("num",c.getNum());
        Teacher t=c.getTeacher();
        if(t == null){
            return m;
        }
        m.put("teacherName",t.getPerson().getName());
        Course preCourse=c.getPreCourse();
        if(preCourse == null){
            m.put("preCourseNum","");
        }else{
            m.put("preCourseNum",c.getPreCourse().getNum());
        }


        m.put("textBook",c.getTextBook());
        m.put("startWeek",c.getStartWeek());
        m.put("endWeek",c.getEndWeek());
        m.put("credit",c.getCredit());
        return m;
    }


    @PostMapping("/getCourseScoreList")
    public DataResponse getScoreList(@Valid @RequestBody DataRequest dataRequest) {
        return null;
    }


    @PostMapping("/getCourseList")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StreamingResponseBody> getCourseList(@Valid @RequestBody DataRequest dataRequest) {
        try {
            List<Course> cList = courseRepository.findAll();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(out);
            oo.writeObject(cList);
            oo.close();
            byte [] data = out.toByteArray();
            MediaType mType = new MediaType(MediaType.APPLICATION_OCTET_STREAM);
            StreamingResponseBody stream = outputStream -> {
                outputStream.write(data);
            };
            return ResponseEntity.ok()
                    .contentType(mType)
                    .body(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
