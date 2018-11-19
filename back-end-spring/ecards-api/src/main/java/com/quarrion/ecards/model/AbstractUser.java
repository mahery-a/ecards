package com.quarrion.ecards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

// TODO: implement a premium User with premium features
//@Entity
//@Inheritance(strategy=InheritanceType.JOINED)// vs SINGLE_TABLE
public abstract class AbstractUser {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	protected AbstractUser() {
	}

	public AbstractUser(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("User[%s]", name);
	}
}