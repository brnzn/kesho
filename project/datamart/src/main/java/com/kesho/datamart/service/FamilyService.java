package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.entity.Family;

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
}