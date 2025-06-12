package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.InfoCompany;

public class InfoCompanyDTO {
    public Long id;
    public String companyName;
    public String companyPicture;
    public InfoCompanyDTO (InfoCompany infoCompany) {
        this.id = infoCompany.getId();
        this.companyName = infoCompany.getCompanyName();
        this.companyPicture = infoCompany.getCompanyPicture();
    }
}
