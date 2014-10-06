package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.WindowsUtil;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */

public class FamilyContactsController extends AbstractContactsController<FamilyDto> {

    public FamilyContactsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    protected ContactType getContactType() {
        return ContactType.P;
    }

}
