package com.cookie.week02.controller;



import com.cookie.week02.entity.Student;
import com.cookie.week02.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * 创建学生
     *
     * @param student
     * @return
     */
    @PostMapping
    public String createStudent(@RequestBody Student student) {
        log.info("接收到创建学生参数: {}", student);
        studentService.createStudent(student);
        return "创建学生成功";
    }



    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        log.info("接收到查询学生ID: {}", id);
        return studentService.getStudentById(id);
    }



    /**
     * 根据姓名查询学生
     *
     * @param name
     * @return
     */
    @GetMapping
    public Student getStudentByName(@RequestParam String name) {
        log.info("接收到查询学生姓名: {}", name);
        return studentService.getStudentByName(name);
    }



    /**
     * 查询所有学生
     *
     * @return
     */
    @GetMapping("/all")
    public Iterable<Student> getAllStudents() {
        log.info("查询所有学生");
        return studentService.getAllStudents();
    }


    /**
     * 更新学生
     *
     * @param id
     * @param student
     * @return
     */
    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody Student student) {
        log.info("接收到更新学生ID: {}, 参数: {}", id, student);
        studentService.updateStudentById(id, student);
        return "更新学生成功";
    }


    /**
     * 删除学生
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        log.info("接收到删除学生ID: {}", id);
        studentService.deleteStudentById(id);
        return "删除学生成功";
    }
}
