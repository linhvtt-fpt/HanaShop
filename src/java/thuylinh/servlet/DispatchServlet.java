/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    final String LOGIN_PAGE = "PageLogin.html";
    final String LOGIN_CONTROLLER = "LoginServlet";
    final String SEARCH_CONTROLLER = "FoodSearchServlet";
    final String LOGOUT_CONTROLLER = "LogOutServlet";
    final String DELETE_CONTROLLER = "DeleteServlet";
    final String LOAD_BYID_CONTROLLER = "LoadByIDServlet";
    final String UPDATE_CONTROLLER = "UpdateServlet";
    final String CREATE_CONTROLLER = "CreateFoodServlet";
    final String ADD_ITEMS_TO_CART_CONTROLLER = "AddItemsToCartServlet";
    final String VIEW_CART = "PageViewCart.jsp";
    final String REMOVE_CONTROLLER = "RemoveItemsFromCartServlet";
    final String CHECKOUT_CONTROLLER  = "CheckOutServlet";
    final String UPDATE_CART_CONTROLLER = "UpdateCartServlet";
    final String SHOPPING_HISTORY_CONTROLLER = "ShoppingHistoryServlet";
    final String SEARCH_AGAIN   = "UrlRewritingSearch";
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
        String url = SEARCH_CONTROLLER;
        String button = request.getParameter("btnAction");

        try {
            if (button == null || button.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Log In")) {
                url = LOGIN_CONTROLLER;
            } 
            else if (button.equals("Log Out")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("Update") ) {
                url = LOAD_BYID_CONTROLLER;
            } else if (button.equals("Save")) {
                url = UPDATE_CONTROLLER;
            } else if (button.equals("Create")) {
                url = CREATE_CONTROLLER;
            } else if (button.equals("Add to cart")) {
                url = ADD_ITEMS_TO_CART_CONTROLLER;
            } else if (button.equals("View cart")) {
                url = VIEW_CART;
            } else if(button.equals("Remove")){
                url = REMOVE_CONTROLLER;
            } else if(button.equals("Check out")){
                url = CHECKOUT_CONTROLLER;
            } else if(button.equals("Update Cart")){
                url = UPDATE_CART_CONTROLLER;
            } else if(button.equals("Search History")){
                url = SHOPPING_HISTORY_CONTROLLER;
            } else if(button.equals("Search Again") || button.equals("Add Items To Your Cart")||button.equals("Shopping")){
                url = SEARCH_AGAIN;
            }

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
