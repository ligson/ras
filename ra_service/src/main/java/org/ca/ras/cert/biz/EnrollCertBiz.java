package org.ca.ras.cert.biz;

import org.ca.common.cert.enums.CertStatus;
import org.ca.common.cert.enums.CertType;
import org.ca.ras.cert.domain.CertEntity;
import org.ca.ras.cert.dto.EnrollCertRequestDto;
import org.ca.ras.cert.dto.EnrollCertResponseDto;
import org.ca.ras.cert.enums.CertFailEnum;
import org.ca.ras.cert.service.CertService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.string.encode.HashHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ligson on 2016/5/6.
 */
@Component("enrollCertBiz")
@Api(name = "申请证书接口")
public class EnrollCertBiz extends AbstractBiz<EnrollCertRequestDto, EnrollCertResponseDto> {
    @Resource
    private CertService certService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        if (!HashHelper.md5(requestDto.getSubjectDn()).equals(requestDto.getSubjectDnHashMd5())) {
            setFailureResult(CertFailEnum.E_PARAM_11001);
            return false;
        }
        return true;
    }

    @Override
    public Boolean bizCheck() {
        CertEntity entity = new CertEntity();
        entity.setSubjectDn(requestDto.getSubjectDn());
        entity.setStatus(CertStatus.VALID.getCode());
        entity.setCertType(CertType.SIGN.getCode());
        long n = certService.countByAnd(entity);
        if (n > 0) {
            setFailureResult(CertFailEnum.E_BIZ_21001);
            return false;
        }
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        CertEntity entity = new CertEntity();
        entity.setStatus(CertStatus.ENROLL.getCode());
        entity.setSubjectDn(requestDto.getSubjectDn());
        entity.setSubjectDnHashMd5(requestDto.getSubjectDnHashMd5());
        entity.setUserId(requestDto.getUserId());
        entity.setReqDate(new Date());
        entity.setCertPin(requestDto.getCertPin());
        entity.setCertType(CertType.SIGN.getCode());
        context.setAttr("entity", entity);
        return true;
    }

    @Override
    public Boolean persistence() {
        CertEntity entity = (CertEntity) context.getAttr("entity");
        certService.add(entity);
        responseDto.setSuccess(true);
        return true;
    }

    @Override
    public void after() {

    }
}
