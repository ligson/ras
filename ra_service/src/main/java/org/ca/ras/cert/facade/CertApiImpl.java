package org.ca.ras.cert.facade;

import org.ca.ras.cert.api.CertApi;
import org.ca.ras.cert.biz.EnrollCertBiz;
import org.ca.ras.cert.biz.QueryCertBiz;
import org.ca.ras.cert.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/25.
 */
public class CertApiImpl implements CertApi {

    @Resource
    private EnrollCertBiz enrollCertBiz;

    @Resource
    private QueryCertBiz queryCertBiz;

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


}
