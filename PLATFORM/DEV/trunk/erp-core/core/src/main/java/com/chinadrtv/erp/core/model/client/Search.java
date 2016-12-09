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
@Table(name = "SEARCH", schema = "ACOAPP_OMS")
public class Search  implements Serializable {

    private Long id;
    private String name;
    private String actionUrl;

    private boolean isPaged;
    private Set<SearchField> searchFields = new HashSet<SearchField>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEARCH_SEQ")
    @SequenceGenerator(name = "SEARCH_SEQ", sequenceName = "ACOAPP_OMS.SEARCH_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "search")
    public Set<SearchField> getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(Set<SearchField> searchFields) {
        this.searchFields = searchFields;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACTION_URL", length = 100)
    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    @Column(name = "IS_PAGED")
    public boolean isPaged() {
        return isPaged;
    }

    public void setPaged(boolean paged) {
        isPaged = paged;
    }
}
