package com.training.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.training.api.Product;

public class ProductDAOJdbcImpl implements ProductDAO {

	ConnectionUtil connectionUtil;

	public ProductDAOJdbcImpl(ConnectionUtil connectionUtil) {
		super();
		this.connectionUtil = connectionUtil;
	}

	@Override
	public int insert(Product toBeInserted) {
		int id = 0;
		try (Connection c = connectionUtil.getConnection();) {
			String sql = "insert into product(product_name,product_price,product_qoh) values(?,?,?)";
			PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, toBeInserted.getName());
			s.setFloat(2, toBeInserted.getPrice());
			s.setInt(3, toBeInserted.getQoh());
			s.executeUpdate();
			// following code is for retrieving generated PK
			ResultSet keys = s.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public Optional<Product> findById(int id) {
		Optional<Product> opt = Optional.empty();
		try (Connection c = connectionUtil.getConnection();) {

			String sql = "select * from product where product_id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				Product p = mapRowToObject(rs);

				opt = Optional.of(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	private Product mapRowToObject(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt(1));
		p.setName(rs.getString(2));
		p.setPrice(rs.getFloat(3));
		p.setQoh(rs.getInt(4));
		return p;
	}

	@Override
	public List<Product> findAll() {
		List<Product> all = new ArrayList<>();
		try (Connection c = connectionUtil.getConnection();) {
			String sql = "select * from product";
			PreparedStatement s = c.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Product p = mapRowToObject(rs);
				all.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}

	@Override
	public boolean update(Product toBeUpdated) {
		try (Connection c = connectionUtil.getConnection();) {
			String sql = "update product set product_name=?,product_price=?,product_qoh=? where product_id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setString(1, toBeUpdated.getName());
			s.setFloat(2, toBeUpdated.getPrice());
			s.setInt(3, toBeUpdated.getQoh());
			s.setInt(4, toBeUpdated.getId());
			int rowCount = s.executeUpdate();
			return (rowCount == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBAccessException(e);
		}
	}

	@Override
	public boolean deleteById(int id) {
		try (Connection c = connectionUtil.getConnection();) {
			String sql = "delete from product where product_id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			int rowCount = s.executeUpdate();
			return (rowCount == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBAccessException(e);
		}

	}

}
