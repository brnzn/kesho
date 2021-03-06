package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FamilyRepository extends Paging {
    FamilyDto save(FamilyDto dto);

    List<FamilyDto> getFamilies();

    Page<FamilyDto> getPage(int page, int pageSize);

    void delete(Long id);

    List<StudentDto> getFamilyStudents(Long familyId);

    List<HistoryDto> getFamilyProfile(Long ownerId);

    HistoryDto save(HistoryDto dto);

    void deleteHistory(Long id);
}
