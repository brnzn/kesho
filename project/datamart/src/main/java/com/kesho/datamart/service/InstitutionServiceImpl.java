package com.kesho.datamart.service;

import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.repository.SchoolsDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class InstitutionServiceImpl implements InstitutionService {
    @Inject
    private SchoolsDAO schoolsDAO;

    @Override
    public List<InstitutionDto> getInstitutions() {
        List<School> schools = schoolsDAO.findAll();

        List<InstitutionDto> institutions = new ArrayList<InstitutionDto>(schools.size());
        for (School school:schools) {
            institutions.add(toDto(school));
        }

        return institutions;
    }

    @Override
    public InstitutionDto create(InstitutionDto institution) {
        School school = new School(null, institution.getName());
        school = schoolsDAO.save(school);
        return toDto(school);
    }

    private InstitutionDto toDto(School school) {
        return new InstitutionDto(school.getId(), school.getName());
    }
}
