package org.ca.ras.cert.dto;

import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

/**
 * Created by ligson on 2016/5/16.
 */
public class ApproveCertRequestDto extends BaseRequestDto {
    private String adminId;
    private String certId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    @Override
    public String toString() {
        return "ApproveCertRequestDto{" +
                "adminId='" + adminId + '\'' +
                ", certId='" + certId + '\'' +
                '}';
    }
}
