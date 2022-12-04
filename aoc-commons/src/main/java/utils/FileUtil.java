package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static String loadData(String input) throws IOException {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(input).getFile());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
