package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.entity.Family;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyAssembler {
    FamilyDto toDto(Family family) {
        FamilyDto dto = new FamilyDto(family.getId(), family.getName());
        return dto;
    }

    Family toFamily(FamilyDto dto) {
        Family family = new Family();
        family.setId(dto.getId());
        family.setName(dto.getName());

        return family;
    }

    public List<FamilyDto> toDto(List<Family> families) {
        List<FamilyDto> list = new ArrayList<FamilyDto>();

        for(Family family:families) {
            list.add(toDto(family));
        }

        return list;
    }
}
