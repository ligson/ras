package org.ca.ras.cert.biz;

import org.ca.common.cert.enums.CertStatus;
import org.ca.ras.cert.domain.CertEntity;
import org.ca.ras.cert.dto.ApproveCertRequestDto;
import org.ca.ras.cert.dto.ApproveCertResponseDto;
import org.ca.ras.cert.enums.CertFailEnum;
import org.ca.ras.cert.service.CertService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ligson on 2016/5/16.
 */
@Component("approveCertBiz")
@Api(name = "批准证书接口")
public class ApproveCertBiz extends AbstractBiz<ApproveCertRequestDto, ApproveCertResponseDto> {
    @Resource
    CertService certService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {

        return null;
    }

    @Override
    public Boolean bizCheck() {
        CertEntity certEntity = certService.get(requestDto.getCertId());
        if (certEntity == null) {
            setFailureResult(CertFailEnum.E_BIZ_21003);
            return false;
        } else {
            context.setAttr("entity", certEntity);
        }
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        CertEntity certEntity = (CertEntity) context.getAttr("entity");
        certEntity.setStatus(CertStatus.VALID.getCode());
        certEntity.setRejectDate(new Date());
        return true;
    }

    @Override
    public Boolean persistence() {
        CertEntity certEntity = (CertEntity) context.getAttr("entity");
        certService.update(certEntity);
        setSuccessResult();
        return true;
    }

    @Override
    public void after() {

    }
}
