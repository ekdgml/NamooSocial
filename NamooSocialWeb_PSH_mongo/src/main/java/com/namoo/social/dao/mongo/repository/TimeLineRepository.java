package com.namoo.social.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.social.dao.mongo.document.TimeLineDoc;

public interface TimeLineRepository extends CrudRepository<TimeLineDoc, String>{
	//
	List<TimeLineDoc> findByOwnerId(String userId);
}
