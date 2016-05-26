<@override name="title">证书列表</@override>
<@override name="header">
<script type="text/javascript" src="${assetsPath}js/ras/admin/certMgr/index.js"></script>
</@override>
<@override name="body">
<table id="tt" class="easyui-datagrid" style="height:500px; width: 100%;"
       data-options="singleSelect:false,fix:true" toolbar="#toolbar" pagination="true" rownumbers="true"
       fitColumns="true"
       url="${basePath}admin/certMgr/certList.json" iconCls="icon-save" pagination="true">
    <thead>
    </thead>
</table>
<div id="approveCertDlgBtns">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="approveCert()">批准</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
       onclick="$('#approveCertDlg').dialog('close')">取消</a>
</div>
<div buttons="#approveCertDlgBtns" id="approveCertDlg" title="批准证书" class="easyui-dialog" closed="true"
     style="padding:30px;width:560px;">
    <form class="easyui-form" action="${basePath}admin/certMgr/approveCert.json"
          id="approveCertForm">
        <input type="hidden" name="id" value=""/>
        <input type="hidden" name="aliase" value=""/>
        <label class="col-sm-9">选择密钥</label>
    </form>
    <table id="keyGrid" class="easyui-datagrid" style="height:350px; width:500px;"
           data-options="singleSelect:true,fix:true" pagination="true" rownumbers="true"
           fitColumns="true"
           url="${basePath}admin/certMgr/keyList.json" iconCls="icon-save" pagination="true">
        <thead>
        </thead>
    </table>
</div>
<div id="viewCsrDlg" title="查看csr" class="easyui-dialog" closed="true" style="padding:30px;width:500px;">
    <div class="container-fluid">
        <textarea rows="15" class="form-control" id="csrTxt" disabled></textarea>
    </div>
</div>
</@override>
<@extends name="admin/layout/certMgr.ftl"/>