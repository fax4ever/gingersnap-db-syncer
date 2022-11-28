package io.gingersnapproject.cdc;

import java.util.concurrent.CompletionStage;

import org.infinispan.commons.dataconversion.internal.Json;

public interface IndexBackend {

   CompletionStage<Void> remove(Json json);

   CompletionStage<Void> put(Json json);

}
