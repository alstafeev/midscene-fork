package com.midscene.core.utils;

import lombok.experimental.UtilityClass;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@UtilityClass
public class JsonResponseToPojoMapper {

  private final ObjectMapper MAPPER = JsonMapper.builder()
      .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
      .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
      .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, true)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .build();

  public <T> T mapResponseToClass(String jsonResponse, Class<T> mappedClass) {
    String clearedJson = cleanMarkdown(jsonResponse);

    return MAPPER.readValue(clearedJson, mappedClass);
  }

  private String cleanMarkdown(String input) {
    return input.replaceAll("^```[a-z]*\\s*", "")
        .replaceAll("\\s*```$", "")
        .trim();
  }
}
