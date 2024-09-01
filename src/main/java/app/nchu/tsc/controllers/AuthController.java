package app.nchu.tsc.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.tsc.codegen.types.GQL_URL;
import app.nchu.tsc.models.Redirecting;
import app.nchu.tsc.services.AuthService;
import app.nchu.tsc.services.RedirectingService;
import app.nchu.tsc.services.TSCSettings;
import app.nchu.tsc.utilities.URLComposer;
import jakarta.annotation.PostConstruct;

@DgsComponent
public class AuthController {

    @Autowired
    private RedirectingService redirectingService;

    @Autowired
    private TSCSettings tscSettings;

    @Autowired
    private AuthService authService;

    private String callback_url;

    @PostConstruct
    public void init() {
        callback_url = tscSettings.getBackendURL(true, true) + "auth/callback";
    }

    @DgsMutation
    private GQL_URL login(@InputArgument String href) {
        if(!URLComposer.parse(href).getOrigin().equals(tscSettings.getFrontendURL(true, false))) {
            throw new IllegalArgumentException("Redirected URL after login must be from the frontend.");
        }

        Redirecting r = redirectingService.generateRedirecting(href, null);

        return URLComposer.parse(authService.getLoginURL(callback_url, r.getId())).toURL();
    }

}
