package filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieFilter {
    final String cookieName = "userName";

    //Create Cookie
    public void createCookie(HttpServletResponse resp, String email) {
        Cookie cookie = new Cookie(cookieName, email);
        cookie.setMaxAge(4 * 60 * 60);
        resp.addCookie(cookie);
    }

    //Delete Cookie
    public void deleteCookie(HttpServletResponse resp, Cookie cookie) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    // Get Cookie
    public Cookie getCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie ckies : cookies) {
                if (cookieName.equals(ckies.getName())) {
                    return ckies;
                }
            }
        }

        return null;
    }

    // Lấy thông tin email của user
    public String getUsernameByCookies(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie ck : cookies) {
                if ("username".equals(ck.getName())) {
                    return ck.getValue();
                }
            }
        }

        return null;
    }
}
