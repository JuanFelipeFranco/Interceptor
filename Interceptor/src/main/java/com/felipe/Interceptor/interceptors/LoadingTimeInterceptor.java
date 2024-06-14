package com.felipe.Interceptor.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod controller = ((HandlerMethod) handler);//cast al metodo
        logger.info("LoadingTimeInterceptor: preHandle() entrando..." + controller.getMethod().getName());

        long start = System.currentTimeMillis();
        request.setAttribute("start", start);//enviamos start por medio de request

        Random random = new Random();
        int delay= random.nextInt(500);//genera un valor de 0 a 500
        Thread.sleep(delay); //espera que nos retrasa cuando la enviamos, una pausa de 0 a 500 milisegundos
        //return true;

        Map<String,String> json = new HashMap<>();
        json.put("error", "no tienes acceso a esta pagina");
        json.put("date", new Date().toString());

        ObjectMapper mapper=new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);//convirtiendo json a string
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(jsonString);
        return false; //en este caso si se envia en foo sale la fecha y el error 401 y el mensaje personalizado
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        long end = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");
        long result = end - start;
        logger.info("Tiempo trascurrido: "+ result+ "milisegundos");
        logger.info("LoadingTimeInterceptor: postHandle() saliendo.."+((HandlerMethod) handler).getMethod().getName());

    }
}
