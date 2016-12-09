package com.chinadrtv.erp.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_GROUP", schema="IAGENT")
public class UserGroup
  implements Serializable
{
  private String usrId;
  private String groupId;
  private String remarks;

  @Id
  @Column(name="USRID")
  public String getUsrId()
  {
    return this.usrId;
  }
  public void setUsrId(String usrId) {
    this.usrId = usrId;
  }
  @Id
  @Column(name="GROUPID")
  public String getGroupId() { return this.groupId; }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  @Column(name="REMARKS")
  public String getRemarks() {
    return this.remarks;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}