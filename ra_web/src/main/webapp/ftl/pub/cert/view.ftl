<@override name="title">查看用户证书</@override>
<@override name="header">
</@override>
<@override name="body">
<div class="col-sm-4"></div>
<div class="col-sm-4">
    <table class="table">
        <tr>
            <td><label>主题</label></td>
            <td>${cert.subjectDn}</td>
        </tr>
        <tr>
            <td><label>颁发者</label></td>
            <td>${cert.issuerDn}</td>
        </tr>
        <tr>
            <td><label>序列号</label></td>
            <td>${cert.serialNumber}</td>
        </tr>
        <tr>
            <td><label>开始日期</label></td>
            <td>${cert.notBefore?string('yyyy年MM月dd日 HH:mm:ss')}</td>
        </tr>
        <tr>
            <td><label>结束日期</label></td>
            <td>${cert.notAfter?string('yyyy年MM月dd日 HH:mm:ss')}</td>
        </tr>
        <tr>
            <td><label>状态</label></td>
            <!-- ENROLL(0, "申请"), VALID(1, "有效"), REVOKE(2, "吊销"), SUSPEND(3, "挂起");-->
            <td>
                <#if cert.status==0>申请<#elseif  cert.status==2>吊销<#elseif  cert.status==3>挂起<#else>有效</#if>
            </td>
        </tr>
    </table>
</div>
<div class="col-sm-4"></div>
</@override>
<@extends name="pub/layout/index.ftl"/>