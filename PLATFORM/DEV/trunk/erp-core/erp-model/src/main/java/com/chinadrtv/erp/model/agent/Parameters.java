package com.chinadrtv.erp.model.agent;

import javax.persistence.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "PARAMETERS", schema = "IAGENT")
@Entity
public class Parameters implements java.io.Serializable{
    private String paramid;
    private String paramdsc;
    private String param;

    @Column(name = "PARAMID", length = 30)
    @Id
    public String getParamid() {
        return paramid;
    }

    public void setParamid(String paramid) {
        this.paramid = paramid;
    }

    @Column(name = "PARAMDSC", length = 30)
    public String getParamdsc() {
        return paramdsc;
    }

    public void setParamdsc(String paramdsc) {
        this.paramdsc = paramdsc;
    }

    @Column(name = "PARAM", length = 80)
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameters that = (Parameters) o;

        if (param != null ? !param.equals(that.param) : that.param != null) return false;
        if (paramdsc != null ? !paramdsc.equals(that.paramdsc) : that.paramdsc != null) return false;
        if (paramid != null ? !paramid.equals(that.paramid) : that.paramid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paramid != null ? paramid.hashCode() : 0;
        result = 31 * result + (paramdsc != null ? paramdsc.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        return result;
    }
}
