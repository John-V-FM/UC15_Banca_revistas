package com.pi.banca.config;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TemaInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            // Verifica se o cookie "pref-estilo" está presente
            String tema = "claro"; // Valor padrão
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("pref-estilo".equals(cookie.getName())) {
                        tema = cookie.getValue();
                        break;
                    }
                }
            }

            // Adiciona o tema ao modelo
            modelAndView.addObject("css", tema);
        }
    }
}
