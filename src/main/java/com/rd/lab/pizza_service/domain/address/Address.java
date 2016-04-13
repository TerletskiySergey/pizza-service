package com.rd.lab.pizza_service.domain.address;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Address {
	private static final String EMPTY_SRTRING = "";
	private static Integer count = 0;

	private Integer id;
	private String postalCode = EMPTY_SRTRING;
	private String country = EMPTY_SRTRING;
	private String city = EMPTY_SRTRING;
	private String addrLine = EMPTY_SRTRING;

	public Address() {
		this(EMPTY_SRTRING, EMPTY_SRTRING, EMPTY_SRTRING, EMPTY_SRTRING);
	}

	public Address(String postalCode, String country, String city, String addrLine) {
		super();
		this.id = ++count;
		this.postalCode = postalCode;
		this.country = country;
		this.city = city;
		this.addrLine = addrLine;
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