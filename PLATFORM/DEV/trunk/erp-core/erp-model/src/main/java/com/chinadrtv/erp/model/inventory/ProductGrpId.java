package com.chinadrtv.erp.model.inventory;

import javax.persistence.Embeddable;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import java.io.Serializable;

/**
 * 渠道产品复合主键
 * Grp: Administrator
 * Date: 13-5-13
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class ProductGrpId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodId;
    private String grpId;

    public ProductGrpId(){
        super();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String value) {
        this.prodId = value;
    }

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String value) {
        this.grpId = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof ProductGrpId))
            return false;

        final ProductGrpId other = (ProductGrpId) object;

        return  prodId!=null&&
                other.getProdId()!=null&&
                prodId==other.getProdId()
                &&
                grpId!=null&&
                other.getGrpId()!=null&&
                grpId==other.getGrpId();

    }

    @Override
    public int hashCode() {
        return super.hashCode()+
                (prodId!=null?prodId.hashCode():0)+
                (grpId!=null?grpId.hashCode():0);
    }
}
