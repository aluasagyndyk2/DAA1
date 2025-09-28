package kz.astana.algos.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private final BufferedWriter writer;

    public CSVWriter(String fileName) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(fileName, true));
        // Заголовок жазу (егер файл бос болса)
        if (new java.io.File(fileName).length() == 0) {
            writer.write("Algorithm,N,TimeMs,Depth,Comparisons\n");
        }
    }

    public void writeRow(String algorithm, int n, long timeMs, int depth, long comparisons) throws IOException {
        writer.write(String.format("%s,%d,%d,%d,%d\n", algorithm, n, timeMs, depth, comparisons));
    }

    public void close() throws IOException {
        writer.close();
    }
}
