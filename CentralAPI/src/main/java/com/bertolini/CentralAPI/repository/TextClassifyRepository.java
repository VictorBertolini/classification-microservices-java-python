package com.bertolini.CentralAPI.repository;

import com.bertolini.CentralAPI.domain.TextClassify;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextClassifyRepository extends JpaRepository<@NonNull TextClassify, @NonNull Long> {
}
