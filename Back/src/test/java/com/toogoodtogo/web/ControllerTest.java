package com.toogoodtogo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.AmazonS3MockConfig;
import com.toogoodtogo.application.search.SearchService;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.configuration.EmbeddedRedisConfig;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import({AmazonS3MockConfig.class, EmbeddedRedisConfig.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DisplayProductRepository displayProductRepository;

    @Autowired
    protected ChoiceProductRepository choiceProductRepository;

    @Autowired
    protected HighestRateProductRepository highestRateProductRepository;

    @Autowired
    protected ProductRepositorySupport productRepositorySupport;

    @Autowired
    protected SignService signService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected RedisTemplate redisTemplate;

    @Autowired
    protected SearchService searchService;
}
