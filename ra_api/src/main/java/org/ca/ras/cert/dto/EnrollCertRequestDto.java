package org.ca.ras.cert.dto;

import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;

/**
 * Created by ligson on 2016/5/4.
 * 提交证书申请
 */
public class EnrollCertRequestDto extends BaseRequestDto {
    private BigInteger userId;
    private String subjectDn;
    private String subjectDnHashMd5;
    private String certPin;


}
