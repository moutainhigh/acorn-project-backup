package com.chinadrtv.erp.core.ui.jquery;

/**
 * User: liuhaidong
 * Date: 12-11-14
 */
public class FieldView {


    private String field;
    private String title;
    private int width;
    private String editor;//:'text'



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
