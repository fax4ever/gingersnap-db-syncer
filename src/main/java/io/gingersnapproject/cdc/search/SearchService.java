package io.gingersnapproject.cdc.search;

import java.net.URI;

import io.gingersnapproject.cdc.IndexBackend;
import io.gingersnapproject.cdc.translation.JsonTranslator;

public interface SearchService {

   boolean supportsURI(URI uri);

   IndexBackend backendForURI(URI uri, JsonTranslator<?> keyTranslator, JsonTranslator<?> valueTranslator);

}
