package com.my.testing.utils.query;

import com.my.testing.model.entities.enums.Subject;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.constants.ParameterValues.*;

public abstract class QueryBuilder {
    private static final Logger logger = LogManager.getLogger(QueryBuilder.class);
    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = ASCENDING_ORDER;
    private int offset = 0;
    private int records = 5;

    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    public QueryBuilder setNameFilter(String nameFilter) {
        if (nameFilter != null && !nameFilter.isBlank()) {
            filters.add("name LIKE '%" + nameFilter + "%' ");
        }

        return this;
    }

    public QueryBuilder setSubjectFilter(String subjectFilter) {
        if (subjectFilter != null && !subjectFilter.isBlank()) {
            filters.add("subject_id=" + Subject.valueOf(subjectFilter).getValue());
        }

        return this;
    }

    public QueryBuilder setSortField(String sortField) {
        if (sortField != null && !sortField.isBlank()) {
            this.sortField = checkSortField(sortField);
        }

        return this;
    }

    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase(DESCENDING_ORDER)) {
            this.order = DESCENDING_ORDER;
        }

        return this;
    }

    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    public String getRecordQuery() {
        return getFilterQuery();
    }

    public String getQuery() {
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

    private String getFilterQuery() {
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        stringJoiner.setEmptyValue("");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    protected abstract String getGroupByQuery();

    private String getSortQuery() {
        return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

    protected abstract String checkSortField(String sortField);

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            logger.info("wrong offset/records format");
            return false;
        }
        return true;
    }
}
