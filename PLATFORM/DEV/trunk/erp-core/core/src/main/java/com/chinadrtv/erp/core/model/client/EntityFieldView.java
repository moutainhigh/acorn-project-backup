package com.chinadrtv.erp.core.model.client;


import javax.persistence.*;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
@Entity
@Table(name = "ENTITY_FIELD_VIEW", schema = "ACOAPP_OMS")
public class EntityFieldView implements Serializable {

    private Long id;



    private String field;

    private String title;

    private Integer type;

    private EntityView entityView;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTITY_FIELD_VIEW_SEQ")
    @SequenceGenerator(name = "ENTITY_FIELD_VIEW_SEQ", sequenceName = "ACOAPP_OMS.ENTITY_FIELD_VIEW_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Column(name = "TITLE", length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "TYPE")
    public int getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "FIELD")
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_VIEW_ID")
    public EntityView getEntityView() {
        return entityView;
    }

    public void setEntityView(EntityView entityView) {
        this.entityView = entityView;
    }

}
