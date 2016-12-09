package com.chinadrtv.erp.core.model.client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
@Entity
@Table(name = "ENTITY_VIEW", schema = "ACOAPP_OMS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",
        discriminatorType=DiscriminatorType.STRING)
public class EntityView implements Serializable {
    private Long id;
    private String name;
    private String startUrl;
    private String viewUrl;
    private String queryUrl;
    private String saveUrl;
    private String delUrl;

    private String EntityName;
    private Set<EntityFieldView> fieldViews = new HashSet<EntityFieldView>(0);

    private Search search;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTITY_VIEW_SEQ")
    @SequenceGenerator(name = "ENTITY_VIEW_SEQ", sequenceName = "ACOAPP_OMS.ENTITY_VIEW_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "QUERY_URL", length = 100)
    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    @Column(name = "SAVE_URL", length = 100)
    public String getSaveUrl() {
        return saveUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    @Column(name = "DEL_URL", length = 100)
    public String getDelUrl() {
        return delUrl;
    }

    public void setDelUrl(String delUrl) {
        this.delUrl = delUrl;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entityView")
    public Set<EntityFieldView> getFieldViews() {
        return fieldViews;
    }

    public void setFieldViews(Set<EntityFieldView> fieldViews) {
        this.fieldViews = fieldViews;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEARCH_ID", referencedColumnName = "ID")
    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Column(name = "ENTITY_NAME", length = 100)
    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }

    @Column(name = "START_URL", length = 100)
    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    @Column(name = "VIEW_URL", length = 100)
    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

}
