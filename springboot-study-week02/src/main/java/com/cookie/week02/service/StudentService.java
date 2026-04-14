package com.cookie.week02.service;



import com.cookie.week02.entity.Phone;
import com.cookie.week02.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class StudentService {
    private static final Map<Long, Student> STUDENT_DATA = new ConcurrentHashMap<>();

    static {
        Student student1 = Student.builder()
                .id(1001L)
                .name("张三")
                .gender("男")
                .birthday(LocalDate.of(1999, 1, 1))
                .phone(Phone.builder().band("iphone").price(9999.99).color("black").build())
                .build();
        Student student2 = Student.builder()
                .id(1002L)
                .name("张三丰")
                .gender("男")
                .birthday(LocalDate.of(1998, 2, 2))
                .phone(Phone.builder().band("huawei").price(8888.88).color("white").build())
                .build();
        STUDENT_DATA.put(student1.getId(), student1);
        STUDENT_DATA.put(student2.getId(), student2);
    }

    public void createStudent(Student student) {
        STUDENT_DATA.put(student.getId(), student);
    }

    public Student getStudentById(Long id) {
        return STUDENT_DATA.get(id);
    }

    public Student getStudentByName(String name) {
        return STUDENT_DATA.values().stream()
                .filter(student -> student.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Iterable<Student> getAllStudents() {
        return STUDENT_DATA.values();
    }

    public void updateStudentById(Long id, Student student) {
        STUDENT_DATA.put(id, student);
    }

    public void deleteStudentById(Long id) {
        STUDENT_DATA.remove(id);
    }
}
