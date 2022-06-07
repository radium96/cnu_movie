package com.naca.cnu_cinema.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class HomeController {

//    @PostMapping(value = "android")
//    @ResponseBody
//    public String androidResponse(@RequestBody User user){
//        System.out.println("Connection from Android");
//        System.out.println("id: " + user.getId() + ", pw: " + user.getPw());
//
//        return "1";
//    }

    //    @RequestMapping("/")
//    public String index(){
//        System.out.println("index");
//        return "null";
//    }
//

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
                jo.put("tname", tname);
                temp = temp_stmt.executeQuery("SELECT SEATS FROM THEATER WHERE TNAME = '" +
                        tname + "'");
                if(temp.next()){
                    jo.put("seats", temp.getString("SEATS"));
                }
                jo.put("sid", rset.getString("SID"));
                ja.put(jo);
            }
            model.addAttribute("schedule", ja);

            return "schedule";
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
