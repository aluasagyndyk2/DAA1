package kz.astana.algos.util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private FileWriter writer;

    // Конструктор: файл ашып, header жазамыз
    public CSVWriter(String fileName) throws IOException {
        writer = new FileWriter(fileName);
        writer.write("algorithm,n,time_ms,depth,comparisons\n");
    }

    // Бір жол жазу
    public void writeRow(String algorithm, int n, long timeMs, int depth, long comparisons) throws IOException {
        writer.write(algorithm + "," + n + "," + timeMs + "," + depth + "," + comparisons + "\n");
    }

    // Файлды жабу
    public void close() throws IOException {
        writer.close();
    }
}
