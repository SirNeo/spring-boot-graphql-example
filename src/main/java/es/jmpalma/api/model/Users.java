package es.jmpalma.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Users {

	/**
	 * Info about pagination (count, pages, total, size)
	 */
	private Info info;

	/**
	 * Results returned
	 */
	private List<User> results;

}
