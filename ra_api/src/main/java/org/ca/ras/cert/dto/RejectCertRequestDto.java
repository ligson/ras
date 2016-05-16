package org.ca.ras.cert.dto;

import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

/**
 * Created by ligson on 2016/5/16.
 */
public class RejectCertRequestDto extends BaseRequestDto {
    private String adminId;
    private String certId;
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RejectCertRequestDto{" +
                "adminId='" + adminId + '\'' +
                ", certId='" + certId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
