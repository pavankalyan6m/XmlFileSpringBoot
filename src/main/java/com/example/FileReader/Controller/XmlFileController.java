package com.example.FileReader.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
class XmlFileController {
	    @GetMapping("/read-xml")
        public String readXmlFile(@RequestParam String filePath) {
        try {
            // Read the XML file from the specified file path
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder xmlContent = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                xmlContent.append(line);
            }

            // Print the XML content to the console
            System.out.println("XML Content:");
            System.out.println(xmlContent.toString());

            bufferedReader.close();
            return "XML content printed to the console. Check your application's logs.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read and print the XML file.";
        }
	}
	
	    //for writing into another XML file:
	
	    @PostMapping("/write-xml")
        public String writeXmlFile(@RequestParam String sourceFilePath, @RequestParam String targetFilePath) {
        try {
            // Read the XML content from the source file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFilePath));
            StringBuilder xmlContent = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                xmlContent.append(line);
            }

            // Write the XML content to the target file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {
                writer.write(xmlContent.toString());
            }

            return "XML content written to the target file.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to write the XML content to the target file.";
        }
    }

        @GetMapping("/read-xml-folder")
        public String readXmlFilesInFolder(@RequestParam String folderPath) {
            File folder = new File(folderPath);
            if (!folder.exists() || !folder.isDirectory()) {
                return "Invalid folder path.";
            }

            File[] xmlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

            if (xmlFiles == null || xmlFiles.length == 0) {
                return "No XML files found in the specified folder.";
            }

            StringBuilder result = new StringBuilder();

            for (File xmlFile : xmlFiles) {
                try {
                    // Read the XML file
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(xmlFile));
                    String line;
                    StringBuilder xmlContent = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        xmlContent.append(line).append("\n");
                    }

                    bufferedReader.close();

                    // Print the XML content to the console
                    System.out.println("XML Content from " + xmlFile.getName() + ":");
                    System.out.println(xmlContent.toString());

                    result.append("XML content from ").append(xmlFile.getName()).append(" printed to the console. Check your application's logs.\n");
                } catch (IOException e) {
                    e.printStackTrace();
                    result.append("Failed to read and print the XML file ").append(xmlFile.getName()).append(".\n");
                }
            }
            return result.toString();
        }
}