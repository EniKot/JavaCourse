package org.fatmansoft.teach.controllers;


import org.fatmansoft.teach.models.Course;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.CourseRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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

    public Map getMaoFromCourse(Course c){
        Map m=new HashMap();

    }

    @PostMapping("/getScoreList")
    public DataResponse getScoreList(@Valid @RequestBody DataRequest dataRequest) {
        return null;
    }


    @PostMapping("/getCourseList")
    @PreAuthorize(" hasRole('ADMIN')")
    public DataResponse getCourseList(@Valid @RequestBody DataRequest dataRequest){

    }
}
