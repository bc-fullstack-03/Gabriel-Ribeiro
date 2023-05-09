package com.sysmap.parrot.repository;

import com.sysmap.parrot.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String> {

}

