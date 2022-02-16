package com.toogoodtogo;

import com.toogoodtogo.web.ControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest //????
//@ExtendWith(SpringExtension.class)
class redisTest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testSet() throws InterruptedException {
//        ZSetOperations<String, String> redisRecentSearch = redisTemplate.opsForZSet();
//        Long userId = 1L;
//
//        for (int i = 0; i < 15; i++) {
//            Double time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//            redisRecentSearch.add("search:" + 1, String.valueOf(i), time);
//            redisRecentSearch.removeRange("search:" + 1, -(10 + 1), -(10 + 1));
//            Thread.sleep(500);
//        }
//        Double time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//        redisRecentSearch.add("search:" + userId, "김치", time);
//        redisRecentSearch.removeRange("search:" + userId, -(10 + 1), -(10 + 1));
//        Thread.sleep(500);
//
//        time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//        redisRecentSearch.add("search:" + userId, "바나나", time);
//        redisRecentSearch.removeRange("search:" + userId, -(10 + 1), -(10 + 1));
//        Thread.sleep(500);
//
//        time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//        redisRecentSearch.add("search:" + userId, "옥수수", time);
//        redisRecentSearch.removeRange("search:" + userId, -(10 + 1), -(10 + 1));
//        Thread.sleep(500);
//
//        time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//        redisRecentSearch.add("search:" + userId, "우유", time);
//        redisRecentSearch.removeRange("search:" + userId, -(10 + 1), -(10 + 1));
//        Thread.sleep(500);

//        List<String> strings = new ArrayList<>(Objects.requireNonNull(
//                redisRecentSearch.reverseRange("search:" + userId, 0, -1)));
//
//        System.out.println("FIRST");
//        strings.forEach(p -> System.out.println("keyword" + p));

//        time = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS")));
//        redisRecentSearch.add("search:" + userId, "바나나", time);
//        redisRecentSearch.removeRange("search:" + userId, -(10 + 1), -(10 + 1));
//        Thread.sleep(500);
//
//        strings = new ArrayList<>(Objects.requireNonNull(
//                redisRecentSearch.reverseRange("search:" + userId, 0, -1)));
//
//        System.out.println("SECOND");
//        strings.forEach(p -> System.out.println("keyword" + p));
//
//        redisRecentSearch.remove("search:" + userId, "옥수수");
//
//        strings = new ArrayList<>(Objects.requireNonNull(
//                redisRecentSearch.reverseRange("search:" + userId, 0, -1)));
//
//        System.out.println("THIRD");
//        strings.forEach(p -> System.out.println("keyword" + p));

    }
}
