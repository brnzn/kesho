package com.kesho.datamart.service;

import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.entity.Sponsor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SponsorAssembler {

    public List<SponsorDto> toDto(List<Sponsor> sponsors) {
        List<SponsorDto> dtos = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            dtos.add(toDto(sponsor));
        }

        return dtos;
    }

    public SponsorDto toDto(Sponsor sponsor) {
        return new SponsorDto(sponsor);
    }

    public Sponsor toEntity(SponsorDto dto) {
        Sponsor sponsor = new Sponsor();
        sponsor.setId(dto.getId());
        sponsor.setName(dto.getName());
        sponsor.setStartDate(dto.getStartDate())       ;
        sponsor.setActive(dto.getActive());
        sponsor.setAddressLine1(dto.getAddressLine1());
        sponsor.setAddressLine2(dto.getAddressLine2());
        sponsor.setAnonymous(dto.getAnonymous());
        sponsor.setCountry(dto.getCountry());
        sponsor.setCounty(dto.getCounty());
        sponsor.setEndDate(dto.getEndDate());
        sponsor.setPostcode(dto.getPostcode());
        sponsor.setPayeeType(dto.getPayeeType());
        sponsor.setPhone(dto.getPhone());
        sponsor.setLevelOfParticipation(dto.getLevelOfParticipation());
        sponsor.setHowFoundUs(dto.getHowFoundUs());
        sponsor.setSurname(dto.getSurname());
        sponsor.setEmail1(dto.getEmail1());
        sponsor.setEmail2(dto.getEmail2());

        return sponsor;
    }
}
