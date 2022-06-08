package com.naca.cnu_cinema_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class CnuCinemaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnuCinemaServerApplication.class, args);

		Statement stmt;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, 9);
			Date d = c.getTime();
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String query = "UPDATE TICKETING T " +
					"SET T.STATUS = 'W', T.RC_DATE = (SELECT S.SDATETIME FROM MOVIE_SCHEDULE S WHERE S.SID = T.SID) " +
					"WHERE T.SID IN (SELECT S.SID FROM MOVIE_SCHEDULE S WHERE S.SDATETIME <= TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD HH24:MI'))";

			Connection con =
					DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
							"d201802062",
							"1234");
			stmt = con.createStatement();
			int s = stmt.executeUpdate(query);
			if(s >= 1){
				System.out.println("티켓 상태 갱신 완료");
			} else {
				System.out.println("티켓 상태 갱신 실패 또는 없음");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
