package com.example.jobsearch.language;

import com.example.jobsearch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
@ControllerAdvice
@RequiredArgsConstructor
public class LanguageAdvice {
    private final UserService userService;
    @ModelAttribute
    public void languageAdvice(Model model, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String userLanguage = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            userLanguage = userService.getLanguage(email);
            if(userLanguage != null) {
                LocaleResolver r = new SessionLocaleResolver();
                Locale l = new Locale(userLanguage);
                r.setLocale(request,response, l);
                String newLanguage = request.getParameter("lang");
                if(newLanguage !=null && !userLanguage.equalsIgnoreCase(newLanguage)) {
                    Locale changeLocale = new Locale(newLanguage);
                    r.setLocale(request, response, changeLocale);
                    userService.setLanguage(email, newLanguage);
                    userLanguage = newLanguage;
                }

            }
        }

        if (userLanguage == null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            userLanguage = currentLocale.getLanguage();
        }

        model.addAttribute("languageCode", userLanguage);
    }

}
