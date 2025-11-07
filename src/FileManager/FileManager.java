package FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager implements AutoCloseable {
    private BufferedWriter writer;
    private String fileName;

    public FileManager(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new BufferedWriter(new FileWriter(fileName));
    }

    public String getFileName() {
        return fileName;
    }

    public void writeHeader(int number, int limit) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        writer.write("Исследование числа " + number + " до границы " + limit + "\n");
        writer.write("Дата и время запуска: " + formattedDate + "\n");
        writer.write(String.format("%-10s %-10s%n", "Число", "Сумма цифр"));
        writer.write("----------------------\n");
    }

    public void writeLine(int current, int sum) throws IOException {
        writer.write(String.format("%-10d %-10d%n", current, sum));
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}