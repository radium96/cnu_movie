package com.naca.cnu_cinema_server.controller;

import com.naca.cnu_cinema_server.MailSending;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class AndroidController {

    // localhost:10000/login으로 접속시 실행되는 함수이다.
    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public String loginPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 String객체를 선언한다.
            String str;
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 있는지 확인한다.
            if(rset.next()){
                // 결과값에서 2번째 값인 비밀번호를 전달한다.
                str = rset.getString(2);
                // model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
                model.addAttribute("login", str);
            }
            // 만약
            else {
                model.addAttribute("login", "");
            }
            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // login.jsp를 이용하여 model에 저장된 값을 localhost:10000/login을 통해 값을 전달해준다.
            return "login";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/user로 접속시 실행되는 함수이다.
    @RequestMapping(value = "user", method = {RequestMethod.POST})
    public String userPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONObject jo = new JSONObject();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while(rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                jo.put("name", rset.getString("NAME"));
                jo.put("email", rset.getString("EMAIL"));
                jo.put("birth", rset.getString("BIRTH_DATE"));
                jo.put("sex", rset.getString("SEX"));
            }
            // 모든 값을 JSONObject로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("user", jo);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // user.jsp를 이용하여 model에 저장된 값을 localhost:10000/user를 통해 값을 전달해준다.
            return "user";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/userticket으로 접속시 실행되는 함수이다.
    @RequestMapping(value = "userticket", method = {RequestMethod.POST})
    public String userTicketPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 ResultSet객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonArray객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while(rset.next()){
                // 결과값에서 한 행의 값을 저장할 JSONObject를 선언한다.
                JSONObject jo = new JSONObject();
                // 다른 테이블의 값을 받아올 Statement를 생성하여 받아온다.
                Statement temp_stmt = con.createStatement();
                // 새로 생성한 Statement에서 결과값을 받아와 저장할 ResultSet객체를 선언한다.
                ResultSet temp;
                //JSONObject에 ResultSet에 저장된 값에서 예약 정보를 받아와 저장한다.
                jo.put("id", rset.getString("ID"));
                jo.put("seat", rset.getString("SEATS"));
                jo.put("status", rset.getString("STATUS"));
                jo.put("rcdate", rset.getString("RC_DATE"));
                // 기존 ResultSet에 저장된 sid 값을 통해 다른 테이블에서 정보를 받아와 다른 ResultSet객체에 저장한다.
                temp = temp_stmt.executeQuery("SELECT SDATETIME, TNAME, MID FROM MOVIE_SCHEDULE WHERE SID = " +
                        rset.getString("SID"));
                // 새로 받아온 ResultSet객체에서 값을 받아와 JSONObject에 저장한다.
                if(temp.next()){
                    jo.put("sdatetime", temp.getString("SDATETIME"));
                    jo.put("tname", temp.getString("TNAME"));
                    // 해당 ResultSet에 저장된 mid 값을 통해 또다른 테이블에서 정보를 받아와 다른 ResultSet객체에 저장한다.
                    ResultSet tmp = temp_stmt.executeQuery("SELECT TITLE FROM MOVIE WHERE MID = '" +
                            temp.getString("MID") + "'");
                    // 새로 받아온 ResultSet객체에서 값을 받아와 JSONObject에 저장한다.
                    if (tmp.next()){
                        jo.put("title", tmp.getString("title"));
                    }
                    // 필요한 값을 받아온 ResultSet을 close한다.
                    tmp.close();
                    // close한 ResultSet에 TICKETING테이블에 존재하는 cid값을 통해 CUSTOMER 테이블을 읽어온다.
                    tmp = temp_stmt.executeQuery("SELECT NAME FROM CUSTOMER WHERE CID = " + rset.getString("CID"));
                    if (tmp.next()){
                        // 새로 받아온 ResultSet객체에서 값을 받아와 JSONObject에 저장한다.
                        jo.put("cname", tmp.getString("NAME"));
                    }
                    // 필요한 값을 받아온 ResultSet을 close한다.
                    tmp.close();
                }
                // 이용한 ResultSet과 Statement를 close한다.
                temp.close();
                temp_stmt.close();

                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("userticket", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // userticket.jsp를 이용하여 model에 저장된 값을 localhost:10000/userticket을 통해 값을 전달해준다.
            return "userticket";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/movie로 접속시 실행되는 함수이다.
    @RequestMapping(value = "movie", method = {RequestMethod.POST})
    public String movieTicketPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while(rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                JSONObject jo = new JSONObject();
                jo.put("mid", rset.getString("MID"));
                jo.put("title", rset.getString("TITLE"));
                jo.put("openDay", rset.getString("OPEN_DAY"));
                jo.put("director", rset.getString("DIRECTOR"));
                jo.put("rating", rset.getString("RATING"));
                jo.put("length", rset.getString("LENGTH"));
                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("movie", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // movie.jsp를 이용하여 model에 저장된 값을 localhost:10000/movie를 통해 값을 전달해준다.
            return "movie";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/moviedetail로 접속시 실행되는 함수이다.
    @RequestMapping(value = "moviedetail", method = {RequestMethod.POST})
    public String movieDetailPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 String객체를 선언한다.
            String str = "";
            if (rset.next()){
                // 두 결과값을 공백을 기준으로 구분할 수 있게 한 뒤 str에 저장한다.
                str = rset.getString("cnt1") + " " + rset.getString("cnt2");
            }
            // model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("moviedetail", str);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // moviedetail.jsp를 이용하여 model에 저장된 값을 localhost:10000/moviedetail을 통해 값을 전달해준다.
            return "moviedetail";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/schedule로 접속시 실행되는 함수이다.
    @RequestMapping(value = "schedule", method = {RequestMethod.POST})
    public String schedulePage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 ResultSet객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonArray객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while(rset.next()){
                // 결과값에서 한 행의 값을 저장할 JSONObject를 선언한다.
                JSONObject jo = new JSONObject();
                // 다른 테이블의 값을 받아올 Statement를 생성하여 받아온다.
                Statement temp_stmt = con.createStatement();
                // 새로 생성한 Statement에서 결과값을 받아와 저장할 ResultSet객체를 선언한다.
                ResultSet temp;
                //JSONObject에 ResultSet에 저장된 값에서 예약 정보를 받아와 저장한다.
                jo.put("sdatetime", rset.getString("sdatetime"));
                String tname = rset.getString("TNAME");
                jo.put("tname", tname);
                String sid = rset.getString("SID");
                jo.put("sid", sid);
                // 기존 ResultSet에 저장된 tname 값을 통해 다른 테이블에서 정보를 받아와 다른 ResultSet객체에 저장한다.
                temp = temp_stmt.executeQuery("SELECT SEATS FROM THEATER WHERE TNAME = '" + tname + "'");
                // 다른 테이블의 값을 받아올 Statement를 생성하여 받아온다.
                Statement tmp_stmt = con.createStatement();
                // 새로 생성한 Statement에서 결과값을 받아와 저장할 ResultSet객체를 선언한다.
                ResultSet tmp;
                // 기존 ResultSet에 저장된 sid 값을 통해 다른 테이블에서 정보를 받아와 다른 ResultSet객체에 저장한다.
                tmp = tmp_stmt.executeQuery("SELECT COUNT(CASE WHEN T.SID = " + sid +
                        " THEN 1 END) CNT FROM TICKETING T WHERE T.STATUS = 'R'");
                if(temp.next() && tmp.next()){
                    // 두 ResultSet에서 값을 받아와 JSONObject에 저장한다.
                    jo.put("seats", temp.getString("SEATS"));
                    jo.put("disable", tmp.getString("CNT"));
                }
                // 이용한 ResultSet과 Statement를 close한다.
                temp.close();
                temp_stmt.close();
                tmp.close();
                tmp_stmt.close();

                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("schedule", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // schedule.jsp를 이용하여 model에 저장된 값을 localhost:10000/schedule을 통해 값을 전달해준다.
            return "schedule";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/seats로 접속시 실행되는 함수이다.
    @RequestMapping(value = "seats", method = {RequestMethod.POST})
    public String seatsPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while(rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                JSONObject jo = new JSONObject();
                jo.put("seats", rset.getString("SEATS"));
                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("seats", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // seats.jsp를 이용하여 model에 저장된 값을 localhost:10000/seats를 통해 값을 전달해준다.
            return "seats";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/join로 접속시 실행되는 함수이다.
    @RequestMapping(value = "join", method = {RequestMethod.POST})
    public String joinPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while (rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                JSONObject jo = new JSONObject();
                jo.put("예약자 이름", rset.getString("예매자 이름"));
                jo.put("예약자 성", rset.getString("예매자 성"));
                jo.put("예약자 id", rset.getString("예매자 id"));
                jo.put("티켓 id", rset.getString("티켓 id"));
                jo.put("티켓 상태", rset.getString("티켓 상태"));
                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("join", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // join.jsp를 이용하여 model에 저장된 값을 localhost:10000/join을 통해 값을 전달해준다.
            return "join";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/group로 접속시 실행되는 함수이다.
    @RequestMapping(value = "group", method = {RequestMethod.POST})
    public String groupPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while (rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                JSONObject jo = new JSONObject();
                jo.put("일정 id", rset.getString("일정 id"));
                jo.put("예약자 이름", rset.getString("예매자 이름"));
                jo.put("cnt", rset.getString("예매 횟수"));
                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("group", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // group.jsp를 이용하여 model에 저장된 값을 localhost:10000/group을 통해 값을 전달해준다.
            return "group";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/window로 접속시 실행되는 함수이다.
    @RequestMapping(value = "window", method = {RequestMethod.POST})
    public String windowPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement와 ResultSet을 선언한다.
        Statement stmt;
        ResultSet rset;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 전달하고 결과를 받아 rset객체로 저장한다.
            rset = stmt.executeQuery(query);
            // 클라이언트에 전달할 값을 저장할 JsonObject객체를 선언한다.
            JSONArray ja = new JSONArray();
            // ResultSet에 저장된 결과값에서 커서가 가르킬 다음 값이 없을 때까지 while문을 반복한다.
            while (rset.next()){
                //JSONObject에 ResultSet에 저장된 값에서 사용자의 정보를 받아와 저장한다.
                JSONObject jo = new JSONObject();
                jo.put("고객 id", rset.getString("고객 id"));
                jo.put("예약 횟수", rset.getString("예매 횟수"));
                jo.put("예약 횟수 순위", rset.getString("예매 횟수 순위"));
                // 필요한 값이 모두 삽입된 JSONObject를 JSONArray에 추가한다.
                ja.put(jo);
            }
            // 모든 값을 JSONArray로 저장하게되면 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("window", ja);

            // 이용한 ResultSet과 Statement를 close한다.
            rset.close();
            stmt.close();

            // window.jsp를 이용하여 model에 저장된 값을 localhost:10000/window을 통해 값을 전달해준다.
            return "window";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/update로 접속시 실행되는 함수이다.
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String updatePage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        // sql 작업에 이용할 Statement를 선언한다.
        Statement stmt;
        try {
            // 전달받은 쿼리를 저장한다.
            String query = request.getParameter("query");
            // 쿼리 확인을 위해 출력한다.
            System.out.println("query : " + query);
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracle DB에 접속하여 객체로 저장한다.
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            // 쿼리 작업을 진행하고 결과값을 받아올 Statement를 생성하여 받아온다.
            stmt = con.createStatement();
            // Statement에서 작성된 쿼리를 실행하여 데이터를 갱신하고 수행한 값을 int형 변수 s에 저장한다.
            int s = stmt.executeUpdate(query);
            // 데이터 갱신상태를 받아 model에 결과값을 추가하여 클라이언트로 전달할 준비를 한다.
            if(s == 1){
                model.addAttribute("update", "success");
            } else {
                model.addAttribute("update", "fail");
            }
            // 이용한 Statement를 close한다.
            stmt.close();

            // update.jsp를 이용하여 model에 저장된 값을 localhost:10000/update를 통해 값을 전달해준다.
            return "update";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    // localhost:10000/ticketing로 접속시 실행되는 함수이다.
    @RequestMapping(value = "ticketing", method = {RequestMethod.POST})
    public String ticketingPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        try {
            // jdbc가 연결되는지 확인한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 전달받은 메일을 보낼 이메일주소, 메일 제목, 메일 내용을 저장한다
            String to = request.getParameter("to");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            // 전달 받은 값을 확인하기위해 출력한다.
            System.out.println("to : " + to);
            System.out.println("title : " + title);
            System.out.println("content : " + content);

            // 메일을 보내는 기능을 하는 객체인 MailSending객체를 선언하고 초기화한다.
            MailSending mailSending = new MailSending();
            // MailSending객체의 sendEmail함수에 필요한 정보를 넣어서 메일을 전송한다.
            mailSending.sendEmail(to, title, content);
            // 메일 전송에 성공했다는 정보를 model에 추가하여 클라이언트로 전달할 준비를 한다.
            model.addAttribute("ticketing", "success");

            // ticketing.jsp를 이용하여 model에 저장된 값을 localhost:10000/ticketing를 통해 값을 전달해준다.
            return "ticketing";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }
}
