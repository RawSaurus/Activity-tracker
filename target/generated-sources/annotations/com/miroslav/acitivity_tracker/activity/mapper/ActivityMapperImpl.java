package com.miroslav.acitivity_tracker.activity.mapper;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.session.model.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-29T16:51:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public ActivityRequest toRequest(Activity activity) {
        if ( activity == null ) {
            return null;
        }

        String name = null;
        String info = null;
        String type = null;
        Category category = null;

        name = activity.getName();
        info = activity.getInfo();
        type = activity.getType();
        category = activity.getCategory();

        boolean isPrivate = false;

        ActivityRequest activityRequest = new ActivityRequest( name, info, type, category, isPrivate );

        return activityRequest;
    }

    @Override
    public ActivityResponse toResponse(Activity activity) {
        if ( activity == null ) {
            return null;
        }

        String name = null;
        String info = null;
        String type = null;
        Category category = null;
        double rating = 0.0d;
        int downloads = 0;

        name = activity.getName();
        info = activity.getInfo();
        type = activity.getType();
        category = activity.getCategory();
        rating = activity.getRating();
        downloads = activity.getDownloads();

        ActivityResponse activityResponse = new ActivityResponse( name, info, type, category, rating, downloads );

        return activityResponse;
    }

    @Override
    public Activity toEntity(ActivityRequest request) {
        if ( request == null ) {
            return null;
        }

        Activity.ActivityBuilder activity = Activity.builder();

        activity.name( request.name() );
        activity.info( request.info() );
        activity.type( request.type() );
        activity.category( request.category() );
        activity.isPrivate( request.isPrivate() );

        return activity.build();
    }

    @Override
    public Activity toEntity(ActivityResponse response) {
        if ( response == null ) {
            return null;
        }

        Activity.ActivityBuilder activity = Activity.builder();

        activity.name( response.name() );
        activity.info( response.info() );
        activity.type( response.type() );
        activity.category( response.category() );
        activity.rating( response.rating() );
        activity.downloads( response.downloads() );

        return activity.build();
    }

    @Override
    public void updateToEntity(ActivityRequest request, Activity activity) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            activity.setName( request.name() );
        }
        if ( request.info() != null ) {
            activity.setInfo( request.info() );
        }
        if ( request.type() != null ) {
            activity.setType( request.type() );
        }
        if ( request.category() != null ) {
            activity.setCategory( request.category() );
        }
    }

    @Override
    public void updateToEntity(Activity original, Activity copy) {
        if ( original == null ) {
            return;
        }

        copy.setActivityId( original.getActivityId() );
        copy.setName( original.getName() );
        copy.setInfo( original.getInfo() );
        copy.setType( original.getType() );
        copy.setCategory( original.getCategory() );
        byte[] picture = original.getPicture();
        if ( picture != null ) {
            copy.setPicture( Arrays.copyOf( picture, picture.length ) );
        }
        else {
            copy.setPicture( null );
        }
        copy.setRating( original.getRating() );
        copy.setDownloads( original.getDownloads() );
        copy.setOriginal( original.isOriginal() );
        copy.setPrivate( original.isPrivate() );
        copy.setCreatedAt( original.getCreatedAt() );
        copy.setUpdatedAt( original.getUpdatedAt() );
        copy.setCreatorId( original.getCreatorId() );
        copy.setCreator( original.getCreator() );
        copy.setOriginalActivity( original.getOriginalActivity() );
        copy.setProfile( original.getProfile() );
        if ( copy.getAchievements() != null ) {
            List<Achievement> list = original.getAchievements();
            if ( list != null ) {
                copy.getAchievements().clear();
                copy.getAchievements().addAll( list );
            }
            else {
                copy.setAchievements( null );
            }
        }
        else {
            List<Achievement> list = original.getAchievements();
            if ( list != null ) {
                copy.setAchievements( new ArrayList<Achievement>( list ) );
            }
        }
        if ( copy.getSessions() != null ) {
            List<Session> list1 = original.getSessions();
            if ( list1 != null ) {
                copy.getSessions().clear();
                copy.getSessions().addAll( list1 );
            }
            else {
                copy.setSessions( null );
            }
        }
        else {
            List<Session> list1 = original.getSessions();
            if ( list1 != null ) {
                copy.setSessions( new ArrayList<Session>( list1 ) );
            }
        }
        if ( copy.getComments() != null ) {
            List<Comment> list2 = original.getComments();
            if ( list2 != null ) {
                copy.getComments().clear();
                copy.getComments().addAll( list2 );
            }
            else {
                copy.setComments( null );
            }
        }
        else {
            List<Comment> list2 = original.getComments();
            if ( list2 != null ) {
                copy.setComments( new ArrayList<Comment>( list2 ) );
            }
        }
    }
}
