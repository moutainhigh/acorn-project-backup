package com.chinadrtv.erp.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="USR", schema="IAGENT")
public class AgentUser
  implements Serializable, UserDetails
{
  private String userId;
  private String name;
  private String password;
  private String title;
  private String defGrp;
  private String adcGroup;
  private String valid;
  private Date lastDt;
  private String workGrp;
  private String department;
  private Set<AgentRole> agentRoles = new HashSet();

  @Id
  @Column(name="USRID")
  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Column(name="NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name="TITLE")
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(name="DEFGRP")
  public String getDefGrp() {
    return this.defGrp;
  }

  public void setDefGrp(String defGrp) {
    this.defGrp = defGrp;
  }

  @Column(name="ACDGROUP")
  public String getAdcGroup() {
    return this.adcGroup;
  }

  public void setAdcGroup(String adcGroup) {
    this.adcGroup = adcGroup;
  }

  @Column(name="VALID")
  public String getValid() {
    return this.valid;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }

  @Column(name="LASTDT")
  public Date getLastDt() {
    return this.lastDt;
  }

  public void setLastDt(Date lastDt) {
    this.lastDt = lastDt;
  }

  @Column(name="WORKGRP")
  public String getWorkGrp() {
    return this.workGrp;
  }

  public void setWorkGrp(String workGrp) {
    this.workGrp = workGrp;
  }

  public void setAgentRoles(Set<AgentRole> agentRoles)
  {
    this.agentRoles = agentRoles;
  }

  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return this.agentRoles;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name="PASSWORD")
  public String getPassword() {
    return this.password;
  }

  @Transient
  public String getUsername() {
    return this.name;
  }

  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }

  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }

  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Transient
  public boolean isEnabled() {
    return (this.valid != null) && (this.valid.equalsIgnoreCase("-1"));
  }

  @Transient
  public String getDepartment()
  {
    return this.department;
  }

  public void setDepartment(String department)
  {
    this.department = department;
  }
}