package com.rd.lab.pizza_service.domain.address;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.customer.Customer;

@Component
@Scope("prototype")
@Entity
@Table(name = "tb_address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "postal_code", nullable = false)
	private String postalCode;
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private String city;
	@Column(name = "addr_line", nullable = false)
	private String addrLine;
	@ManyToMany(mappedBy = "addr")
	private List<Customer> cust;

	public Address() {
	}

	public String getAddrLine() {
		return addrLine;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public List<Customer> getCust() {
		return cust;
	}

	public Integer getId() {
		return id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setAddrLine(String addrLine) {
		this.addrLine = addrLine;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCust(List<Customer> cust) {
		this.cust = cust;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", postalCode=" + postalCode + ", country=" + country
				+ ", city=" + city + ", addrLine=" + addrLine + "]";
	}
}