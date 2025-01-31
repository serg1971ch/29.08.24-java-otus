package ru.otus;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "WebCalc", urlPatterns = "/calc")
public class WebCalc extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(WebCalc.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        String operator = String.valueOf(req.getParameter("op"));

        switch (operator) {
            case "+":
                String plusResult = String.valueOf(a + b);
                out.println("<html><body>" + plusResult + "</body></html>");
            case "-":
                String minusResult = String.valueOf(a - b);
                out.println("<html><body>" + minusResult + "</body></html>");
            case "*":
                String multiplyResult = String.valueOf(a * b);
                out.println("<html><body>" + multiplyResult + "</body></html>");
            case "/":
                String divideResult = String.valueOf(a / b);
                out.println("<html><body>" + divideResult + "</body></html>");
        }
        out.close();
    }
}

