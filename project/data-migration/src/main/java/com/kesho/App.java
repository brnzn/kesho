package com.kesho;

import com.kesho.db.SchoolsWriter;
import com.kesho.db.Writer;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
        String userName = "oren";
        String password = "oren";
        String url = "jdbc:mysql://localhost/kesho";

//        Path in = Paths.get(App.class.getResource("/Schools.txt").toURI());
//
//        new Writer().proces(null, in, null, 8);
//        try(Stream<String> filteredLines = Files.lines(in)
//                //test if file is closed or not
//                .onClose(() -> System.out.println("File closed"))
//                ) {
//
//            filteredLines.forEach(s -> {
//                String[] vals = s.split(",");
//                String[] n = Arrays.copyOf(vals, 8);
//                System.out.println(n.length);
//                System.out.println(n[7]);
//            });
//        }


            // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            // ...
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Path in = Paths.get(App.class.getResource("/data/Schools.txt").toURI());

            new Writer().proces(create, in, new SchoolsWriter(), 8);

//            System.out.println(create.select().from(Schools.SCHOOLS).fetch().get(0));
//
//            Result<SchoolsRecord> result = create.selectFrom(Schools.SCHOOLS).fetch();
//            System.out.println(result.get(0).getName());

//            create.insertInto(Schools.SCHOOLS, Schools.SCHOOLS.NAME)
//                    .values("Orwell")
//                    .execute();
        }
    }

    //read file
    //for each line
    // split
    //transform
    //insert
}
