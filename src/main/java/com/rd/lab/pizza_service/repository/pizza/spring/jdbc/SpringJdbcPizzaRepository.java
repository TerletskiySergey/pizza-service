package com.rd.lab.pizza_service.repository.pizza.spring.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.pizza.PizzaRepository;

@Repository
public class SpringJdbcPizzaRepository extends JdbcDaoSupport implements PizzaRepository {

	private static class PizzaRowMapper implements RowMapper<Pizza> {

		@Override
		public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
			Pizza toRet = new Pizza();
			toRet.setId(rs.getInt(1));
			toRet.setName(rs.getString(2));
			toRet.setPrice(new BigDecimal(rs.getString(3)));
			toRet.setType(Pizza.Type.values()[rs.getInt(4) - 1]);
			return toRet;
		}
	}

	private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
	private static final String INSERT_SQL = "INSERT INTO %s(name, price, type) VALUES(?, ?, ?)";
	private static final String SELECT_SQL = "SELECT * FROM %s WHERE ID = ?";
	private static final String TBL_NAME = "tb_pizza";
	private static final String UPDATE_SQL = "UPDATE %s SET name = ?, price = ?, type = ? WHERE id = ?";

	@Override
	public int addPizza(Pizza toAdd) {
		String sql = String.format(INSERT_SQL, TBL_NAME);
		String name = toAdd.getName();
		double price = toAdd.getPrice().doubleValue();
		int type = toAdd.getType().ordinal() + 1;
		return getJdbcTemplate().update(sql, name, price, type);
	}

	@Override
	public boolean delete(int id) {
		String sql = String.format(DELETE_SQL, TBL_NAME);
		return getJdbcTemplate().update(sql, id) != 0;
	}

	@Override
	public Pizza getPizzaByID(Integer id) {
		String sql = String.format(SELECT_SQL, TBL_NAME);
		return getJdbcTemplate().queryForObject(sql, new PizzaRowMapper(), id);
	}

	@Override
	public int update(Pizza toUpd) {
		String sql = String.format(UPDATE_SQL, TBL_NAME);
		Integer id = toUpd.getId();
		String name = toUpd.getName();
		double price = toUpd.getPrice().doubleValue();
		int type = toUpd.getType().ordinal() + 1;
		return getJdbcTemplate().update(sql, name, price, type, id);
	}

	@Autowired
	private void setSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

}