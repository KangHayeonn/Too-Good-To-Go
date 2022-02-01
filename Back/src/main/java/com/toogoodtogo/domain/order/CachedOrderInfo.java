package com.toogoodtogo.domain.order;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CachedOrderInfo {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private String requirement;

    private String paymentMethod;
}
