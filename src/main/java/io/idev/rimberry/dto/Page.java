package io.idev.rimberry.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Page
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-23T17:57:51.097069300+01:00[Europe/Paris]")
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("currentPage")
	private Integer currentPage;

	@JsonProperty("totalPages")
	private Integer totalPages;

	@JsonProperty("totalElements")
	private Integer totalElements;

	@JsonProperty("content")
	@Valid
	private List<T> content = null;

	@JsonProperty("hasNext")
	private Boolean hasNext;

	@JsonProperty("hasPrevious")
	private Boolean hasPrevious;

	public Page currentPage(Integer currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	/**
	 * Get currentPage
	 * 
	 * @return currentPage
	 */

	@Schema(name = "currentPage", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Page totalPages(Integer totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	/**
	 * Get totalPages
	 * 
	 * @return totalPages
	 */

	@Schema(name = "totalPages", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Page totalElements(Integer totalElements) {
		this.totalElements = totalElements;
		return this;
	}

	/**
	 * Get totalElements
	 * 
	 * @return totalElements
	 */

	@Schema(name = "totalElements", example = "100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Page content(List<T> content) {
		this.content = content;
		return this;
	}

	public Page addContentItem(T contentItem) {
		if (this.content == null) {
			this.content = new ArrayList<>();
		}
		this.content.add(contentItem);
		return this;
	}

	/**
	 * Get content
	 * 
	 * @return content
	 */
	@Valid
	@Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Page hasNext(Boolean hasNext) {
		this.hasNext = hasNext;
		return this;
	}

	/**
	 * Get hasNext
	 * 
	 * @return hasNext
	 */

	@Schema(name = "hasNext", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Page hasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
		return this;
	}

	/**
	 * Get hasPrevious
	 * 
	 * @return hasPrevious
	 */

	@Schema(name = "hasPrevious", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	public Boolean getHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Page page = (Page) o;
		return Objects.equals(this.currentPage, page.currentPage) && Objects.equals(this.totalPages, page.totalPages)
				&& Objects.equals(this.totalElements, page.totalElements) && Objects.equals(this.content, page.content)
				&& Objects.equals(this.hasNext, page.hasNext) && Objects.equals(this.hasPrevious, page.hasPrevious);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentPage, totalPages, totalElements, content, hasNext, hasPrevious);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Page {\n");
		sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
		sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
		sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
		sb.append("    content: ").append(toIndentedString(content)).append("\n");
		sb.append("    hasNext: ").append(toIndentedString(hasNext)).append("\n");
		sb.append("    hasPrevious: ").append(toIndentedString(hasPrevious)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
