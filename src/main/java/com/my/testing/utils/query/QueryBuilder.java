package com.my.testing.utils.query;

import com.my.testing.model.entities.enums.Subject;
import java.util.*;
import static com.my.testing.controller.actions.constants.ParameterValues.*;

/**
 * Abstract QueryBuilder. Defines all methods to build query to obtain sorted, ordered and limited list of entities
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public abstract class QueryBuilder {
    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = ASCENDING_ORDER;
    private int offset = 0;
    private int records = 5;

    /**
     * @param sortField by default
     */
    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    /**
     * Creates name filter for query
     * @param nameFilter used name for query
     * @return QueryBuilder
     */
    public QueryBuilder setNameFilter(String nameFilter) {
        if (nameFilter != null && !nameFilter.isBlank()) {
            filters.add("name LIKE '%" + nameFilter + "%' ");
        }

        return this;
    }

    /**
     * Creates subject filter for query
     * @param subjectFilter can be any possible subject
     * @return QueryBuilder
     */
    public QueryBuilder setSubjectFilter(String subjectFilter) {
        if (subjectFilter != null && !subjectFilter.isBlank()) {
            filters.add("subject_id=" + Subject.valueOf(subjectFilter).getValue());
        }

        return this;
    }

    /**
     * Sets sort field, but will check if it
     * @param sortField will be checked in subclasses to avoid injections
     * @return QueryBuilder
     */
    public QueryBuilder setSortField(String sortField) {
        if (sortField != null && !sortField.isBlank()) {
            this.sortField = checkSortField(sortField);
        }

        return this;
    }

    /**
     * Sets sorting order
     * @param order sorting order (ASC by default)
     * @return QueryBuilder
     */
    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase(DESCENDING_ORDER)) {
            this.order = DESCENDING_ORDER;
        }

        return this;
    }

    /**
     * Sets limits for pagination
     * @param offset record to start with. Check if valid, set by default if not
     * @param records number of records per page. Checks if valid, set by default if not
     * @return QueryBuilder
     */
    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    /**
     * @return filter query to user in DAO to obtain number of records
     */
    public String getRecordQuery() {
        return getFilterQuery();
    }

    /**
     * @return complete query for DAO to obtain list of Entities
     */
    public String getQuery() {
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

    private String getFilterQuery() {
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        stringJoiner.setEmptyValue("");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * Should be implemented in subclasses
     * @return group by some filed or empty
     */
    protected abstract String getGroupByQuery();

    private String getSortQuery() {
        return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

    /**
     * Should be implemented in subclasses
     * @param sortField field that should be checked
     * @return sort field if it's suitable or default
     */
    protected abstract String checkSortField(String sortField);

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
