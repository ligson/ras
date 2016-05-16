<@override name="title">申请证书</@override>
<@override name="header">
<link type="text/css" rel="stylesheet" href="${assetsPath}js/lib/bootstrap-validator/css/bootstrapValidator.css">
<script type="text/javascript" src="${assetsPath}js/lib/bootstrap-validator/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="${assetsPath}js/ras/pub/cert/enroll.js"></script>
</@override>
<@override name="body">
<div class="col-sm-4"></div>
<div class="col-sm-4">
    <form id="enrollForm" class="form-horizontal" method="post" action="${basePath}cert/enrollCert.do">
        <div class="form-group text-center">
            <p>
                <small class="text-danger">${RequestParameters["errorMsg"]}</small>
            </p>
        </div>
        <div class="form-group">
            <label class="col-sm-4">CSR(O)</label>
            <div class="col-sm-8">
                <textarea name="csr" class="form-control">${RequestParameters['csr']}</textarea>
            </div>
        </div>
        <div class="form-group text-center">
            <input type="submit" class="btn-info" value="提交申请"/>
        </div>
    </form>
</div>
<div class="col-sm-4"></div>
</@override>
<@extends name="pub/layout/index.ftl"/>