package io.gingersnapproject.cdc.configuration;

import java.net.URI;
import java.util.Optional;

import io.smallrye.config.WithDefault;

public interface Indexing {

   @WithDefault("false")
   boolean enabled();

   Optional<URI> uri();

}
