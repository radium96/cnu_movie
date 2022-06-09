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
		// Spring서버를 실행한다.
		SpringApplication.run(CnuCinemaServerApplication.class, args);

		// 서버를 실행할 때마다 상영시간이 지난 스케쥴에 대해 상태변경을 진행한다.
		Statement stmt;
		try{
			// jdbc가 연결되는지 확인한다.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 현재 시각을 저장하여 Date객체에 저장한다.
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, 9);
			Date d = c.getTime();
			// Date객체에 저장된 시간을 일정한 포맷으로 출력되도록하는 SimpleDateFormat을 선언하고 포맷을 설정한다.
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// 데이터 갱신을 진행할 쿼리를 작성한다.
			String query = "UPDATE TICKETING T " +
					"SET T.STATUS = 'W', T.RC_DATE = (SELECT S.SDATETIME FROM MOVIE_SCHEDULE S WHERE S.SID = T.SID) " +
					"WHERE T.SID IN (SELECT S.SID FROM MOVIE_SCHEDULE S WHERE S.SDATETIME <= TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD HH24:MI'))";

			// Oracle DB에 접속하여 객체로 저장한다.
			Connection con =
					DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
							"d201802062",
							"1234");
			// 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
			stmt = con.createStatement();
			// Statement에서 작성된 쿼리를 전달하고 결과를 받아 s에 저장한다.
			int s = stmt.executeUpdate(query);
			// s의 상태에 따라 서버에서 출력을 진행한다.
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
