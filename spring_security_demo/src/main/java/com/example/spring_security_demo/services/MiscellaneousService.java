package com.example.spring_security_demo.services;


import com.example.spring_security_demo.common.CommonException;
import com.example.spring_security_demo.dtos.BasicInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MiscellaneousService {

    private String formattingNumberString(String numString) {
        int beginIdx = 0, endIdx = numString.length() - 1;

        while (beginIdx <= endIdx && numString.charAt(beginIdx) == '0')
            beginIdx++;

        if (numString.contains("P")) {
            numString = numString + "00";
            int idxOfP = numString.indexOf('P');
            endIdx = idxOfP + 2;
        }

        if (beginIdx <= endIdx) {
            numString = numString.substring(beginIdx, endIdx + 1);
            if (numString.startsWith("P"))
                numString = "0" + numString;

            return numString;
        }
        return "0";
    }

    private void numberToWord(int number, String power, List<String> wordList) {
        if (number == 0)
            return;

        String[] singleDigit = new String[]{
                "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE"
                , "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"
        };

        String[] doubleDigit = new String[]{
                "", "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"
        };

        if (number < 20) {
            wordList.add(singleDigit[number]);
        } else {
            String string = doubleDigit[number / 10] + " " + singleDigit[number % 10];
            wordList.add(string.trim());
        }
        if (!power.isEmpty())
            wordList.add(power);
    }

    public List<String> numberToWord(int number) {
        List<String> wordList = new ArrayList<>();
        numberToWord(number / 100000, "LAC", wordList);
        numberToWord((number / 1000) % 100, "THOUSAND", wordList);
        numberToWord((number / 100) % 10, "HUNDRED", wordList);
        numberToWord(number % 100, "", wordList);

        return wordList;
    }

    public String numberToWord(String numString) {
        numString = numString.replace(".", "P");
        numString = formattingNumberString(numString);

        String[] splittedTkPaisa = numString.split("P");
        String tkPart = splittedTkPaisa[0];

        if (tkPart.length() > 14)
            return "Number Length Mustn't Be Greater Than 14!";

        List<String> wordList;
        if (tkPart.length() > 7) {
            int croreNumber = Integer.parseInt(tkPart.substring(0, tkPart.length() - 7));
            int otherNumber = Integer.parseInt(tkPart.substring(tkPart.length() - 7));

            wordList = numberToWord(croreNumber);
            wordList.add("CRORE");
            wordList.addAll(numberToWord(otherNumber));
        } else {
            wordList = numberToWord(Integer.parseInt(tkPart));
        }

        if (wordList.isEmpty())
            wordList.add("ZERO");

        wordList.add("TAKA");
        if (splittedTkPaisa.length == 2) {
            String paisaPart = splittedTkPaisa[1];
            List<String> paisaList = numberToWord(Integer.parseInt(paisaPart));
            if (!paisaList.isEmpty())
                paisaList.add("PAISA");

            wordList.addAll(paisaList);
        }

        return String.join(" ", wordList);
    }

    public Object mapTest() {
        try {
            BasicInfo basicInfo = new BasicInfo();

            Map map = new HashMap<>();
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyhhmma"));

            map.put("name", "Gias Uddin, Abc");
            map.put("address", "Betbaris : Abc");
            map.put("age", 26);
            map.put("currentTime", currentTime);

            basicInfo.setMap(map);
            basicInfo.setAddress("Betbaria, Gangni, Meherpur");

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(basicInfo);
            System.out.println("String ----> " + jsonString);

            BasicInfo jsonMap = new ObjectMapper().readValue(jsonString, BasicInfo.class);

            return jsonMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(e.getMessage());
        }
    }

    public Object dataSavings() {
        try {
            Map dataMap = new HashMap<>();
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyhhmma"));

            dataMap.put("name", "Gias Uddin");
            dataMap.put("age", 26);
            dataMap.put("gender", "M");

            Map addressMap = new LinkedHashMap();
            addressMap.put("RoadNo", 20);
            addressMap.put("village", "Betbaria");
            addressMap.put("postOffice", "Betbaria");
            addressMap.put("upazila", "Gangni");
            addressMap.put("district", "Meherpur");
            addressMap.put("country", "Bangladesh");

            dataMap.put("address", addressMap);
            dataMap.put("currentTime", currentTime);
            dataMap.put("class", 10);
            dataMap.put("classRollNo", 1);
            dataMap.put("schoolName", "Betbaria Secondary school");

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(dataMap);
            System.out.println("String ----> " + jsonString);

            Map jsonMap = new ObjectMapper().readValue(jsonString, Map.class);


            Map newDataMap = new HashMap<>();
            newDataMap.put("age", 20);

            Map newAddressMap = new HashMap();
            newAddressMap.put("RoadNo", 30);
            newAddressMap.put("village", "Bamundi");
            newAddressMap.put("postOffice", "Bamundi");
            newAddressMap.put("upazila", "Gangni");
            newAddressMap.put("district", "Meherpur");
            newAddressMap.put("country", "Bangladesh");

            newDataMap.put("address", newAddressMap);
            newDataMap.put("class", 10);
            newDataMap.put("schoolName", "Betbaria Secondary");
            newDataMap.put("currentTime", null);
            newDataMap.put("gender", "M");
            newDataMap.put("fatherName", "Mosharraf Hossain");
            newDataMap.put("motherName", "Jahanara Begum");

            Map changedData = new HashMap();
            for (Object key : newDataMap.keySet()) {
                if (!jsonMap.containsKey(key) || !jsonMap.get(key).equals(newDataMap.get(key))) {

                    Map changed = new HashMap();
                    changed.put("O", jsonMap.get(key));
                    changed.put("N", newDataMap.get(key));
                    changedData.put(key, changed);

                    if (newDataMap.get(key) == null) {
                        jsonMap.remove(key);
                    } else {
                        jsonMap.put(key, newDataMap.get(key));
                    }
                }
            }

            String changedJsonString = mapper.writeValueAsString(changedData);
            System.out.println("Changed: " + changedJsonString);

            Map changedJsonMap = new ObjectMapper().readValue(changedJsonString, Map.class);

            String jsonString2 = mapper.writeValueAsString(jsonMap);
            System.out.println("After Change: " + jsonString2);

            for (Object key : changedJsonMap.keySet()) {
                Map changed = (Map) changedJsonMap.get(key);
                if (changed.get("O") == null) {
                    jsonMap.remove(key);
                } else {
                    jsonMap.put(key, changed.get("O"));
                }
            }

            String jsonString4 = mapper.writeValueAsString(jsonMap);
            System.out.println("After Restore: " + jsonString4);

            return jsonMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(e.getMessage());
        }
    }
}
