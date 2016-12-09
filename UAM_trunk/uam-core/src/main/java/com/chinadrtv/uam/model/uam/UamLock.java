package com.chinadrtv.uam.model.uam;

import com.chinadrtv.uam.model.BaseEntity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "UAM_LOCK", schema = "ACOAPP_UAM")
public class UamLock extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3501633929210039682L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UAM_LOCK_GENERATOR")
    @SequenceGenerator(name = "UAM_LOCK_GENERATOR", schema = "ACOAPP_UAM",
            sequenceName = "SEQ_LOCK", allocationSize = 1, initialValue = 1000000001)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "LOCK_NAME", length = 20, nullable = false)
    private String lockName;

    @Column(name = "VERSION", length = 10, nullable = false)
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
