package kr.yh;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
@Slf4j
public class H2Runner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        try(Connection connection = dataSource.getConnection()){
            log.info(connection.getMetaData().getURL());
            log.info(connection.getMetaData().getUserName());

            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE USERS (ID INTEGER NOT NULL, name VARCHAR(255),  PRIMARY KEY (ID))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        jdbcTemplate.execute("INSERT INTO USERS VALUES(1, 'yonghwan')");
    }
}
