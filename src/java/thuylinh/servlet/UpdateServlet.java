/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tblFood.FoodDTO;
import tblFood.TblFoodDAO;

/**
 *
 * @author Thuy Linh
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    final String UPDATE_PAGE = "PageUpdate.jsp";
    final String LOAD_PAGE = "LoadByIDServlet";
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
        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("ISADMIN");
        String url = ERROR_PAGE;
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {

            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    log("UpdateServlet FileUpload " + e.getMessage());
                }
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                String fileName = null;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString());
                    } else {
                        try {
                            String itemName = item.getName();
                            fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                            String realPath = getServletContext().getRealPath("/") + "images\\" + fileName;
                            File saveFile = new File(realPath);
                            item.write(saveFile);
                        } catch (Exception e) {
                            log("UpdateServlet Exception " + e.getMessage());
                        }
                    }
                }
                String foodID = (String) params.get("pk");
                String foodName = (String) params.get("txtFoodName");
                String description = (String) params.get("txtFoodDescription");
                String p = (String) params.get("txtFoodPrice");
                float price = Float.parseFloat(p);
                String q = (String) params.get("txtQuantity");
                int quantity = Integer.parseInt(q);
                String cateID = (String) params.get("cboCate");
                String status = (String) params.get("cboStatus");
                boolean statusUp = false;
                Date dateUpdate = new Date();
                String note = fullname + " has updated in " + dateUpdate;
                if (status.equals("Active")) {
                    statusUp = true;
                } else if (status.equals("Inactive")) {
                    statusUp = false;
                }
                FoodDTO dto = new FoodDTO(foodID, foodName, description, fileName, cateID, price, dateUpdate, statusUp, quantity);
                TblFoodDAO dao = new TblFoodDAO();
                boolean result = dao.updateFood(dto, note);
                if (result) {
                    request.setAttribute("SUCCESS", "Update SuccessFul!");
                    url = LOAD_PAGE;
                }
            }
        } catch (SQLException e) {
            log("SQL UpdateServlet " + e.getMessage());
        } catch (NamingException e) {
            log("Naming UpdateServlet " + e.getMessage());
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
