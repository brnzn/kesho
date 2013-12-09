package com.kesho.datamart.dto;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstitutionDto {
    private final Long id;
    private final String name;
    public InstitutionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
