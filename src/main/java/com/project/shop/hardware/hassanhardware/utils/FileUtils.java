package com.project.shop.hardware.hassanhardware.utils;

import java.io.File;

public class FileUtils {

    public static String getFileExtension(File file) {
        if (file == null) {
            return "";
        }

        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        } else {
            return "";
        }
    }
}
