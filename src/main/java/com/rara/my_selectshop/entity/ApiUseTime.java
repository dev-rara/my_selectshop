package com.rara.my_selectshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ApiUseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(nullable = false)
	private Long totalTime;

	public ApiUseTime(User user, Long totalTime) {
		this.user = user;
		this.totalTime = totalTime;
	}

	public void addUseTime(Long userTime) {
		this.totalTime += userTime;
	}

}
