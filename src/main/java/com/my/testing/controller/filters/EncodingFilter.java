package com.my.testing.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.*;

import java.io.IOException;

/**
 * EncodingFilter class. Sets encoding for any values from view
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*",
           initParams = @WebInitParam(name = "encoding", value = "UTF-8"))
public class EncodingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    /**
     * Sets default encoding
     * @param config passed by application
     */
    @Override
    public void init(FilterConfig config) {
        logger.info("Default encoding filter was set");
        encoding = config.getInitParameter("encoding");
    }

    /**
     * Sets default encoding for any values from user
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encodingRequest = request.getCharacterEncoding();

        if (encoding != null && !encoding.equalsIgnoreCase(encodingRequest)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
