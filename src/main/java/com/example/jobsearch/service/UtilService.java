package com.example.jobsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilService {

    public int totalPagesCounter(int listSize, int pageSize) {
        int totalPages = listSize/pageSize;
        if(listSize%pageSize > 0) {
            totalPages += 1;
        }
        return totalPages;
    }
    public <T> Page<T> toPage(List<T> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), list.size());
        List<T> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

}
