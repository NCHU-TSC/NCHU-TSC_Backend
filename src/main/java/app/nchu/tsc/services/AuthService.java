package app.nchu.tsc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.nchu.auth.Authenticator;
import app.nchu.auth.UserInfo;
import app.nchu.auth.UserInfoFetcher;
import app.nchu.tsc.codegen.types.GQL_InfoFromAuthServer;
import jakarta.annotation.PostConstruct;

@Component
public class AuthService {

    @Autowired
    TSCSettings tscSettings;

    private Authenticator authenticator;
    private UserInfoFetcher userInfoFetcher;

    @PostConstruct
    public void init() {
        this.authenticator = new Authenticator(tscSettings.getClientID(), tscSettings.getClientToken());
        this.userInfoFetcher = new UserInfoFetcher(authenticator);
    }

    public String getLoginURL(String href, String state) {
        return authenticator.getLoginURL(href, state);
    }

    public UserInfo getUserInfo(String resId, String resToken) {
        return userInfoFetcher.fetch(resId, resToken);
    }

    public static GQL_InfoFromAuthServer toUserInfo(UserInfo userInfo) {
        GQL_InfoFromAuthServer result = new GQL_InfoFromAuthServer();

        result.setNumber(userInfo.getLoginId());
        result.setName(userInfo.getName());
        result.setEmail(userInfo.getEmail());
        result.setValidEmail(userInfo.getEmailVerified());
        result.setGender(userInfo.getGender());
        result.setDepartment(userInfo.getDepartment());
        result.setGrade(userInfo.getGrade());
        result.setPhoto(userInfo.getPhoto());

        return result;
    }
    
}
