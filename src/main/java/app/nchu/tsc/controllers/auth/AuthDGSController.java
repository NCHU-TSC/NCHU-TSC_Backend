package app.nchu.tsc.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.auth.Authenticator;
import app.nchu.tsc.codegen.types.URL;
import app.nchu.tsc.models.Redirecting;
import app.nchu.tsc.services.RedirectingService;
import app.nchu.tsc.services.TSCSettings;
import app.nchu.tsc.utilities.URLComposer;
import jakarta.annotation.PostConstruct;

@DgsComponent
public class AuthDGSController {

    @Autowired
    private RedirectingService redirectingService;

    @Autowired
    private TSCSettings tscSettings;

    private Authenticator authenticator;

    @PostConstruct
    public void init() {
        this.authenticator = new Authenticator(tscSettings.getClientID(), tscSettings.getClientToken());
    }

    @DgsMutation
    private URL login(@InputArgument String href) {
        Redirecting r = redirectingService.generateRedirecting(href, null);

        String callback_url = tscSettings.getBackendURL(true, true) + "auth/callback";

        return URLComposer.parse(authenticator.getLoginURL(callback_url, r.getId())).toURL();
    }

}
