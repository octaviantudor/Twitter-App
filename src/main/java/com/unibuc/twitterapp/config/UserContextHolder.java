package com.unibuc.twitterapp.config;

import com.unibuc.twitterapp.persistence.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class UserContextHolder {
    private User user;
}
