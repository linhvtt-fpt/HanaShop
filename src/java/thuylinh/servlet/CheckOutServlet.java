/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import cart.CartObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tblBill.BillDTO;
import tblBill.tblBillDAO;
import tblBillDetail.BillDetailDTO;
import tblBillDetail.tblBillDetailDAO;
import tblFood.FoodDTO;
import tblFood.TblFoodDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    final String SEARCH_PAGE = "PageSearch.jsp";
    final String VIEW_CART = "PageViewCart.jsp";

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
        //BillID: HD00HANASHOP00username*xxx
        //BillDetailID : BillID+countProduct
        String url = VIEW_CART;
        try {
            HttpSession session = request.getSession(true);
            CartObject cart = (CartObject) session.getAttribute("CUSTCART");
            tblBillDAO dao = new tblBillDAO();
            String username = (String) session.getAttribute("USERNAME");
            String billID = dao.getLastOrderByUser(username);

            if (billID == null) {
                billID = "HD00HANASHOP00" + username + "-1";
            } else {
                String[] tmp = billID.split("-");
                int count = Integer.parseInt(tmp[1].trim());
                Random random = new Random();
                int number = random.nextInt(2000);
                billID = "HD00HANASHOP00" + username + "-" + (count + number);
            }
            Date date = new Date();
            float total = cart.total();
            BillDTO dto = new BillDTO(billID, username, total, date);
            int countList = 0;
            TblFoodDAO foodDao = new TblFoodDAO();

            for (FoodDTO food : cart.getItems().values()) {
                FoodDTO foodInDB = foodDao.findByFoodID(food.getFoodID());
                if (foodInDB.getQuantity() >= food.getQuantity()) {
                    countList++;
                } else {
                    String error = food.getName() + " remaining quanity " + foodInDB.getQuantity();
                    session.setAttribute("ERROR_CHECKOUT", error);
                    url = VIEW_CART;
                    return;
                }
            }
            if (countList == cart.getItems().size()) {
                if (dao.checkOut(dto)) {
                    int count = 1;
                    for (FoodDTO food : cart.getItems().values()) {
                        String billDetailID = billID + "-" + count++;
                        FoodDTO foodInDB = foodDao.findByFoodID(food.getFoodID());
                        int quantityCur = foodInDB.getQuantity() - food.getQuantity();
                        BillDetailDTO billDetailDTO = new BillDetailDTO(billID, billDetailID, food.getFoodID(), food.getQuantity(), food.getPrice());
                        tblBillDetailDAO billDetailDao = new tblBillDetailDAO();
                        billDetailDao.insertBillDetail(billDetailDTO);
                        foodDao.updateQuantity(quantityCur, food.getFoodID());
                    }
                    session.removeAttribute("CUSTCART");
                    url = "DispatchServlet"
                            + "?btnAction=Search"
                            + "&txtSearchValue="
                            + "&txtMinPrice="
                            + "&txtMaxPrice="
                            + "&cboCate=ALL";
                } else {
                    session.setAttribute("ERROR", "Create bill failed");
                    url = VIEW_CART;
                }
            }

        } catch (SQLException e) {
            log("CheckOutServlet SQL " + e.getMessage());
        } catch (NamingException e) {
            log("CheckOutServlet Naming " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
