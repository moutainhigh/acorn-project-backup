<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/company/logisticsWeightRule.js"></script>
    <script type="text/javascript" src="/static/scripts/company/logisticsWeightRule.details.js"></script>

    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:120px;
            padding:5px 0 0;
        }
    </style>
</head>
<body>

<div id="condition">
    <table class="condition" cellpadding="2" cellspacing="2">
        <tr>
            <td><label for="cbWarehouse">仓库：</label></td>
            <td><input id="cbWarehouse" style="width:150px" class="easyui-combobox" name="warehouseId"/></td>
            <td><label for="cbCompany">承运商：</label></td>
            <td><input id="cbCompany" style="width:150px" class="easyui-combobox" name="companyId" /></td>
            <td><button id="btnSearch" type="button" value='查找'>查找</button></td>
        </tr>
    </table>
</div>
<div>
    <div id="toolbar">
        <a href="#" id="lbNew" class="easyui-linkbutton button" iconCls="icon-add" plain="true" style="float:left">新增</a>
        <a href="#" id="lbEdit" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left">编辑</a>
        <a href="#" id="lbDelete" class="easyui-linkbutton button" iconCls="icon-remove" plain="true" style="float:left">删除</a>
    </div>
    <div class="container">
        <table id="dg" title="送货公司"  cellspacing="0" cellpadding="0" data-options=""></table>
    </div>
    <div class="toolbar">
        <a href="#" id="lbNew-weight" class="easyui-linkbutton button" iconCls="icon-add" plain="true" style="float:left">新增</a>
        <a href="#" id="lbEdit-weight" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left">编辑</a>
        <a href="#" id="lbDelete-weight" class="easyui-linkbutton button" iconCls="icon-remove" plain="true" style="float:left">删除</a>
    </div>
    <div class="container">
        <table id="dg-weight" title="送货公司" cellspacing="0" cellpadding="0" data-options="" ></table>
    </div>
</div>


<div id="dlg" class="easyui-dialog" style="width:360px;height:200px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <fieldset>
        <form id="fm" method="post" novalidate>
            <input id="fmId" name="id" type="hidden" >
            <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWarehouse">仓库：</label>
                            <input id="fmWarehouse" style="width:150px;" class="easyui-combobox" name="warehouse"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmContract">承运商合同:</label>
                            <input id="fmContract" style="width:150px;" name="contract" required=true class="easyui-combobox">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmVersion">运费公式版本:</label>
                            <input id=fmVersion style="width:150px;" name="version" class="easyui-numberbox" min="0" required="true" >
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label>当前状态：</label>
                            <input id="fmStatus" style="width:150px;" name="status" class="easyui-combobox">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>


<div id="dlg-weight" class="easyui-dialog" style="width:360px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-buttons-weight">
    <fieldset>
        <form id="fm-weight" method="post" novalidate>
            <input id="fmId-weight" name="id" type="hidden" >
            <input id="fmRule-weight" name="rule" type="hidden" >
            <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmDestinationProvince">省份：</label>
                            <input id="fmDestinationProvince" style="width:150px;"  class="easyui-combobox" name="destinationProvince"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmDestinationCity">城市：</label>
                            <input id="fmDestinationCity" style="width:150px;"  class="easyui-combobox" name="destinationCity"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmDestinationCounty">区/县：</label>
                            <input id="fmDestinationCounty" style="width:150px;"  class="easyui-combobox" name="destinationCounty"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightFloor">首重条件1（kg）</label>
                            <input id="fmWeightFloor" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightFloor"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightCeil">首重条件2（kg）</label>
                            <input id="fmWeightCeil" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightCeil"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightInitial">首重（kg）</label>
                            <input id="fmWeightInitial" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightInitial"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightInitialFee">首重费用 </label>
                            <input id="fmWeightInitialFee" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightInitialFee"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightIncrease">续重（kg）</label>
                            <input id="fmWeightIncrease" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightIncrease"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmWeightIncreaseFee">单位续重费用</label>
                            <input id="fmWeightIncreaseFee" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="weightIncreaseFee"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmSucceedFeeAmount">成功费用</label>
                            <input id="fmSucceedFeeAmount" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="succeedFeeAmount"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmRoundType">取整方式 </label>
                            <select id="fmRoundType" style="width:150px;" class="easyui-combobox" maxlength="19" precision="2" min="0" name="roundType">
                                <option value="0">正常</option>
                                <option value="1">向上取整</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmSucceedFeeRatio">成功费率(%)</label>
                            <input id="fmSucceedFeeRatio" style="width:150px;" class="easyui-numberbox"  maxlength="19" precision="2" min="0" name="succeedFeeRatio"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmRefusedFeeAmount">拒收费用</label>
                            <input id="fmRefusedFeeAmount" style="width:150px;" class="easyui-numberbox" maxlength="19" precision="2" min="0" name="refusedFeeAmount"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmRefusedIncludePostfee">拒收是否包含邮费 </label>
                            <select id="fmRefusedIncludePostfee" style="width:150px;" class="easyui-combobox" maxlength="19" precision="2" min="0" name="refusedIncludePostFee">
                                <option value="0">不包含</option>
                                <option value="1">包含</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmRefusedFeeRatio">拒收费率(%)</label>
                            <input id="fmRefusedFeeRatio" style="width:150px;" class="easyui-numberbox"  maxlength="19" precision="2" min="0" name="refusedFeeRatio"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmSpecialFee">计泡费用</label>
                            <input id="fmSpecialFee" style="width:150px;" class="easyui-numberbox"  maxlength="19" precision="2" min="0" name="specialFee"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmSideLength">边长(CM)</label>
                            <input id="fmSideLength" style="width:150px;" class="easyui-numberbox"  maxlength="19" precision="2" min="0" name="sideLength"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="fitem">
                            <label for="fmVolumeFeeAmount">每立方体积费用</label>
                            <input id="fmVolumeFeeAmount" style="width:150px;" class="easyui-numberbox"  maxlength="19" precision="2" min="0" name="volumeFeeAmount"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
</div>

<div id="dlg-buttons-weight">
    <a href="javascript:void(0)" id="lbSave-weight" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="lbClose-weight" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>

</body>
</html>
