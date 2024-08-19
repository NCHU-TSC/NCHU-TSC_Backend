package app.nchu.tsc.utilities;

import java.util.List;

import app.nchu.tsc.codegen.types.QueryPair;
import app.nchu.tsc.codegen.types.URL;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class URLComposer {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class QueryPairComposer {
        private String key;
        private List<String> value;

        @Override
        public String toString() {
            return key + "=" + String.join(",", value);
        }
    }
    
    private String href;
    private String protocol;
    private String host;
    private String hostname;
    private Integer port;
    private String pathname;
    private List<QueryPairComposer> search;
    private String hash;
    private String origin;

    public static URLComposer parse(CharSequence href) {
        URLComposer result = new URLComposer();
        result.href = href.toString();

        String[] parts = href.toString().split("//");
        if (parts.length > 1) {
            result.protocol = parts[0];
            parts = parts[1].split("/", 2);
        } else {
            result.protocol = "https";
            parts = parts[0].split("/", 2);
        }

        result.host = parts[0];

        String[] hostParts = result.host.split(":");
        result.hostname = hostParts[0];
        result.port = hostParts.length > 1 ? Integer.parseInt(hostParts[1]) : 443;

        if (parts.length > 1) {
            String[] pathParts = parts[1].split("\\?", 2);
            result.pathname = "/" + pathParts[0];

            if (pathParts.length > 1) {
                String[] queryParts = pathParts[1].split("#", 2);
                result.search = List.of(queryParts[0].split("&")).stream().map(queryPair -> {
                    String[] queryPairParts = queryPair.split("=", 2);
                    return QueryPairComposer.builder().key(queryPairParts[0]).value(List.of(queryPairParts[1].split(","))).build();
                }).toList();

                if (queryParts.length > 1) {
                    result.hash = queryParts[1];
                }
            }
        } else {
            result.pathname = "/";
        }

        result.origin = result.protocol + "//" + result.host;

        return result;
    }

    @Override
    public String toString() {
        return href;
    }

    public URL toURL() {
        return URL.newBuilder()
            .href(this.href)
            .protocol(this.protocol)
            .host(this.host)
            .hostname(this.hostname)
            .port(this.port)
            .pathname(this.pathname)
            .search(this.search.stream().map(queryPair -> QueryPair.newBuilder()
            .key(queryPair.getKey())
            .value(queryPair.getValue())
            .build()).toList())
            .hash(this.hash)
            .origin(this.origin)
            .build();
    }

}
