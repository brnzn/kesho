package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.Request;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FamilyService {
    FamilyDto save(FamilyDto family);

    List<FamilyDto> getFamilies();

    Page<FamilyDto> getPage(Request request);

    void delete(Long id);

    List<StudentDto> getFamilyStudents(Long familyId);

    HistoryDto save(HistoryDto dto);

    List<HistoryDto> getFamilyProfile(Long familyId);

    void deleteHistory(Long id);
}
