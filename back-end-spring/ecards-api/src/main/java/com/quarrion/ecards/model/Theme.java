package com.quarrion.ecards.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "query_get_all_themes", 
				query = "Select  t  From Theme t"),		
		@NamedQuery(name = "query_get_all_themes_join_fetch", 
		query = "Select  t  From Theme t JOIN FETCH t.users s"),		
		@NamedQuery(name = "query_get_happy_themes", 
		query = "Select  t  From Theme t where name like '%Happy'") })
@Cacheable
@SQLDelete(sql="update theme set is_deleted=true where id=?") // -> softdelete: set boolean when doing a delete, the row is in fact not deleted
@Where(clause="is_deleted = false") // -> softdelete: all retrieval will check the boolean instead. IMPORTANT: doesn't apply to NativeQueries (you have to check yourself by adding "where is_deleted=false" to query)
public class Theme {

	private static Logger LOGGER = LoggerFactory.getLogger(Theme.class);
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy="theme")
	private List<ReviewV2> reviews = new ArrayList<>();
	
	@ManyToMany(mappedBy="themes")
	@JsonIgnore // -> used to exclude from exposed rest resources
	private List<User> users = new ArrayList<>();
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;

	@CreationTimestamp
	private LocalDateTime createdDate;
	
	private boolean isDeleted = true;
	
	@PreRemove // entity lifecyle
	private void preRemove(){
		LOGGER.info("Setting isDeleted to True");
		this.isDeleted = true;
	}

	protected Theme() {
	}

	public Theme(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public List<ReviewV2> getReviews() {
		return reviews;
	}

	public void addReview(ReviewV2 review) {
		this.reviews.add(review);
	}

	public void removeReview(ReviewV2 review) {
		this.reviews.remove(review);
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Theme[%s]", name);
	}
}