package com.kesho.datamart.service;

import com.kesho.datamart.dto.InstitutionDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstitutionService {
    List<InstitutionDto> getInstitutions();

    InstitutionDto create(InstitutionDto institution);
}
