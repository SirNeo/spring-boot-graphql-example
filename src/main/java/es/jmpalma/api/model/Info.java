package es.jmpalma.api.model;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Info {

	/**
	 * Number of elements returned
	 */
	private Integer count;
	
	/**
	 * Total of elements in database
	 */
	private Long total;
	
	/**
	 * Total of pages for the current size
	 */
	private Integer pages;
	
	/**
	 * Current size
	 */
	private Integer size;

	public Info(Page page) {
		this.count = page.getNumberOfElements();
		this.total = page.getTotalElements();
		this.pages = page.getTotalPages();
		this.size = page.getSize();
	}
}
