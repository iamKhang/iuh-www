package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Account;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Log;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Role;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories.AccountRepository;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories.LogRepository;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.services.AccountService;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.services.RoleService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet("/controlServlet")
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RoleService roleService;
    private AccountService accountService;

    public ControlServlet() {
        roleService = new RoleService();
        accountService = new AccountService();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            AccountService accountService = new AccountService();
            int result = accountService.checkLogin(username, password);
            if (result == 1) {
                Account user = accountService.getUser(username);
                roleService = new RoleService();
                List<Role> roles = roleService.getAllRolesByAccountId(username);
                long logId = accountService.writeLogLogin(username);


                req.getSession().setAttribute("roles", roles);
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("logId", logId);
                List<Log> logs = new LogRepository().getAllLogs();
                req.setAttribute("logs", logs);
                req.getRequestDispatcher("dashboard.jsp").forward(req, res);
            } else if (result == 2) {
                req.setAttribute("errorMessage", "Wrong password");
                req.getRequestDispatcher("index.jsp").forward(req, res);
            } else if (result == 3) {
                req.setAttribute("errorMessage", "No account found");
                req.getRequestDispatcher("index.jsp").forward(req, res);
            } else {
                req.setAttribute("errorMessage", "Invalid username or password");
                req.getRequestDispatcher("index.jsp").forward(req, res);
            }
        }else if (action.equals("logout")) {
            Long logId = (Long) req.getSession().getAttribute("logId");
            accountService.writeLogLogout(logId);
            req.getSession().invalidate();
            res.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

    }
}