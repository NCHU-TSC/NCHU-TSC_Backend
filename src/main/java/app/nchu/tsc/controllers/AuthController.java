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

@DgsComponent
public class AuthController {

    @Autowired
    private RedirectingService redirectingService;

    @Autowired
    private TSCSettings tscSettings;

    @Autowired
    private AuthService authService;

    @DgsMutation
    private GQL_URL login(@InputArgument String href) {
        Redirecting r = redirectingService.generateRedirecting(href, null);

        String callback_url = tscSettings.getBackendURL(true, true) + "auth/callback";

        return URLComposer.parse(authService.getLoginURL(callback_url, r.getId())).toURL();
    }

}
