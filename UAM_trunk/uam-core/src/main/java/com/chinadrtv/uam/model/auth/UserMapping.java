package com.chinadrtv.uam.model.auth;

import javax.persistence.*;

import com.chinadrtv.uam.model.BaseEntity;

@Entity
@Table(name = "UAM_USER_MAPPING", schema = "ACOAPP_UAM")
public class UserMapping extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 197620466446633275L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_MAPPING_GENERATOR")
    @SequenceGenerator(name = "USER_MAPPING_GENERATOR", schema = "ACOAPP_UAM",
            sequenceName = "SEQ_USER_MAPPING", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(name = "USER_ID", length = 30)
    private Long userId;
    @Column(name = "SOURCE_ID", length = 30)
    private String sourceId;
    @Column(name = "SOURCE", length = 30)
    private String source;
    @Column(name = "SOURCE_PASSWORD", length = 100)
    private String sourcePassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }
}
