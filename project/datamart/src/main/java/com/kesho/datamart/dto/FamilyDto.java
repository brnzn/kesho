package com.kesho.datamart.dto;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDto implements Comparable {
    private final Long id;
    private final String name;

    public FamilyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyDto familyDto = (FamilyDto) o;

        if (id != null ? !id.equals(familyDto.id) : familyDto.id != null) return false;
        if (name != null ? !name.equals(familyDto.name) : familyDto.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo( ((FamilyDto)o).getName() );
    }

    @Override
    public String toString() {
        return name;
    }
}
