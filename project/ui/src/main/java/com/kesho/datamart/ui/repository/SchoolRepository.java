package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SchoolRepository {
    SchoolDto create(SchoolDto institution);
    Page<SchoolDto> getPage(int page, int pageSize);
    List<SchoolDto> getAllSchools();
}
