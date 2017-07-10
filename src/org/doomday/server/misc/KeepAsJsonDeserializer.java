package org.doomday.server.misc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class KeepAsJsonDeserializer extends JsonDeserializer<String> {
	public KeepAsJsonDeserializer() {
		super();
	}
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode tree = jp.getCodec().readTree(jp);
        return tree.toString();
    }
}