package com.lhs.musiclab.repository;

import com.lhs.musiclab.pojo.MLabUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MLabUserRepository extends ElasticsearchRepository<MLabUser,String> {
    public List<MLabUser> findByEmailLike(String email);
}
