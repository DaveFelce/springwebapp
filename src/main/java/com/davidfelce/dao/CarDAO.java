package com.davidfelce.dao;

import com.davidfelce.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CarDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getCarGreeting() {
        return "Here's a car, enjoy";
    }

    public void add(Car car) {
        String sql = "insert into car (name, price) values (?, ?)";
        jdbcTemplate.update(sql, car.getName(), car.getPrice());
    }

    public Car findById(int id) {
        String sql = "select * from car where id=?";
        Car car = jdbcTemplate.queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance((Car.class)));
        return car;
    }

    public List<Car> findAll() {
        String sql = "select * from car";

        List<Car> carList = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Car.class));

        return carList;
    }

//    private class CarMapper implements RowMapper<Car> {
//        public Car mapRow(ResultSet row, int rowNum) throws SQLException {
//            Car car = new Car();
//
//            car.setId(row.getInt("id"));
//            car.setName(row.getString("name"));
//            car.setPrice(row.getBigDecimal("price"));
//
//            return car;
//        }
//    }
}
