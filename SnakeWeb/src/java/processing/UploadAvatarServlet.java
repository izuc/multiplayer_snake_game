package processing;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import beans.User;

import cms.CreateThumbnail;
import java.io.PrintWriter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadAvatarServlet extends HttpServlet implements Constants {
    
    private void uploadImage(HttpServletRequest request, PrintWriter out) throws FileUploadException, Exception {
            if (ServletFileUpload.isMultipartContent(request)) {
                
                String path = this.getServletContext().getRealPath(USER_UPLOAD_PATH) + File.separator;
                
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(MAX_FILE_SIZE);
                File folder = new File(path);
                if (!folder.exists()) folder.mkdir();                
                factory.setRepository(folder);
                ServletFileUpload upload = new ServletFileUpload(factory);
                
                List items = upload.parseRequest(request);

                for (Iterator i = items.iterator(); i.hasNext();) {
                    FileItem item = (FileItem) i.next();
                    if (!item.isFormField()) {
                        User user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
                        if (item.getSize() < MAX_FILE_SIZE) {
                            String filename = user.getUserName() + '_' + 
                                              System.currentTimeMillis() + 
                                              item.getName().substring(item.getName().lastIndexOf('.'));

                            item.write(new File(path + filename));
                            CreateThumbnail ct = new CreateThumbnail(path + filename);
                            ct.getThumbnail(100, CreateThumbnail.HORIZONTAL);
                            ct.saveThumbnail(new File(path + filename), CreateThumbnail.IMAGE_JPEG);
                            if (!(user.getDisplayPicture().equalsIgnoreCase(DEFAULT_AVATAR))) {
                                new File(path + user.getDisplayPicture()).delete();
                            }
                            user.setDisplayPicture(filename);
                            user.update();
                            out.println(USER_UPLOAD_PATH + filename);
                        } else {
                            out.println(USER_UPLOAD_PATH + user.getDisplayPicture());
                        }
                    }
                }
            }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        try {
                this.uploadImage(request, out);
        } catch (Exception ex) {
                out.println(ex.getMessage());
        }   
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}