package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T09:39:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class AchievementMapperImpl implements AchievementMapper {

    @Override
    public AchievementRequest toRequest(Achievement achievement) {
        if ( achievement == null ) {
            return null;
        }

        String name = null;
        String info = null;
        Type type = null;

        name = achievement.getName();
        info = achievement.getInfo();
        type = achievement.getType();

        AchievementRequest achievementRequest = new AchievementRequest( name, info, type );

        return achievementRequest;
    }

    @Override
    public AchievementResponse toResponse(Achievement achievement) {
        if ( achievement == null ) {
            return null;
        }

        String name = null;
        String info = null;
        Type type = null;

        name = achievement.getName();
        info = achievement.getInfo();
        type = achievement.getType();

        AchievementResponse achievementResponse = new AchievementResponse( name, info, type );

        return achievementResponse;
    }

    @Override
    public Achievement toEntity(AchievementRequest request) {
        if ( request == null ) {
            return null;
        }

        Achievement.AchievementBuilder achievement = Achievement.builder();

        achievement.name( request.name() );
        achievement.info( request.info() );
        achievement.type( request.type() );

        return achievement.build();
    }

    @Override
    public Achievement toEntity(AchievementResponse response) {
        if ( response == null ) {
            return null;
        }

        Achievement.AchievementBuilder achievement = Achievement.builder();

        achievement.name( response.name() );
        achievement.info( response.info() );
        achievement.type( response.type() );

        return achievement.build();
    }

    @Override
    public void updateToEntity(AchievementRequest request, Achievement achievement) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            achievement.setName( request.name() );
        }
        if ( request.info() != null ) {
            achievement.setInfo( request.info() );
        }
        if ( request.type() != null ) {
            achievement.setType( request.type() );
        }
    }
}
