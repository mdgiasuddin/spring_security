package com.example.spring_security_demo.services;

import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.datasource.Student;
import com.example.spring_security_demo.dtos.StudentDTO;
import com.example.spring_security_demo.repositories.StudentRepository;
import com.example.spring_security_demo.utils.ClassOptionUtils;
import com.example.spring_security_demo.utils.ExcelFormattingUtils;
import com.itextpdf.text.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {
    private final StudentRepository studentRepository;
    private final ExcelFormattingUtils excelFormattingUtils;
    private final ClassOptionUtils classOptionUtils;
    private final PdfFileGenerationService pdfFileGenerationService;
    private final WatermarkPdfGeneration watermarkPdfGeneration;

    public Object saveStudent(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "Wrong Excel Format!";
        }


        try {
            InputStream inputStream = multipartFile.getInputStream();
            XSSFRow row;

            String originalFileName = multipartFile.getOriginalFilename();
            System.out.println("File Name : " + originalFileName);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            int colNumber;

            if (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                colNumber = row.getPhysicalNumberOfCells();
                if (colNumber != 4) {
                    return "Wrong Excel Format!";
                }

                List<StudentDTO> studentDTOList = new ArrayList<>();

                while (rowIterator.hasNext()) {
                    row = (XSSFRow) rowIterator.next();

                    String studentName = excelFormattingUtils.getStringFromAllCellType(row.getCell(0));
                    String schoolName = excelFormattingUtils.getStringFromAllCellType(row.getCell(1));
                    String classId = excelFormattingUtils.getStringFromAllCellType(row.getCell(2));
                    Long schoolRollNo = excelFormattingUtils.getIntegerFromAllCellType(row.getCell(3)).longValue();

                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setName(studentName);
                    studentDTO.setSchoolName(schoolName);
                    studentDTO.setClassId(classId);
                    studentDTO.setSchoolRollNo(schoolRollNo);

                    studentDTOList.add(studentDTO);
                }

                List<StudentDTO> sortedStudent = sortStudent(studentDTOList);

                saveStudentToDatabase(studentDTOList);

                return "Right Excel Format!";

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Wrong Excel Format!";
    }

    public List<StudentDTO> sortStudent(List<StudentDTO> studentDTOList) {
        List<StudentDTO> resultList = new ArrayList<>();
        Random random = new Random();

        int listLength = studentDTOList.size();
        while (listLength > 0) {
            int count = 0;
            while (true) {
                int randIndex = random.nextInt(listLength);
                System.out.println("Random number : " + randIndex);
                if (resultList.size() == 0 || !resultList.get(resultList.size() - 1).getSchoolName().equals(studentDTOList.get(randIndex).getSchoolName())) {
                    resultList.add(studentDTOList.get(randIndex));

                    StudentDTO tempStudent = studentDTOList.get(randIndex);
                    studentDTOList.set(randIndex, studentDTOList.get(listLength - 1));
                    studentDTOList.set(listLength - 1, tempStudent);
                    listLength--;

                    count = 0;
                    break;
                }
                count++;

                if (count > 200)
                    break;
            }

            if (count > 200)
                break;

        }
        for (int i = 0; i < listLength; i++) {
            StudentDTO student = studentDTOList.get(i);

            for (int j = 1; j < resultList.size(); j++) {
                if (!resultList.get(j - 1).getSchoolName().equals(student.getSchoolName()) && !resultList.get(j).getSchoolName().equals(student.getSchoolName())) {
                    resultList.add(j, student);
                    break;
                }
            }
        }
        return resultList;
    }

    public void saveStudentToDatabase(List<StudentDTO> studentDTOList) {
        if (studentDTOList.size() == 0)
            return;

        StudentDTO firstStudent = studentDTOList.get(0);

        Map<String, String> map = classOptionUtils.getOptionsOfClass(firstStudent.getClassId());

        Long startingRollNo = Long.parseLong(map.get("startingRollNo"));
        Long startingRegNo = Long.parseLong(map.get("startingRegNo"));
        Long increasingRegNo = Long.parseLong(map.get("increasingRegNo"));

        Random random = new Random();

        int i = 0;
        for (StudentDTO studentDTO : studentDTOList) {
            Student student = new Student();
            student.setName(studentDTO.getName());
            student.setSchoolName(studentDTO.getSchoolName());
            student.setClassId(studentDTO.getClassId());
            student.setSchoolRollNo(studentDTO.getSchoolRollNo());
            Long rollNo = startingRollNo + i;
            Long regNo = (startingRegNo * 10000) + ((1 + random.nextInt(9)) * 1000) + increasingRegNo + i;
            student.setRollNo(rollNo);
            student.setRegNo(regNo);
            i++;

            studentRepository.save(student);

        }
    }

    public void generateAdmitCard(String classId) throws Exception {
        List<Student> studentList = studentRepository.findByClassIdOrderByRollNo(classId);
        Map<String, String> map = classOptionUtils.getOptionsOfClass(classId);

        String admitCardFileName = ConstantsClass.INPUT_OUTPUT_FILE_DIRECTORY + map.get("admitCards");
        String watermarkAdmitCard = ConstantsClass.INPUT_OUTPUT_FILE_DIRECTORY + map.get("watermarkAdmitCards");
        pdfFileGenerationService.generateAdmitCard(studentList, admitCardFileName);

        Thread.sleep(2000);

        Image logoImage = Image.getInstance(ConstantsClass.AMAR_AMI_LOGO);
        watermarkPdfGeneration.addWaterMarkToPdf(admitCardFileName, watermarkAdmitCard, logoImage, 350, 350, 0.1f);
    }


    //    @Scheduled(fixedRate = 5000)
    public void createStudent() {
        List<Student> studentList = Arrays.asList(
                new Student("Gias Uddin", "Betbaria Secondary School", 1L, "Ten", 123456L, 234567L, 56.0),
                new Student("Sobuj Ahmed", "Pirtola Secondary School", 1L, "Eight", 123457L, 234568L, 60.0),
                new Student("Biplob Hossain", "Nouda Para Secondary School", 1L, "Ten", 123458L, 234569L, 62.0),
                new Student("Rony Islam", "HB Secondary School", 1L, "Ten", 123459L, 234570L, 58.0),
                new Student("Parvez Ahmed", "Betbaria Secondary School", 1L, "Ten", 123460L, 234571L, 59.0),
                new Student("Rubel Ahmed", "Pirtola Secondary School", 1L, "Five", 123461L, 234572L, 70.0),
                new Student("Riaz Ahmed", "Nouda Para Secondary School", 1L, "Ten", 123462L, 234573L, 81.0),
                new Student("Rabby Ahmed", "HB Secondary School", 1L, "Eight", 123463L, 234574L, 76.0)
        );

        studentRepository.saveAll(studentList);
    }

    public Page<Student> filterStudentBySearch(Map map, Pageable pageable) {
        return studentRepository.filterBySearch(map.get("name"), map.get("schoolName"), map.get("schoolRollNo"), pageable);
    }

    public long countStudentBySearch(Map map) {
        return studentRepository.countByName(map.get("name"));
    }
}
