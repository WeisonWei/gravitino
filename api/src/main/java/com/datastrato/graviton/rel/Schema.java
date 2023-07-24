/*
 * Copyright 2023 Datastrato.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.graviton.rel;

import com.datastrato.graviton.Auditable;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * An interface representing a schema in the {@link com.datastrato.graviton.Catalog}. A Schema is a
 * basic container of relational objects, like tables, views, etc. A Schema can be self-nested,
 * which means it can be schema1.schema2.table.
 *
 * <p>This defines the basic properties of a schema. A catalog implementation with {@link
 * SupportsSchemas} should implement this interface.
 */
public interface Schema extends Auditable {

  /** Return the name of the Schema. */
  String name();

  /** Return the comment of the Schema. Null is returned if the comment is not set. */
  @Nullable
  default String comment() {
    return null;
  }

  /** Return the properties of the Schema. An empty map is returned if no properties are set. */
  default Map<String, String> properties() {
    return Collections.emptyMap();
  }
}