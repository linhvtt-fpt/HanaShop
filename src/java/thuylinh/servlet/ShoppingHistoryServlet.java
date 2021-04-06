/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import searchHistory.HistoryDAO;
import searchHistory.HistoryDTO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "ShoppingHistoryServlet", urlPatterns = {"/ShoppingHistoryServlet"})
public class ShoppingHistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "SearchHistory.jsp";
        String searchValue = request.getParameter("txtSearchHistory");
        String format = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            HistoryDAO dao = new HistoryDAO();
            List<HistoryDTO> result = new ArrayList<>();
            if (searchValue.trim().matches(format)) {
                result = dao.searchHistoryByDate(searchValue, username);
            } else {
                result = dao.searchHistoryByName(username, searchValue);
            }
            if (result != null) {
                request.setAttribute("SEARCHHISTORY", result);
            }
        } catch (SQLException e) {
            log("ShoppingHistoryServlet SQL " + e.getMessage());
        } catch (NamingException e) {
            log("ShoppingHistoryServlet Naming " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
