package com.example.jobsearch.service;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

    public int totalPagesCounter(int listSize, int pageSize) {
        int totalPages = listSize/pageSize;
        if(listSize%pageSize > 0) {
            totalPages += 1;
        }
        return totalPages;
    }
}
