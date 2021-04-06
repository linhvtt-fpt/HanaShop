/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tblFood.TblFoodDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
    final String ERROR_PAGE = "ErrorPage.html";
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
        String foodID = request.getParameter("pk");
        String urlRewriting = ERROR_PAGE;
        String searchName = request.getParameter("searchName");
        String searchMinPrice  = request.getParameter("searchMinPrice");
        String searchMaxPrice  = request.getParameter("searchMaxPrice");
        String searchCate      = request.getParameter("searchCate");
        try  {
            TblFoodDAO dao = new TblFoodDAO();
            boolean result = dao.deleteFood(foodID);
            if(result){
                urlRewriting = "DispatchServlet"
                        + "?btnAction=Search"
                        + "&txtSearchValue="+searchName
                        + "&txtMinPrice="+searchMinPrice
                        + "&txtMaxPrice="+searchMaxPrice
                        + "&cboCate="+searchCate;
                HttpSession session = request.getSession();
                String fullname = (String) session.getAttribute("ISADMIN");
                Date date = new Date();
                String note = fullname + " has updated in " + date;
                dao.insertNote(foodID, note);
            }
        }
        catch(SQLException e){
            log("DeleteServlet SQL "+ e.getMessage());
        }
        catch(NamingException e){
            log("DeleteServlet Naming "+e.getMessage());
        }
        finally{
            response.sendRedirect(urlRewriting);
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
