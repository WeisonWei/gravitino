package com.datastrato.graviton.dto.requests;

import com.datastrato.graviton.CatalogChange;
import com.datastrato.graviton.rest.RESTRequest;
import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value = CatalogUpdateRequest.RenameCatalogRequest.class, name = "rename"),
  @JsonSubTypes.Type(
      value = CatalogUpdateRequest.UpdateCatalogCommentRequest.class,
      name = "updateComment"),
  @JsonSubTypes.Type(
      value = CatalogUpdateRequest.SetCatalogPropertyRequest.class,
      name = "setProperty"),
  @JsonSubTypes.Type(
      value = CatalogUpdateRequest.RemoveCatalogPropertyRequest.class,
      name = "removeProperty")
})
public interface CatalogUpdateRequest extends RESTRequest {

  CatalogChange catalogChange();

  @EqualsAndHashCode
  class RenameCatalogRequest implements CatalogUpdateRequest {

    @Getter
    @JsonProperty("newName")
    private final String newName;

    public RenameCatalogRequest() {
      this(null);
    }

    public RenameCatalogRequest(String newName) {
      this.newName = newName;
    }

    @Override
    public void validate() throws IllegalArgumentException {
      Preconditions.checkArgument(
          newName != null && !newName.isEmpty(),
          "\"newName\" field is required and cannot be empty");
    }

    @Override
    public CatalogChange catalogChange() {
      return CatalogChange.rename(newName);
    }
  }

  @EqualsAndHashCode
  class UpdateCatalogCommentRequest implements CatalogUpdateRequest {

    @Getter
    @JsonProperty("newComment")
    private final String newComment;

    public UpdateCatalogCommentRequest(String newComment) {
      this.newComment = newComment;
    }

    public UpdateCatalogCommentRequest() {
      this(null);
    }

    @Override
    public void validate() throws IllegalArgumentException {
      Preconditions.checkArgument(
          newComment != null && !newComment.isEmpty(),
          "\"newComment\" field is required and cannot be empty");
    }

    @Override
    public CatalogChange catalogChange() {
      return CatalogChange.updateComment(newComment);
    }
  }

  @EqualsAndHashCode
  class SetCatalogPropertyRequest implements CatalogUpdateRequest {

    @Getter
    @JsonProperty("property")
    private final String property;

    @Getter
    @JsonProperty("value")
    private final String value;

    public SetCatalogPropertyRequest(String property, String value) {
      this.property = property;
      this.value = value;
    }

    public SetCatalogPropertyRequest() {
      this(null, null);
    }

    @Override
    public void validate() throws IllegalArgumentException {
      Preconditions.checkArgument(
          property != null && !property.isEmpty(),
          "\"property\" field is required and cannot be empty");
      Preconditions.checkArgument(
          value != null && !value.isEmpty(), "\"value\" field is required and cannot be empty");
    }

    @Override
    public CatalogChange catalogChange() {
      return CatalogChange.setProperty(property, value);
    }
  }

  @EqualsAndHashCode
  class RemoveCatalogPropertyRequest implements CatalogUpdateRequest {

    @Getter
    @JsonProperty("property")
    private final String property;

    public RemoveCatalogPropertyRequest(String property) {
      this.property = property;
    }

    public RemoveCatalogPropertyRequest() {
      this(null);
    }

    @Override
    public void validate() throws IllegalArgumentException {
      Preconditions.checkArgument(
          property != null && !property.isEmpty(),
          "\"property\" field is required and cannot be empty");
    }

    @Override
    public CatalogChange catalogChange() {
      return CatalogChange.removeProperty(property);
    }
  }
}