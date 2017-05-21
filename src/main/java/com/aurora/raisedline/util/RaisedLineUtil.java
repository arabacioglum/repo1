package com.aurora.raisedline.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aurora.raisedline.dto.UserDetailsImpl;
import com.aurora.raisedline.entities.User;

@Component
public class RaisedLineUtil {
	
	@Autowired
	public RaisedLineUtil(MessageSource messageSource) {
		RaisedLineUtil.messageSource = messageSource;
	}
	
	public static void flash(RedirectAttributes redirectAttributes,
			String kind, String messageKey) {
		
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage",
				RaisedLineUtil.getMessage(messageKey));
		
	}
	
    private static String hostAndPort;

    @Value("${hostAndPort}")
    public void setHostAndPort(String hostAndPort) {
    	RaisedLineUtil.hostAndPort = hostAndPort;
    }
    
	private static String activeProfiles;
	
    @Value("${spring.profiles.active}")
    public void setActiveProfiles(String activeProfiles) {
    	RaisedLineUtil.activeProfiles = activeProfiles;
    }
    
	public static boolean isDev() {
    	return activeProfiles.equals("dev");
    }
    
    public static String hostUrl() {
		return (isDev() ? "http://" : "https://") + hostAndPort;
	}
	
	private static MessageSource messageSource;

	public static String getMessage(String messageKey, Object... args) {
		return messageSource.getMessage(messageKey, args, Locale.getDefault());
	}

	public static void validate(boolean valid, String msgContent,
			Object... args) {
		if (!valid)
			throw new RuntimeException(getMessage(msgContent, args));
	}

	public static User getSessionUser() {
		UserDetailsImpl auth = getAuth();
		return auth == null ? null : auth.getUser();
	}

	public static UserDetailsImpl getAuth() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	    if (auth != null) {
	      Object principal = auth.getPrincipal();
	      if (principal instanceof UserDetailsImpl) {
	        return (UserDetailsImpl) principal;
	      }
	    }
	    return null;	  
	}
	
	public static int compareDates(Date d1, Date d2){
		int result = 0;;
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    cal1.setTime(d1);
	    cal2.setTime(d2);
	    if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
	        result = 1;
	    } else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
	        result = -1;
	    } else {
	        if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
	            result = 1;
	        } else if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
	            result = -1;
	        } else {
	            if (cal1.get(Calendar.DATE) > cal2.get(Calendar.DATE)) {
	                result = 1;
	            } else if (cal1.get(Calendar.DATE) < cal2.get(Calendar.DATE)) {
	                result = -1;
	            }
	        }
	    }
	    return result;
	}

}
