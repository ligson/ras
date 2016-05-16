package org.ca.ras.cert.facade;

import org.ca.ras.cert.api.RaCertApi;
import org.ca.ras.cert.biz.*;
import org.ca.ras.cert.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/25.
 */
public class RaCertApiImpl implements RaCertApi {

    @Resource
    private EnrollCertBiz enrollCertBiz;
    @Resource
    private QueryCertBiz queryCertBiz;
    @Resource
    private ApproveCertBiz approveCertBiz;
    @Resource
    private RejectCertBiz rejectCertBiz;
    @Resource
    private ModifyCertBiz modifyCertBiz;

    @Override
    public Result<IssueCertResponseDto> issueCert(IssueCertRequestDto requestDto) {
        return null;
    }

    @Override
    public Result<QueryCertResponseDto> queryCert(QueryCertRequestDto requestDto) {
        return queryCertBiz.operation(requestDto);
    }

    @Override
    public Result<EnrollCertResponseDto> enrollCert(EnrollCertRequestDto requestDto) {
        return enrollCertBiz.operation(requestDto);
    }

    @Override
    public Result<ApproveCertResponseDto> approveCert(ApproveCertRequestDto requestDto) {
        return approveCertBiz.operation(requestDto);
    }

    @Override
    public Result<RejectCertResponseDto> rejectCert(RejectCertRequestDto requestDto) {
        return rejectCertBiz.operation(requestDto);
    }

    @Override
    public Result<ModifyCertResponseDto> modifyCert(ModifyCertRequestDto requestDto) {
        return modifyCertBiz.operation(requestDto);
    }


}
