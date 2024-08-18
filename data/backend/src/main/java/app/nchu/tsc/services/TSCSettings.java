package app.nchu.tsc.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Getter
@Service
public class TSCSettings {

    @Value("${tsc.protocol}")
    private String protocal;

    @Value("${tsc.host}")
    private String host;

    @Value("${tsc.backend.prefix}")
    private String backendPrefix;

    @Value("${tsc.client.id}")
    private String clientID;

    @Value("${tsc.client.token}")
    private String clientToken;

    public String getFrontendURL(Boolean withProtocal, Boolean rootSign) {
        StringBuilder url = new StringBuilder();
        if (withProtocal) {
            url.append(this.protocal);
            url.append("://");
        }

        url.append(this.host);

        if (rootSign) {
            url.append('/');
        }

        return url.toString();
    }

    public String getBackendURL(Boolean withProtocal, Boolean rootSign) {
        StringBuilder url = new StringBuilder();
        if (withProtocal) {
            url.append(this.protocal);
            url.append("://");
        }

        url.append(this.backendPrefix);
        url.append('.');
        url.append(this.host);

        if (rootSign) {
            url.append('/');
        }

        return url.toString();
    }

}
