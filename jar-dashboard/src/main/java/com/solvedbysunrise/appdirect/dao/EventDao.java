package com.solvedbysunrise.appdirect.dao;

import com.solvedbysunrise.appdirect.entity.jpa.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends PagingAndSortingRepository<Event, String> {

}
