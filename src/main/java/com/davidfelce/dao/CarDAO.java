package com.davidfelce.dao;

import com.davidfelce.domain.Car;
import com.davidfelce.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public void update(Car car) {
        String sql = "update car set name=?, price=? where id=?";
        jdbcTemplate.update(sql, car.getName(), car.getPrice(),car.getId());
    }

    public void save(Car car) {
        if (car.getId() == 0) {
            add(car);
        } else {
            update(car);
        }
    }

    public Car findById(int id) {
        String sql = "select * from car where id=?";
        Car car = jdbcTemplate.queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance((Car.class)));
        return car;
    }

//    public List<Car> findAll() {
//        String sql = "select * from car";
//
//        List<Car> carList = jdbcTemplate.query(
//                sql,
//                BeanPropertyRowMapper.newInstance(Car.class));
//
//        return carList;
//    }

    public List<Car> findAll() {
        String sql = "select c.id, c.name, c.price, d.id as d_id, d.name as d_name, d.date as d_date from car c left join driver d on d.car_id = c.id order by c.id asc, d.date desc";
        return jdbcTemplate.query(sql, new CarWithDrivers());
    }

    private class CarWithDrivers implements ResultSetExtractor<List<Car>> {

        public List<Car> extractData(ResultSet rs) throws SQLException,
                DataAccessException {

            Map<Integer, Car> carMap = new ConcurrentHashMap<Integer, Car>();
            Car c = null;
            while (rs.next()) {
                // car already in map?
                int id = rs.getInt("id");
                c = carMap.get(id);

                // if not, add it
                if(c == null) {
                    c = new Car();
                    c.setId(id);
                    c.setName(rs.getString("name"));
                    c.setPrice(rs.getBigDecimal("price"));
                    carMap.put(id, c);
                }

                // create driver if there's one
                int driverId = rs.getInt("d_id");
                if (driverId > 0) {
                    System.out.println("add driver id=" + driverId);
                    Driver d = new Driver();
                    d.setId(driverId);
                    d.setName(rs.getString("d_name"));
                    d.setDate(rs.getDate("d_date"));
                    d.setCar(c);
                    c.getDrivers().add(d);
                }
            }

            LinkedList<Car> ll = new LinkedList<Car>(carMap.values());
            return ll;
        }
    }

    public void addList(List<Car> carList) {
        String sql = "insert into car(name, price) values (?, ?)";

        List<Object[]> carRows = new ArrayList<>();
        for (Car car : carList) {
            carRows.add(new Object[] {car.getName(), car.getPrice()});
        }

        jdbcTemplate.batchUpdate(sql, carRows);
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
