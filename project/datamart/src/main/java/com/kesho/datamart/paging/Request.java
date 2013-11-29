package com.kesho.datamart.paging;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/21/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Request {
    @Min(value = 0, message = "Page number cannot be negative")
    @NotNull(message = "Page number must be zero or greater")
    private final Integer pageNumber;

    @Min(value = 1, message = "Page size must be greater than zero")
    @NotNull(message = "Page size must be greater than zero")
    @Max(value = 50, message = "Page size cannot exceed 50")
    private final Integer pageSize;

    public Request(Integer pageNumber, Integer pageSize) {
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
