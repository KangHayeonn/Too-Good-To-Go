package com.toogoodtogo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toogoodtogo.AmazonS3MockConfig;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ChoiceProductRepository;
import com.toogoodtogo.domain.shop.product.DisplayProductRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(AmazonS3MockConfig.class)
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
    protected RefreshTokenRepository refreshTokenRepository;

    @Autowired
    protected DisplayProductRepository displayProductRepository;

    @Autowired
    protected ChoiceProductRepository choiceProductRepository;

    @Autowired
    protected SignService signService;

    @Autowired
    protected PasswordEncoder passwordEncoder;
}
