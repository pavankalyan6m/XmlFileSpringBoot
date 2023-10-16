package com.example.FileReader.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}