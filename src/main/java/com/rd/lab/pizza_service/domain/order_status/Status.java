package com.rd.lab.pizza_service.domain.order_status;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

@Entity
@Table(name = "tb_status", uniqueConstraints = @UniqueConstraint(columnNames = "STATUS_TYPE"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "STATUS_TYPE")
public abstract class Status {
	public static final int MAX_STATUS_PRIORITY = Integer.MAX_VALUE;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public int add(List<Pizza> toAdd, int limit) {
		return 0;
	}

	public Integer getId() {
		return id;
	}

	public abstract int getPriority();

	public void init() {
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean canBeChangedTo(Status status) {
		return this.getPriority() <= status.getPriority();
	}

	public String toString() {
		return super.toString();
	}
}