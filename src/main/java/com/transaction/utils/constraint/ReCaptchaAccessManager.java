package com.transaction.utils.constraint;

import com.transaction.services.ReCaptchaService;
import com.transaction.utils.constraint.security.AccessStatus;
import com.transaction.utils.constraint.security.AccessStatusSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@Named
public class ReCaptchaAccessManager implements AccessStatusSource<Recaptcha> {
    @Inject
    private ReCaptchaService reCaptchaService;
    @Override
    public AccessStatus getStatus(Recaptcha accessConstraint) {
       final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
       final String token = request.getParameter("recaptcha-token");

       boolean isValid = reCaptchaService.verifyCaptchaToken(token);

       return isValid? AccessStatus.allowed() : AccessStatus.denied("Captcha token is invalid");
    }
}
