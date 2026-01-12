package com.example.springbootserver.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UtilityClassManager {

    /**
     * Creates a {@link Pageable} object for pagination and sorting.
     * The page size is fixed at 15 elements.
     * The sort string must be in the format "field,direction" (e.g., "title,asc").
     *
     * @param page the page number requested (0-based index)
     * @param sort the string specifying the field and sort direction, separated by a comma
     * @return a {@link Pageable} object configured with page, size, and sorting
     */
    public static Pageable createPageable(int page, String sort) {
        int size = 15;
        String[] orderBy = sort.split(",");
        Sort.Direction direction = orderBy[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, orderBy[0]));
    }

    /**
     * Splits the filter string into an array containing the filter type and value.
     * Returns {@code null} if the input is {@code null} or empty.
     *
     * @param filter the string containing the filter type and value, separated by a comma
     * @return an array with the filter type at index 0 and the filter value at index 1, or {@code null} if input is invalid
     */
    public static String [] getFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            return null;
        }
        return filter.split(",");
    }

}
