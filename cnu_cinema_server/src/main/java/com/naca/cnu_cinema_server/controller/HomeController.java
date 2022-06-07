package com.naca.cnu_cinema_server.controller;

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
public class HomeController {

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public String loginPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            String str;
            if(rset.next()){
                str = rset.getString(2);
                System.out.println(str);
                model.addAttribute("login", str);
            } else {
                model.addAttribute("login", "");
            }

            return "login";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "user", method = {RequestMethod.POST})
    public String userPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            String str;
            JSONObject jo = new JSONObject();
            while(rset.next()){
                jo.put("name", rset.getString("NAME"));
                jo.put("email", rset.getString("EMAIL"));
                jo.put("birth", rset.getString("BIRTH_DATE"));
                jo.put("sex", rset.getString("SEX"));
            }
            model.addAttribute("user", jo);

            return "user";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }


    @RequestMapping(value = "userticket", method = {RequestMethod.POST})
    public String userTicketPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while(rset.next()){
                JSONObject jo = new JSONObject();
                Statement temp_stmt = con.createStatement();
                ResultSet temp;
                jo.put("id", rset.getString("ID"));
                jo.put("seat", rset.getString("SEATS"));
                jo.put("status", rset.getString("STATUS"));
                jo.put("rcdate", rset.getString("RC_DATE"));
                temp = temp_stmt.executeQuery("SELECT SDATETIME, TNAME, MID FROM MOVIE_SCHEDULE WHERE SID = " +
                        rset.getString("SID"));
                if(temp.next()){
                    jo.put("sdatetime", temp.getString("SDATETIME"));
                    jo.put("tname", temp.getString("TNAME"));
                    ResultSet tmp = temp_stmt.executeQuery("SELECT TITLE FROM MOVIE WHERE MID = '" +
                            temp.getString("MID") + "'");
                    if (tmp.next()){
                        jo.put("title", tmp.getString("title"));
                    }
                    tmp.close();
                    tmp = temp_stmt.executeQuery("SELECT NAME FROM CUSTOMER WHERE CID = " + rset.getString("CID"));
                    tmp.next();
                    jo.put("cname", tmp.getString("NAME"));
                }
                ja.put(jo);
            }
            model.addAttribute("userticket", ja);

            return "userticket";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "movie", method = {RequestMethod.POST})
    public String movieTicketPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while(rset.next()){
                JSONObject jo = new JSONObject();
                jo.put("mid", rset.getString("MID"));
                jo.put("title", rset.getString("TITLE"));
                jo.put("openDay", rset.getString("OPEN_DAY"));
                jo.put("director", rset.getString("DIRECTOR"));
                jo.put("rating", rset.getString("RATING"));
                jo.put("length", rset.getString("LENGTH"));
                ja.put(jo);
            }
            model.addAttribute("movie", ja);

            return "movie";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "moviedetail", method = {RequestMethod.POST})
    public String movieDetailPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            String str = "";
            if (rset.next()){
                str = rset.getString("cnt1") + " " + rset.getString("cnt2");
            }
            model.addAttribute("moviedetail", str);

            return "moviedetail";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "schedule", method = {RequestMethod.POST})
    public String schedulePage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while(rset.next()){
                JSONObject jo = new JSONObject();
                Statement temp_stmt = con.createStatement();
                ResultSet temp;
                jo.put("sdatetime", rset.getString("sdatetime"));
                String tname = rset.getString("TNAME");
                String sid = rset.getString("SID");
                jo.put("tname", tname);
                temp = temp_stmt.executeQuery("SELECT SEATS FROM THEATER WHERE TNAME = '" +
                        tname + "'");
                Statement tmp_stmt = con.createStatement();
                ResultSet tmp;
                tmp = tmp_stmt.executeQuery("SELECT COUNT(CASE WHEN T.SID = " + sid + " THEN 1 END) CNT FROM TICKETING T WHERE T.STATUS = 'R'");
                if(temp.next() && tmp.next()){
                    jo.put("seats", temp.getString("SEATS"));
                    jo.put("disable", tmp.getString("CNT"));
                }
                jo.put("sid", sid);
                ja.put(jo);
            }
            model.addAttribute("schedule", ja);

            return "schedule";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "seats", method = {RequestMethod.POST})
    public String seatsPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while(rset.next()){
                JSONObject jo = new JSONObject();
                jo.put("seats", rset.getString("SEATS"));
                ja.put(jo);
            }
            model.addAttribute("seats", ja);

            return "seats";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "join", method = {RequestMethod.POST})
    public String joinPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while (rset.next()){
                JSONObject jo = new JSONObject();
                jo.put("예약자 이름", rset.getString("예약자 이름"));
                jo.put("예약자 성", rset.getString("예약자 성"));
                jo.put("예약자 id", rset.getString("예약자 id"));
                jo.put("티켓 id", rset.getString("티켓 id"));
                jo.put("티켓 상태", rset.getString("티켓 상태"));
                ja.put(jo);
            }
            model.addAttribute("join", ja);

            return "join";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "group", method = {RequestMethod.POST})
    public String groupPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while (rset.next()){
                JSONObject jo = new JSONObject();
                jo.put("일정 id", rset.getString("일정 id"));
                jo.put("예약자 이름", rset.getString("예약자 이름"));
                jo.put("cnt", rset.getString("예약 횟수"));
                ja.put(jo);
            }
            model.addAttribute("group", ja);

            return "group";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "window", method = {RequestMethod.POST})
    public String windowPage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        ResultSet rset;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            JSONArray ja = new JSONArray();
            while (rset.next()){
                JSONObject jo = new JSONObject();
                jo.put("고객 id", rset.getString("고객 id"));
                jo.put("예약 횟수", rset.getString("예약 횟수"));
                jo.put("예약 횟수 순위", rset.getString("예약 횟수 순위"));
                ja.put(jo);
            }
            model.addAttribute("window", ja);

            return "window";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String updatePage(HttpServletRequest request, Model model){
        System.out.println("안드로이드에서 접속요청");
        Statement stmt;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = request.getParameter("query");
            System.out.println("query : " + query);
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                            "d201802062",
                            "1234");
            stmt = con.createStatement();
            int s = stmt.executeUpdate(query);
            if(s == 1){
                model.addAttribute("update", "success");
            } else {
                model.addAttribute("update", "fail");
            }

            return "update";
        } catch (Exception e){
            e.printStackTrace();
            return "null";
        }
    }
}
