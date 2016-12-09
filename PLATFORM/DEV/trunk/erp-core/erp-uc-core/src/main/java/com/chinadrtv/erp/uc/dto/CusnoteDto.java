package com.chinadrtv.erp.uc.dto;

import java.io.Serializable;

/**
 * Created by xieguoqiang on 14-4-25.
 */
public class CusnoteDto implements Serializable {
    private String isreplay;
    private String noteclass;
    private String gendate_begin;
    private String gendate_end;
    private String genseat;
    private String redate_begin;
    private String redate_end;
    private String reseat;

    public String getIsreplay() {
        return isreplay;
    }

    public void setIsreplay(String isreplay) {
        this.isreplay = isreplay;
    }

    public String getNoteclass() {
        return noteclass;
    }

    public void setNoteclass(String noteclass) {
        this.noteclass = noteclass;
    }

    public String getGenseat() {
        return genseat;
    }

    public void setGenseat(String genseat) {
        this.genseat = genseat;
    }

    public String getReseat() {
        return reseat;
    }

    public void setReseat(String reseat) {
        this.reseat = reseat;
    }

    public String getGendate_begin() {
        return gendate_begin;
    }

    public void setGendate_begin(String gendate_begin) {
        this.gendate_begin = gendate_begin;
    }

    public String getGendate_end() {
        return gendate_end;
    }

    public void setGendate_end(String gendate_end) {
        this.gendate_end = gendate_end;
    }

    public String getRedate_begin() {
        return redate_begin;
    }

    public void setRedate_begin(String redate_begin) {
        this.redate_begin = redate_begin;
    }

    public String getRedate_end() {
        return redate_end;
    }

    public void setRedate_end(String redate_end) {
        this.redate_end = redate_end;
    }
}
