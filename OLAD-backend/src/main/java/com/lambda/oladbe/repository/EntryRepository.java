package com.lambda.oladbe.repository;

import com.lambda.oladbe.models.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {

//    List<Entry> findEntriesByEntrydate_MonthAndEntrydate_Day(Pageable pageable);
//    Entry findAllByEntrydate(Pageable pageable);

//    Entry findEntriesByEntrydate(Pageable pageable);

}
