package com.example.spring_security_demo.angular;

import com.example.spring_security_demo.dtos.StudentDTO;
import com.example.spring_security_demo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AngularApiService {

    private final StudentRepository studentRepository;

    public List<StudentDTO> getAllStudent() {
        return studentRepository.findAll().stream().map(StudentDTO::new).collect(Collectors.toList());
    }
}
