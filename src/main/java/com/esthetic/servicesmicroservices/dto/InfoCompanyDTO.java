package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.InfoCompany;

public class InfoCompanyDTO {
    public Long id;
    public String companyName;
    public String companyPictureUrl;
    public InfoCompanyDTO (InfoCompany infoCompany) {
        this.id = infoCompany.getId();
        this.companyName = infoCompany.getCompanyName();
        this.companyPictureUrl = infoCompany.getCompanyPictureUrl();
    }
}
