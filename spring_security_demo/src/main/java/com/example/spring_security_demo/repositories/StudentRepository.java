package com.example.spring_security_demo.repositories;

import com.example.spring_security_demo.datasource.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
