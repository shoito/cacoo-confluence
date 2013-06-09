package com.github.shoito.confluence.integration.cacoo.servlet;

import com.github.shoito.confluence.integration.cacoo.macro.CacooMacro;
import com.github.shoito.confluence.integration.cacoo.util.DiagramImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditorImageGeneratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String diagramImageUrl = req.getParameter(CacooMacro.PARAM_DIAGRAM_URL) + ".png";
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(diagramImageUrl));
            resp.setContentType("image/png");
            ImageIO.write(bufferedImage, "png", resp.getOutputStream());
        } catch (Exception e) {
            resp.setStatus(404);
        }
    }
}