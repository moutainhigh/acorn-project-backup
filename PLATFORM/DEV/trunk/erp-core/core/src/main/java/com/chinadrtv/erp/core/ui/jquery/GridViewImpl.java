package com.chinadrtv.erp.core.ui.jquery;

import com.chinadrtv.erp.core.model.FieldType;
import com.chinadrtv.erp.core.model.client.EntityFieldView;
import com.chinadrtv.erp.core.model.client.EntityGridView;
import com.chinadrtv.erp.core.ui.GridView;
import com.chinadrtv.erp.util.JsonBinder;

import java.util.HashSet;
import java.util.Set;

/**
 * User: liuhaidong
 * Date: 12-11-14
 */
public class GridViewImpl implements GridView{
    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
    private String idField = "id";
    private String url;
    private String saveUrl;
    private String updateUrl;
    private String destroyUrl;
    private Set<FieldView> fields = new HashSet<FieldView>(0);

    private String getEditor(int fieldType){
        switch (fieldType) {
            case FieldType.STRING:{
                return "text";
            }
            case FieldType.INTEGER:{
                return "numberbox";
            }
            case FieldType.DOUBLE:{
                return "numberbox";
            }
            case FieldType.DATE:{
                return "text";
            } default:{
                return "text";
            }
        }

    }
    public GridViewImpl(EntityGridView entityGridView) {
        this.entityGridView = entityGridView;

        this.url = entityGridView.getQueryUrl();
        this.saveUrl = entityGridView.getSaveUrl();
        this.updateUrl = entityGridView.getSaveUrl();
        this.destroyUrl = entityGridView.getDelUrl();
        for (EntityFieldView entityFieldView : entityGridView.getFieldViews()){
            FieldView fieldView = new FieldView();
            fieldView.setEditor(getEditor(entityFieldView.getType()));
            fieldView.setField(entityFieldView.getField());
            fieldView.setTitle(entityFieldView.getTitle());
            fieldView.setWidth(100);
            fields.add(fieldView);
        }
    }

    private EntityGridView entityGridView;

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getDestroyUrl() {
        return destroyUrl;
    }

    public void setDestroyUrl(String destroyUrl) {
        this.destroyUrl = destroyUrl;
    }

    public Set<FieldView> getFields() {
        return fields;
    }

    public void setFields(Set<FieldView> fields) {
        this.fields = fields;
    }

    public String getJs() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "    $(function () {\n" +
                "    $('#listAuditLogTable').edatagrid({\n" +
                "        title:'',\n" +
                "        iconCls:'icon-edit',\n" +
                "        width: 700,\n" +
                "        height: 400,\n" +
                "        nowrap: false,\n" +
                "        striped: true,\n" +
                "        border: true,\n" +
                "        collapsible:false,\n" +
                "        fit: true,");
        stringBuilder.append("url:'"+getUrl()+"',");
        stringBuilder.append("saveUrl:'"+getSaveUrl()+"',");
        stringBuilder.append("updateUrl:'"+getSaveUrl()+"',");
        stringBuilder.append("destroyUrl:'"+getDestroyUrl()+"',");
        stringBuilder.append("columns:[ ");
        stringBuilder.append(jsonBinder.toJson(getFields()));
        stringBuilder.append("],\n" +
                "        remoteSort:false,\n" +
                "        idField:'id',\n" +
                "        singleSelect:false,\n" +
                "        pagination:true,\n" +
                "        rownumbers:true,\n" +
                "        frozenColumns:[[\n" +
                "        {field:'ck',checkbox:true}\n" +
                "    ]]");
        stringBuilder.append("});");
        stringBuilder.append("});");
        return stringBuilder.toString();
    }

    public String getHtml() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
