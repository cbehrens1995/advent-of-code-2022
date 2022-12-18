package solution;

import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        List<String> commands = data.lines().toList();

        Directory baseDir = null;
        Directory currentRoot = null;
        Directory oldRoot = null;
        for (String command : commands) {
            if (command.startsWith("$ cd") && !command.equals("$ cd ..")) {
                String dirName = StringUtils.split(command)[2];
                Directory directory = new Directory(dirName, currentRoot);
                if (baseDir == null) {
                    baseDir = directory;
                } else {
                    currentRoot.addDirectory(directory);
                }

                oldRoot = currentRoot;
                currentRoot = directory;
            }

            if (!command.startsWith("dir") && !command.startsWith("$")) {
                BigDecimal fileSize = new BigDecimal(StringUtils.split(command)[0]);
                currentRoot.addFile(fileSize);
            }

            if (command.equals("$ cd ..")) {
                currentRoot = oldRoot;
                oldRoot = oldRoot.getParentDirectory();
            }
        }
        BigDecimal sizePart1 = baseDir.streamAllDirectories()
                .map(Directory::getTotalSize)
                .filter(size -> size.compareTo(BigDecimal.valueOf(100000)) <= 0)
                .reduce(BigDecimal::add)
                .orElseThrow();

        System.out.printf("The total size is %s%n", sizePart1);

        //part two
        BigDecimal fileSystemSize = new BigDecimal("70000000");
        BigDecimal currentFreeSpace = fileSystemSize.subtract(baseDir.getTotalSize());
        BigDecimal requiredFreeSpace = new BigDecimal("30000000");
        BigDecimal neededFreeSpace = requiredFreeSpace.subtract(currentFreeSpace);

        BigDecimal sizeOfSmallestNeededDirectory = baseDir.streamAllDirectories()
                .map(Directory::getTotalSize)
                .filter(size -> size.compareTo(neededFreeSpace) > 0)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        System.out.printf("The size for smallest directory need for requiring enough space %s%n", sizeOfSmallestNeededDirectory);
    }

    public static final class Directory {
        //name is needed for debugging reasons
        private String name;
        private Directory parentDirectory;
        private List<Directory> directories = new ArrayList<>();
        private BigDecimal fileSize = BigDecimal.ZERO;

        public Directory(String name, Directory parentDirectory) {
            this.name = name;
            this.parentDirectory = parentDirectory;
        }

        public void addDirectory(Directory directory) {
            directories.add(directory);
        }

        public void addFile(BigDecimal fileSizeToAdd) {
            fileSize = fileSize.add(fileSizeToAdd);
        }

        public Directory getParentDirectory() {
            return parentDirectory;
        }

        public BigDecimal getTotalSize() {
            BigDecimal directorySize = directories.stream()
                    .map(Directory::getTotalSize)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            return fileSize.add(directorySize);
        }

        public Stream<Directory> streamAllDirectories() {
            Stream<Directory> subDirectories = directories.stream()
                    .flatMap(Directory::streamAllDirectories);
            return Stream.concat(directories.stream(), subDirectories);
        }
    }

}
