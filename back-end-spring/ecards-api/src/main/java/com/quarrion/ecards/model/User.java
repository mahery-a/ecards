package com.quarrion.ecards.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@ApiModel(description="All details about the user.")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    @Column(nullable = false, columnDefinition = "boolean default true") // required for non-null default value
    private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
    
    @Size(min=2, message="Name should have atleast 2 characters")
	@ApiModelProperty(notes="Name should have atleast 2 characters")
	private String name;

	@Past
	@ApiModelProperty(notes="Birth date should be in the past")
	private Date birthDate;
	
	@OneToMany(mappedBy="user")
	@JsonIgnore //TODO: https://www.thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/ 
	private List<Ecard> ecards;
	
	@ManyToMany
	@JoinTable(name = "USER_THEME", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "THEME_ID"))// to customize the join table ids
	@JsonIgnore //TODO: https://www.thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/ 
	private List<Theme> themes = new ArrayList<>();
    
    @Embedded
	private Address address;
    
    public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Ecard> getEcards() {
		return ecards;
	}

	public void setPosts(List<Ecard> ecards) {
		this.ecards = ecards;
	}
	
	public List<Theme> getThemes() {
		return themes;
	}

	public void addTheme(Theme theme) {
		this.themes.add(theme);
	}

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    public void grantAuthority(Role authority) {
        if ( roles == null ) roles = new ArrayList<>();
        roles.add(authority);
    }
    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
        return authorities;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public String toString() {
        String toStr = String.format("User[%s]",id);
        toStr += String.format("\n username[%s]",username);
        toStr += String.format("\n | email[%s]", email);
        return toStr;
    }
}
