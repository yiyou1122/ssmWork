package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String uname = request.getParameter("uname");
        String upass = request.getParameter("upass");
        System.out.println("接收到用户传递的帐号:"+uname+", 密码"+upass);
        if("abc".equals(uname)&&"123".equals(upass)) {
            response.getWriter().append("很遗憾, 此帐号因涉黄 ,已被封禁~ ");
        }else {
            response.getWriter().append("<h2>")
                    .append(" 欢迎: vip用户<span style='color:red'>"+uname
                            +"</span>登录本网站, 支持本网站继续拍摄岛国动作小视频")
                    .append("</h2>");
        }
    }
}
