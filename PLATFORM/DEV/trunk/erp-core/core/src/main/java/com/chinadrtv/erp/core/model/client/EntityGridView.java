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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GRID_VIEW")
public class EntityGridView extends EntityView implements Serializable {


}
