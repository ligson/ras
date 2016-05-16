package org.ca.ras.cert.biz;

import org.ca.ras.cert.domain.CertEntity;
import org.ca.ras.cert.dto.ModifyCertRequestDto;
import org.ca.ras.cert.dto.ModifyCertResponseDto;
import org.ca.ras.cert.enums.CertFailEnum;
import org.ca.ras.cert.service.CertService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.common.utils.BeanCopy;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/16.
 */
@Component("modifyCertBiz")
@Api(name = "修改证书接口")
public class ModifyCertBiz extends AbstractBiz<ModifyCertRequestDto, ModifyCertResponseDto> {
    @Resource
    private CertService certService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        return null;
    }

    @Override
    public Boolean bizCheck() {
        CertEntity certEntity = certService.get(requestDto.getId());
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
        BeanCopy.copyPropertiesIgnoreNull(requestDto, certEntity);
        certService.update(certEntity);
        setSuccessResult();
        return true;
    }

    @Override
    public Boolean persistence() {
        return null;
    }

    @Override
    public void after() {

    }
}
