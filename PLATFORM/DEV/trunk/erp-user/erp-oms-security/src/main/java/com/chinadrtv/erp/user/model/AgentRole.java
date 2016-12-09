package com.chinadrtv.erp.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="PGLINK", schema="IAGENT")
public class AgentRole
  implements Serializable, GrantedAuthority
{
  private String purViewId;
  private String grpId;
  private String dsc;
  private String right;
  private String funcId;

  @Id
  @Column(name="PURVIEWID")
  public String getPurViewId()
  {
    return this.purViewId;
  }

  public void setPurViewId(String purViewId) {
    this.purViewId = purViewId;
  }
  @Id
  @Column(name="GRPID")
  public String getGrpId() {
    return this.grpId;
  }

  public void setGrpId(String grpId) {
    this.grpId = grpId;
  }

  @Column(name="DSC")
  public String getDsc() {
    return this.dsc;
  }

  public void setDsc(String dsc) {
    this.dsc = dsc;
  }

  @Column(name="RIGHTS")
  public String getRight() {
    return this.right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  @Column(name="FUNCID")
  public String getFuncId() {
    return this.funcId;
  }

  public void setFuncId(String funcId) {
    this.funcId = funcId;
  }

  @Transient
  public String getAuthority()
  {
    return this.purViewId;
  }

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.purViewId == null ? 0 : this.purViewId.hashCode());

    result = 31 * result + (this.grpId == null ? 0 : this.grpId.hashCode());

    result = 31 * result + (this.dsc == null ? 0 : this.dsc.hashCode());

    result = 31 * result + (this.right == null ? 0 : this.right.hashCode());
    result = 31 * result + (this.funcId == null ? 0 : this.funcId.hashCode());

    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AgentRole other = (AgentRole)obj;
    if (this.purViewId == null) {
      if (other.purViewId != null)
        return false;
    } else if (!this.purViewId.equals(other.purViewId))
      return false;
    if (this.grpId == null) {
      if (other.grpId != null)
        return false;
    } else if (!this.grpId.equals(other.grpId))
      return false;
    if (this.dsc == null) {
      if (other.dsc != null)
        return false;
    } else if (!this.dsc.equals(other.dsc))
      return false;
    if (this.right == null) {
      if (other.right != null)
        return false;
    } else if (!this.right.equals(other.right))
      return false;
    if (this.funcId == null) {
      if (other.funcId != null)
        return false;
    } else if (!this.funcId.equals(other.funcId)) {
      return false;
    }
    return true;
  }
}