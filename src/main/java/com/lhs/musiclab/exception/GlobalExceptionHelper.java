package com.lhs.musiclab.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHelper {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e)throws Exception{
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
}
