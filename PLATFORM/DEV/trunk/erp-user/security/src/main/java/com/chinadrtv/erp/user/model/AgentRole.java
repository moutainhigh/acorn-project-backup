package com.chinadrtv.erp.user.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-11-19
 */
@Entity
@Table(name = "PGLINK", schema = "IAGENT")
public class AgentRole implements Serializable, GrantedAuthority, Comparable {

	private static final long serialVersionUID = -8680242787622753953L;
	
	private String purViewId;
    private String grpId;
    private String dsc;
    private String right;
    private String funcId;
    
    private String roleId;
    private String roleName;
    
    private Integer priority;
    
    private String description;

    @Id
    @Column(name = "PURVIEWID")
    public String getPurViewId() {
        return purViewId;
    }

    public void setPurViewId(String purViewId) {
        this.purViewId = purViewId;
    }

    @Id
    @Column(name = "GRPID")
    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    @Column(name = "DSC")
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "RIGHTS")
    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Column(name = "FUNCID")
    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }


    @Transient
    public String getAuthority() {
        return purViewId;
    }

    
    /**
	 * @return the roleId
	 */
    @Transient
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	@Transient
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the priority
	 */
	@Transient
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the description
	 */
	@Transient
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((purViewId == null) ? 0 : purViewId.hashCode());
        result = prime * result
                + ((grpId == null) ? 0 : grpId.hashCode());
        result = prime * result
                + ((dsc == null) ? 0 : dsc.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        result = prime * result
                + ((funcId == null) ? 0 : funcId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AgentRole other = (AgentRole) obj;
        if (purViewId == null) {
            if (other.purViewId != null)
                return false;
        } else if (!purViewId.equals(other.purViewId))
            return false;
        if (grpId == null) {
            if (other.grpId != null)
                return false;
        } else if (!grpId.equals(other.grpId))
            return false;
        if (dsc == null) {
            if (other.dsc != null)
                return false;
        } else if (!dsc.equals(other.dsc))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        if (funcId == null) {
            if (other.funcId != null)
                return false;
        } else if (!funcId.equals(other.funcId))
            return false;

        return true;
    }

	@Override
	public int compareTo(Object o) {
		int hashCode = 0;
		
		if(null == o) {
			hashCode = 1;
		}else {
			hashCode = this.hashCode() - o.hashCode(); 
		}		
		return hashCode;
	}

}
