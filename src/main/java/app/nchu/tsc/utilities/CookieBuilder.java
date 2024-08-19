package app.nchu.tsc.utilities;

import jakarta.servlet.http.Cookie;

public class CookieBuilder {
    
    private Cookie cookie;

    public CookieBuilder(String name, String value) {
        cookie = new Cookie(name, value);
    }

    public CookieBuilder domain(String domain) {
        cookie.setDomain(domain);
        return this;
    }

    public CookieBuilder path(String path) {
        cookie.setPath(path);
        return this;
    }

    public CookieBuilder maxAge(int maxAge) {
        cookie.setMaxAge(maxAge);
        return this;
    }

    public CookieBuilder secure(boolean secure) {
        cookie.setSecure(secure);
        return this;
    }

    public CookieBuilder httpOnly(boolean httpOnly) {
        cookie.setHttpOnly(httpOnly);
        return this;
    }

    public Cookie build() {
        return cookie;
    }
    
}
