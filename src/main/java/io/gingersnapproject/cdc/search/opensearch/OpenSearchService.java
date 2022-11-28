package io.gingersnapproject.cdc.search.opensearch;

import java.net.URI;

import javax.inject.Singleton;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import io.gingersnapproject.cdc.IndexBackend;
import io.gingersnapproject.cdc.search.SearchService;
import io.gingersnapproject.cdc.translation.JsonTranslator;
import io.quarkus.arc.lookup.LookupIfProperty;

@LookupIfProperty(name = "service.opensearch.enabled", stringValue = "true", lookupIfMissing = true)
@Singleton
public class OpenSearchService implements SearchService {

   private static final String INDEX_NAME = "debezium-index";

   @Override
   public IndexBackend backendForURI(URI uri, JsonTranslator<?> keyTranslator, JsonTranslator<?> valueTranslator) {
      RestClientBuilder builder = RestClient.builder(HttpHost.create(uri.toString()));
      builder.setHttpClientConfigCallback(new BasicAuthClientConfig());
      RestClient restClient = builder.build();

      return new OpenSearchIndexBackend(restClient, INDEX_NAME, keyTranslator, valueTranslator);
   }

   public class BasicAuthClientConfig implements RestClientBuilder.HttpClientConfigCallback {

      @Override
      public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
         final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
         credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));

         httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
         return httpClientBuilder;
      }
   }
}
