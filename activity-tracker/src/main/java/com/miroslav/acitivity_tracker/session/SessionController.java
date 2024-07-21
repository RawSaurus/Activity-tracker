package com.miroslav.acitivity_tracker.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("session")
public class SessionController {

    private final SessionService sessionService;

    //TODO find session
    //TODO find all sessions by name
    //TODO create session
    //TODO update session
    //TODO delete session
    //TODO copy session to another activity
    //TODO create group -> group entity
}
