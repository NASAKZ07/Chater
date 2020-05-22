package com.company.file;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public interface ReadFile {
    public Scanner getScanner();
    public HashMap<String,String> getProperties();
}
