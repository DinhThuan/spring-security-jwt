package com.example.spring.crud.phrase.utils;

import com.example.spring.crud.phrase.entities.DeveloperTutorial;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Id", "Title", "Description", "published"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType()) || "application/vnd.ms-ex-cel".equals(file.getContentType())) {
            return true;
        }
        return false;
    }

    public static List<DeveloperTutorial> csvTutorials(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<DeveloperTutorial> developerTutorialList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                DeveloperTutorial developerTutorial = new DeveloperTutorial(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Title"),
                        csvRecord.get("Description"),
                        Boolean.parseBoolean(csvRecord.get("Published")),
                        fileName
                );

                developerTutorialList.add(developerTutorial);
            }

            return developerTutorialList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream tutorialToCSV(List<DeveloperTutorial> developerTutorialList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (DeveloperTutorial developerTutorial : developerTutorialList) {
                List<String> data = Arrays.asList(
                        String.valueOf(developerTutorial.getId()),
                        developerTutorial.getTitle(),
                        developerTutorial.getDescription(),
                        String.valueOf(developerTutorial.isPublished()),
                        String.valueOf(developerTutorial.getFileName())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
