package com.kesho.db;

import org.jooq.DSLContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/25/15
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Writer {
    public void proces(DSLContext context, Path file, EntityWriter entityWriter, int cols) {
        try(Stream<String> filteredLines = Files.lines(file)
                //test if file is closed or not
                .onClose(() -> System.out.println("File closed"))
        ) {
            filteredLines.forEach(s -> {
                String[] n = Arrays.copyOf(s.split(","), cols);
                entityWriter.insert(n, context);
            });
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
