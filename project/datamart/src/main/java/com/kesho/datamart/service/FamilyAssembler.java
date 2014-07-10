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
        dto.setHomeLocation(family.getHomeLocation());
        dto.setHomeSubLocation(family.getHomeSubLocation());
        dto.setHomeClusterId(family.getHomeClusterId());
        dto.setAliveParents(family.getAliveParents());
        dto.setMarried(family.isMarried());
        dto.setNumOfChildrenAtAddress(family.getNumOfChildrenAtAddress());
        dto.setNumOfWives(family.getNumOfWives());
        dto.setPrimaryCaretaker(family.getPrimaryCaretaker());
        dto.setMainContactName(family.getMainContactName());
        dto.setMobileNumber(family.getMobileNumber());
        dto.setPhoneOwner(family.isPhoneOwner());
        dto.setPhoneOwnerName(family.getPhoneOwnerName());
        dto.setProfile(family.getProfile());
        dto.setNumOfAdultsAtAddress(family.getNumOfAdultsAtAddress());
        return dto;
    }

    Family toFamily(FamilyDto dto) {
        Family family = new Family();
        family.setId(dto.getId());
        family.setName(dto.getFamilyName());
        family.setHomeLocation(dto.getHomeLocation());
        family.setHomeSubLocation(dto.getHomeSubLocation());
        family.setHomeClusterId(dto.getHomeClusterId());
        family.setAliveParents(dto.getAliveParents());
        family.setMarried(dto.getMarried());
        family.setNumOfChildrenAtAddress(dto.getNumOfChildrenAtAddress());
        family.setNumOfWives(dto.getNumOfWives());
        family.setPrimaryCaretaker(dto.getPrimaryCaretaker());
        family.setMainContactName(dto.getMainContactName());
        family.setMobileNumber(dto.getMobileNumber());
        family.setPhoneOwner(dto.getPhoneOwner());
        family.setPhoneOwnerName(dto.getPhoneOwnerName());
        family.setProfile(dto.getProfile());
        family.setNumOfAdultsAtAddress(dto.getNumOfAdultsAtAddress());
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
