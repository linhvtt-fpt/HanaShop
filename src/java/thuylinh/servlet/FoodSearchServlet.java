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
import tblCategory.TblCategoryDAO;
import tblCategory.TblCategoryDTO;
import tblFood.FoodDTO;
import tblFood.TblFoodDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "FoodSearchServlet", urlPatterns = {"/FoodSearchServlet"})
public class FoodSearchServlet extends HttpServlet {

    final String SEARCH_PAGE = "PageSearch.jsp";
    final String SEARCH_PAGE_FORADMIN = "PageSearchForAdmin.jsp";

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
        String searchValue = request.getParameter("txtSearchValue");
        String Min = request.getParameter("txtMinPrice");
        String Max = request.getParameter("txtMaxPrice");
        String cate = request.getParameter("cboCate");
        String url = SEARCH_PAGE;
        String button = request.getParameter("btnAction");
        String p = request.getParameter("page");
        int numberOfRecord = 0;
        int recordOnPage = 20;
        int pageSize;
        int offset;
        boolean isAdmin = false;
        try {
            int pageID = 1;
            if(p!=null){
                pageID= Integer.parseInt(p);
            }
            
            if (button == null) {
                HttpSession session = request.getSession();
                TblFoodDAO foodDao = new TblFoodDAO();
                 offset = (pageID - 1) * recordOnPage;
                List<FoodDTO> result = foodDao.searchFood("", "", 0, Float.MAX_VALUE, false, offset);
                numberOfRecord = foodDao.getRecord("", "", 0, Float.MAX_VALUE, false);
                pageSize = (int) Math.ceil((double) numberOfRecord / recordOnPage);
                request.setAttribute("PAGESIZE", pageSize);
                request.setAttribute("currentPage", pageID);
                request.setAttribute("RESULT_SEARCH", result);

                if (result != null) {
                    request.setAttribute("RESULT_SEARCH", result);
                }
                TblCategoryDAO cateDao = new TblCategoryDAO();
                List<TblCategoryDTO> listCate = cateDao.getCate();
                if (listCate != null) {
                    session.setAttribute("LISTCATE", listCate);
                }
            } else if (button.equals("Search")) {
                float priceMin, priceMax = 0;
                if (Min.trim().isEmpty()) {
                    priceMin = 0;
                } else {
                    priceMin = Float.parseFloat(Min);
                }
                if (Max.trim().isEmpty()) {
                    priceMax = Float.MAX_VALUE;
                } else {
                    priceMax = Float.parseFloat(Max);
                }
                if (cate.equals("ALL")) {
                    cate = "";
                }
                HttpSession session = request.getSession();
                String admin = (String) session.getAttribute("ISADMIN");
                if (admin != null) {
                    isAdmin = true;
                }
                TblFoodDAO dao = new TblFoodDAO();
                offset = (pageID - 1) * recordOnPage;
                List<FoodDTO> result = dao.searchFood(searchValue, cate, priceMin, priceMax, isAdmin, offset);
                numberOfRecord = dao.getRecord(searchValue, cate, priceMin, priceMax, isAdmin);
                pageSize = (int) Math.ceil((double) numberOfRecord / recordOnPage);
                request.setAttribute("PAGESIZE", pageSize);
                request.setAttribute("currentPage", pageID);
                request.setAttribute("RESULT_SEARCH", result);
                if (admin != null) {
                    url = SEARCH_PAGE_FORADMIN;
                }
            }
        } catch (SQLException e) {
            log("SQL FoodSearchServlet " + e.getMessage());
        } catch (NamingException e) {
            log("Naming FoodSearchServlet " + e.getMessage());
        } catch (NumberFormatException e) {
            log("FoodSearchServlet NumberFortmat " + e.getMessage());
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
