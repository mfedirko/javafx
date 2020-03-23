package com.example.javafx.model;

public class SearchContext {
    private SearchContext(){}

    private static SearchFilter activeSearchFilter;
    private static SearchFilter filterToEdit;

    public static void setActiveSearchFilter(SearchFilter filter) {
        SearchContext.activeSearchFilter = filter;
    }

    public static SearchFilter activeSearchFilter() {
        return activeSearchFilter;
    }

    public static SearchFilter getFilterToEdit() {
        return filterToEdit;
    }

    public static void setFilterToEdit(SearchFilter filterToEdit) {
        SearchContext.filterToEdit = filterToEdit;
    }
}
