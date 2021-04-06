/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tblFood.FoodDTO;
import tblFood.TblFoodDAO;
import tblLogin.TblLoginDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    final String INVALID_PAGE = "PageInvalid.html";
    final String SEARCH_PAGE = "PageSearch.jsp";
    final String SEARCH_PAGE_FOR_ADMIN = "PageSearchForAdmin.jsp";

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
        String url = INVALID_PAGE;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String p = request.getParameter("page");
        int numberOfRecord = 0;
        int recordOnPage = 20;
        int pageSize;
        int offset;
        try {
            int pageID = 1;
            if (p != null) {
                pageID = Integer.parseInt(p);
            }
            TblLoginDAO dao = new TblLoginDAO();
            boolean user = dao.checkLoginCust(username, password);
            if (user) {
                String fullname = dao.findFullname(username);
                boolean isAdmin = dao.checkIsAdmin(username);
                HttpSession session = request.getSession(true);
                if (isAdmin == true && fullname != null) {
                    session.setAttribute("ISADMIN", fullname);
                    offset = (pageID - 1) * recordOnPage;
                    TblFoodDAO foodDao = new TblFoodDAO();
                    List<FoodDTO> result = foodDao.searchFood("", "", 0, Float.MAX_VALUE, isAdmin, offset);
                    numberOfRecord = foodDao.getRecord("", "", 0, Float.MAX_VALUE, isAdmin);
                    pageSize = (int) Math.ceil((double) numberOfRecord / recordOnPage);
                    request.setAttribute("PAGESIZE", pageSize);
                    request.setAttribute("currentPage", pageID);
                    if (result != null) {
                        request.setAttribute("RESULT_SEARCH", result);
                    }
                    url = SEARCH_PAGE_FOR_ADMIN;
                } else if (isAdmin == false && fullname != null) {
                    session.setAttribute("FULLNAME", fullname);
                    session.setAttribute("USERNAME", username);
                    TblFoodDAO foodDao = new TblFoodDAO();
                    offset = (pageID - 1) * recordOnPage;
                    List<FoodDTO> ListFood = foodDao.searchFood("", "", 0, Float.MAX_VALUE, isAdmin, offset);
                    numberOfRecord = foodDao.getRecord("", "", 0, Float.MAX_VALUE, isAdmin);
                    pageSize = (int) Math.ceil((double) numberOfRecord / recordOnPage);
                    request.setAttribute("PAGESIZE", pageSize);
                    request.setAttribute("currentPage", pageID);
                    if (ListFood != null) {
                        request.setAttribute("RESULT_SEARCH", ListFood);
                        url = SEARCH_PAGE;
                    }
                }

            }

        } catch (SQLException e) {
            log("SQLException LoginServlet" + e.getMessage());
        } catch (NamingException e) {
            log("NamingException LoginServlet" + e.getMessage());
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
