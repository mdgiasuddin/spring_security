package com.example.spring_security_demo.services;

import com.example.spring_security_demo.common.ConstantsClass;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CSVGenerationService {

    public void readFile() {
        try {
            String fileName = ConstantsClass.STATIC_RESOURCES_DIRECTORY + "CBOE.csv";
            FileReader filereader = new FileReader(fileName);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFileFromUpload(final MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader csvReader = new CSVReader(inputStreamReader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createFile() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        try {
            CSVWriter writer = new CSVWriter(outputStreamWriter);

            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"Name", "Class", "Marks"});
            data.add(new String[]{"Aman", "10", "620"});
            data.add(new String[]{"Suraj", "10", "630"});
            writer.writeAll(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(inputStream);
    }
}
