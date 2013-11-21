package com.kesho.datamart.paging;

import javax.validation.constraints.Min;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/21/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageRequest {
    private final Integer pageNumber;
    @Min(value = 0, message = "Page size cannot be negative")
    private final Integer pageSize;

    public PageRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
