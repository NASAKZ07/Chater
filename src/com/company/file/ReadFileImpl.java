package com.company.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadFileImpl implements ReadFile {

    @Override
    public Scanner getScanner() {
        try {
            File file = new File("properties.txt");
            Scanner scanner = new Scanner(file);
            return scanner;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String,String> getProperties() {
        HashMap<String,String> properties = new HashMap<>();
        Scanner scanner = getScanner();
        properties.put("url",scanner.nextLine());
        properties.put("user", scanner.nextLine());
        properties.put("password",scanner.nextLine());
        properties.put("ip", scanner.nextLine());
        properties.put("port",scanner.nextLine());
        return properties;
    }




}
