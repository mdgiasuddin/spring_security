package com.example.spring_security_demo.angular;

import com.example.spring_security_demo.dtos.AngularData;
import com.example.spring_security_demo.dtos.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/angular/api")
//@CrossOrigin(origins = "http://localhost:4100")
public class AngularApiController {

    private final AngularApiService angularApiService;

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AngularData> getData() {
        return Arrays.asList(
                new AngularData(1, "Giash", 25),
                new AngularData(2, "Sobuz", 28),
                new AngularData(3, "Biplob", 27),
                new AngularData(4, "Jonaed", 24),
                new AngularData(5, "Rony", 26)
        );
    }

    @GetMapping("/student/all")
    public List<StudentDTO> getAllStudent() {
        return angularApiService.getAllStudent();
    }
}
