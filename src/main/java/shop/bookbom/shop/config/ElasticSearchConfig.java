package shop.bookbom.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    private final SecureManager secureManager;

    @Value("${secure.manager.elastic.ip}")
    private String ip;
    @Value("${secure.manager.elastic.port}")
    private String port;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(secureManager.getValue(ip) + ":" + secureManager.getValue(port))
                .build();
    }
}
