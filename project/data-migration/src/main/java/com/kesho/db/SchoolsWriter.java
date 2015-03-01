package com.kesho.db;

import static com.kesho.generated.tables.Schools.SCHOOLS;

import com.kesho.generated.tables.Schools;
import org.jooq.DSLContext;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 3/1/15
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolsWriter implements EntityWriter {
    @Override
    public void insert(String[] values, DSLContext context) {
        context.insertInto(SCHOOLS,
                SCHOOLS.ID,
                SCHOOLS.NAME,
                SCHOOLS.ADDRESS_LINE1,
                SCHOOLS.ADDRESS_LINE2,
                SCHOOLS.ADDRESS_LINE3,
                SCHOOLS.COUNTRY,
                SCHOOLS.COUNTY,
                SCHOOLS.POSTCODE)
                .values(Long.valueOf(values[0].substring(1)),
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5],
                        values[6],
                        values[7])
                .execute();
    }
}
