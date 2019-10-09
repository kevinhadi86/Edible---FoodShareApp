/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */
package edible.simple.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

/**
 * @author Kevin Hadinata
 * @version $Id: User.java, v 0.1 2019‐09‐11 11:59 Kevin Hadinata Exp $$
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
                                             @UniqueConstraint(columnNames = { "email" }) })
public class User extends DataAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    @NotBlank
    @Size(max = 255)
    private String        username;
    @NotBlank
    @Size(max = 255)
    @NaturalId
    @Email
    private String        email;
    @NotBlank
    @Size(max = 255)
    private String        password;
    @NotBlank
    @Size(max = 255)
    private String        name;
    @Size(max = 255)
    private String        bio;
    @Size(max = 30)
    private String        phonenumber;
    private String        imageurl;
    private int           rating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role>     roles       = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Offer> offers = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Location location;

    public User() {
    }

    public User(String name, String username, String email, String password, String phonenumber) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
