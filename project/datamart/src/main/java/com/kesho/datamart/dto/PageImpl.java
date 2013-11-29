package com.kesho.datamart.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/21/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageImpl<T> implements Page <T> {
    private int totalPages;
    private List<T> content;
    private List<String> errors;
    private int size;

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean isError() {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public int getSize() {
        return size;
    }

    public PageImpl<T> withErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public PageImpl<T> withContent(List<T> content) {
        this.content = content;
        return this;
    }

    public PageImpl<T> withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageImpl<T> withPageSize(int size) {
        this.size = size;
        return this;
    }

    public PageImpl<T> withError(String error) {
        if(errors == null) {
            errors = new ArrayList<String>();
        }

        errors.add(error);
        return this;
    }
}
