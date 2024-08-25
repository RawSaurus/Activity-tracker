package com.miroslav.acitivity_tracker.session;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T10:03:12+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class SessionMapperImpl implements SessionMapper {

    @Override
    public Session toEntity(SessionRequest request) {
        if ( request == null ) {
            return null;
        }

        Session.SessionBuilder session = Session.builder();

        session.name( request.name() );
        session.info( request.info() );

        return session.build();
    }

    @Override
    public Session toEntity(SessionResponse response) {
        if ( response == null ) {
            return null;
        }

        Session.SessionBuilder session = Session.builder();

        session.name( response.name() );
        session.info( response.info() );
        session.start( response.start() );
        session.finish( response.finish() );
        session.duration( response.duration() );

        return session.build();
    }

    @Override
    public SessionRequest toRequest(Session session) {
        if ( session == null ) {
            return null;
        }

        String name = null;
        String info = null;

        name = session.getName();
        info = session.getInfo();

        SessionRequest sessionRequest = new SessionRequest( name, info );

        return sessionRequest;
    }

    @Override
    public SessionResponse toResponse(Session session) {
        if ( session == null ) {
            return null;
        }

        String name = null;
        String info = null;
        LocalDateTime start = null;
        LocalDateTime finish = null;
        LocalTime duration = null;

        name = session.getName();
        info = session.getInfo();
        start = session.getStart();
        finish = session.getFinish();
        duration = session.getDuration();

        SessionResponse sessionResponse = new SessionResponse( name, info, start, finish, duration );

        return sessionResponse;
    }

    @Override
    public void updateToEntity(SessionRequest request, Session session) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            session.setName( request.name() );
        }
        if ( request.info() != null ) {
            session.setInfo( request.info() );
        }
    }
}
