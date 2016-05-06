package org.ca.ras.cert.dto;

import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;

/**
 * Created by ligson on 2016/5/4.
 * 提交证书申请
 */
public class EnrollCertRequestDto extends BaseRequestDto {
    @Param(name = "用户id", required = true)
    private BigInteger userId;
    @Param(name = "颁发给", required = true)
    private String subjectDn;
    @Param(name = "颁发给哈希", required = true)
    private String subjectDnHashMd5;
    @Param(name = "证书密码", required = true)
    private String certPin;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getSubjectDn() {
        return subjectDn;
    }

    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }

    public String getSubjectDnHashMd5() {
        return subjectDnHashMd5;
    }

    public void setSubjectDnHashMd5(String subjectDnHashMd5) {
        this.subjectDnHashMd5 = subjectDnHashMd5;
    }

    public String getCertPin() {
        return certPin;
    }

    public void setCertPin(String certPin) {
        this.certPin = certPin;
    }


    @Override
    public String toString() {
        return "EnrollCertRequestDto{" +
                "userId=" + userId +
                ", subjectDn='" + subjectDn + '\'' +
                ", subjectDnHashMd5='" + subjectDnHashMd5 + '\'' +
                ", certPin='" + certPin + '\'' +
                '}';
    }
}
