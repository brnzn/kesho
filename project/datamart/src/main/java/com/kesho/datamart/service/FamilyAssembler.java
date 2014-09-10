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
        FamilyDto dto = new FamilyDto();
        dto.withId(family.getId())
            .withFamilyName(family.getName())
            .withVersion(family.getVersion())
            .withHomeLocation(family.getHomeLocation())
            .withHomeSubLocation(family.getHomeSubLocation())
            .withHomeClusterId(family.getHomeClusterId())
            .withAliveParents(family.getAliveParents())
            .isMarried(family.isMarried())
            .withNumOfChildrenAtAddress(family.getNumOfChildrenAtAddress())
            .withNumOfWives(family.getNumOfWives())
            .withPrimaryCaretaker(family.getPrimaryCaretaker())
            .withMainContactName(family.getMainContactName())
            .withMobileNumber(family.getMobileNumber())
            .isPhoneOwner(family.isPhoneOwner())
            .withPhoneOwnerName(family.getPhoneOwnerName())
            .withProfile(family.getProfile())
            .withNumOfAdultsAtAddress(family.getNumOfAdultsAtAddress());

        return dto;
    }

    Family toFamily(FamilyDto dto) {
        Family family = new Family();
        family.setVersion(dto.getVersion());
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
