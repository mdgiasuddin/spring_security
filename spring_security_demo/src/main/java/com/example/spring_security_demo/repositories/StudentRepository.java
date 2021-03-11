package com.example.spring_security_demo.repositories;

import com.example.spring_security_demo.datasource.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByClassIdOrderByRollNo(String classId);

    @Query("select s from Student s where ( :name is null or s.name = :name ) and " +
            "( :schoolName is null or s.schoolName = :schoolName) and " +
            "( :schoolRollNo is null or s.schoolRollNo = :schoolRollNo )")
    Page<Student> filterBySearch(@Param("name") Object name, @Param("schoolName") Object schoolName
            , @Param("schoolRollNo") Object schoolRollNo, Pageable pageable);


    long countByName(Object name);
}
