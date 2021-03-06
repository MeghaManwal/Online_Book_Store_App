package com.bridgelabz.onlinebookstore.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
public @Data class WishlistDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "uuid-char")
        public UUID whishlistId;
	
	@JsonIgnore
	@OneToOne()
        @JoinColumn(referencedColumnName = "userId")
        public UserData userData;
	
//	@JsonIgnore
//      @Type(type="uuid-char")
//      public UUID userId;
	
	@JsonIgnore
	@OneToMany(mappedBy="whishlistDetails")
	@Where(clause = "order_status=true")   
        public List<BookWishlistData> wishlistdata;
	
	public WishlistDetails() {}
	
}
