package com.chinadrtv.logistics.dal.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by ZXL on 14-12-24.
 */
public class Menberlive  implements Serializable {

    private String contactid;
    //private Date CRDT;
    //private Date MARKERCRDT;
    //private String SMS;
    //private double MARKERTOTALPRICE;
    private String MEMBERLEVELID;
    //private double TOTALPRICE;
    //private long TOTAL;
    //private Date MEMBERCRDT;
    private String memberlevel;

    public String getContactid(){
        return this.contactid;
    }

    public void setContactid(String contactid){
        this.contactid = contactid;
    }

    /*@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+08:00")
    public Date getcrdt(){
        return this.CRDT;
    }

    public void setcrdt(Date crdt){
        this.CRDT = crdt;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+08:00")
    public Date getMARKERCRDT(){
        return this.MARKERCRDT;
    }

    public void setMARKERCRDT(Date MARKERCRDT){
        this.MARKERCRDT = MARKERCRDT;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+08:00")
    public Date getMEMBERCRDT(){
        return this.MEMBERCRDT;
    }

    public void setMEMBERCRDT(Date MEMBERCRDT){
        this.MEMBERCRDT = MEMBERCRDT;
    }

    public String getSMS(){
        return this.SMS;
    }

    public void setSMS(String SMS){
        this.SMS = SMS;
    }

    public double getMARKERTOTALPRICE(){
        return this.MARKERTOTALPRICE;
    }

    public void setMARKERTOTALPRICE(double markertotalprice){
        this.MARKERTOTALPRICE = markertotalprice;
    }

    public double getTOTALPRICE(){
        return this.TOTALPRICE;
    }

    public void setTOTALPRICE(double totalprice){
        this.TOTALPRICE = totalprice;
    }*/

    public String getMEMBERLEVELID(){
        return this.MEMBERLEVELID;
    }

    public void setMEMBERLEVELID(String MEMBERLEVELID){
        this.MEMBERLEVELID = MEMBERLEVELID;
    }

    /*public long getTOTAL(){
        return  this.TOTAL;
    }

    public void setTOTAL(long total){
        this.TOTAL = total;
    }*/

    public String getmemberlevel(){
        return this.memberlevel;
    }

    public void setmemberlevel(String memberlevel){
        this.memberlevel = memberlevel;
    }

}
