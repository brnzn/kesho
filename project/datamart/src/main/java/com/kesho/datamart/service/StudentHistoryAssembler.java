package com.kesho.datamart.service;

import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.entity.FamilyProfile;
import com.kesho.datamart.entity.StudentHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 7/8/14
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentHistoryAssembler {
    List<HistoryDto> toDto(List<StudentHistory> elements) {
        List<HistoryDto> items = new ArrayList<>();
        for (StudentHistory element:elements) {
            items.add(toDto(element));
        }
        return items;
    }

    StudentHistory toStudentHistory(HistoryDto dto) {
        StudentHistory history = new StudentHistory();
        history.setId(dto.getId());
        history.setStudentId(dto.getOwnerId());
        history.setComments(dto.getComments());
        history.setDate(dto.getDate());
        return history;
    }

    HistoryDto toDto(StudentHistory history) {
        HistoryDto dto = new HistoryDto(history.getStudentId(), history.getComments(), history.getDate());
        dto.setId(history.getId());
        return dto;
    }

    FamilyProfile toFamilyProfile(HistoryDto dto) {
        FamilyProfile profile = new FamilyProfile();
        profile.setId(dto.getId());
        profile.setFamilyId(dto.getOwnerId());
        profile.setComments(dto.getComments());
        profile.setDate(dto.getDate());
        return profile;
    }

    HistoryDto toDto(FamilyProfile familyProfile) {
        HistoryDto dto = new HistoryDto(familyProfile.getFamilyId(), familyProfile.getComments(), familyProfile.getDate());
        dto.setId(familyProfile.getId());
        return dto;
    }

    List<HistoryDto> profilesToDto(List<FamilyProfile> profiles) {
        List<HistoryDto> items = new ArrayList<>();
        for (FamilyProfile element:profiles) {
            items.add(toDto(element));
        }
        return items;
    }
}
