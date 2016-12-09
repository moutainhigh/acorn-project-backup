package com.chinadrtv.erp.core.model.client;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
@Entity
@Table(name = "SEARCH_FIELD", schema = "ACOAPP_OMS")
public class SearchField  implements Serializable {
    private Long id;

    private String name;

    private Integer type;

    private Search search;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEARCH_FIELD_SEQ")
    @SequenceGenerator(name = "SEARCH_FIELD_SEQ", sequenceName = "ACOAPP_OMS.SEARCH_FIELD_SEQ")
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

    @Column(name = "TYPE")
    public int getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEARCH_ID")
    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

}
