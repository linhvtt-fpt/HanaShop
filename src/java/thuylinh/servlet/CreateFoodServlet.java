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
@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFoodServlet"})
public class CreateFoodServlet extends HttpServlet {

    final String ERROR_PAGE = "PageCreateFood.jsp";
        final String SEARCH_AGAIN = "UrlRewritingSearch";

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
                    log(" CreateFoodServlet FileUploadException " + e.getMessage());
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
                            log("ERROR at CreateFoodServlet: " + e.getMessage());
                        }
                    }
                }
                String foodID = (String) params.get("txtFoodID");
                String foodName = (String) params.get("txtFoodName");
                String description = (String) params.get("txtDesciption");
                String cateID = (String) params.get("cboCate");
                String p = (String) params.get("txtPrice");
                String q = (String) params.get("txtQuantity");
                int Quantity = Integer.parseInt(q);
                float price = Float.parseFloat(p);
                Date createDate = new Date();
                try {
                    FoodDTO dto = new FoodDTO(foodID, foodName, description, fileName, cateID, price, createDate, true, Quantity);
                    TblFoodDAO dao = new TblFoodDAO();
                    boolean result = dao.createFood(dto);
                    if (result) {
                        url = SEARCH_AGAIN;
                    }

                } catch (SQLException e) {
                    String errMgs = e.getMessage();
                    log("CreateFoodServlet SQLException " + e.getMessage());
                    if (errMgs.contains("duplicate")) {
                        String err = foodID + " is existed";
                        request.setAttribute("CREATE_ERROR", err);
                    }
                }
                catch(NamingException e){
                    log("CreateFoodServlet Naming "+ e.getMessage());
                }

            }

        } catch (NumberFormatException e) {
            log("CreateFoodServlet NumberFormatException : " + e.getMessage());
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
