/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import edible.simple.model.dataEnum.RoleName;

/**
 * @author Kevin Hadinata
 * @version $Id: Role.java, v 0.1 2019‐09‐11 12:27 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long     id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60, name = "role_name")
    private RoleName roleName;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
