package com.quarrion.ecards.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;

@Entity
public class ReviewV1 {

	@Id
	@GeneratedValue
	private Long id;

	@Max(5)
	private int rating;

	@ManyToOne
	private Theme theme;

	protected ReviewV1() {
	}

	public ReviewV1(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Review[%s %s]", rating);
	}

}