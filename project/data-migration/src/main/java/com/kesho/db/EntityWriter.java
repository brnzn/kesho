package com.kesho.db;

import org.jooq.DSLContext;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/25/15
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityWriter {
    void insert(String[] values, DSLContext context);
}
