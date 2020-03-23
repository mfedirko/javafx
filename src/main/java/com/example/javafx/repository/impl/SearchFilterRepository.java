package com.example.javafx.repository.impl;

import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.SearchFilterCollection;
import com.example.javafx.repository.JSONFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class SearchFilterRepository extends JSONFileRepository<SearchFilterCollection> {

    public SearchFilterRepository(@Value("${app.search.filters.file.location}") FileSystemResource saveFile, @Value("${app.search.filters.file.default.location}") Resource defaultSaveFile) {
        super(saveFile, defaultSaveFile, SearchFilterCollection.class);
    }

    public boolean deleteSearchFilter(SearchFilter filter) {
        SearchFilterCollection collection = getEntity();
        int originalSize = collection.size();
        collection.remove(filter);
        if (collection.size() < originalSize) {
            return true;
        } else return false;
    }

    public SearchFilter getDefaultFilter() {
        SearchFilterCollection filters = getEntity();
        if (filters != null && !filters.isEmpty()) {
            return filters.stream().filter(SearchFilter::isDefault).findFirst().orElse(filters.get(0));
        }
        return null;
    }
    public void setDefaultFilter(SearchFilter filter) {
        filter.setDefault(true);
        if (saveSearchFilter(filter) != null) {
            getEntity().stream().filter(f -> !f.getName().equals(filter.getName())).forEach(f -> f.setDefault(false));
        }

    }
    public Iterable<SearchFilter> getAllFilters() {
        return getEntity();
    }

    public SearchFilter newSearchFilter(SearchFilter searchFilter) {
        setEntityDefaultValues(searchFilter);
        if (!isDuplicate(searchFilter)) {
            getEntity().add(searchFilter);
            return searchFilter;
        }
        return null;
    }
    public SearchFilter saveSearchFilter(SearchFilter searchFilter) {
        SearchFilter existing = findByName(searchFilter);
        if (existing != null) {
            if (existing.isDefault()) searchFilter.setDefault(true);
            int foundIndex = -1;
            for (int i=0; i < getEntity().size(); i++) {
                if (getEntity().get(i).getName().equals(existing.getName())) {
                    foundIndex = i;
                    getEntity().set(foundIndex, searchFilter);
                    return existing;
                }
            }
        }
        return null;
    }
    public SearchFilter findByName(SearchFilter searchFilter) {
        return getEntity().stream().filter(f -> f.getName().equals(searchFilter.getName())).findFirst().orElse(null);
    }

    private boolean isDuplicate(SearchFilter searchFilter) {
        return findByName(searchFilter) != null;
    }
    private void setEntityDefaultValues(SearchFilter searchFilter) {
        if (searchFilter.getName() == null) {
            searchFilter.setName("" + getEntity().size());
        }
    }
}
