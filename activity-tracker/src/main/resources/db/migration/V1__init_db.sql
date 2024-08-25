CREATE TABLE IF NOT EXISTS _user (
  user_id INTEGER NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   username VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   account_locked BOOLEAN NOT NULL,
   enabled BOOLEAN NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk__user PRIMARY KEY (user_id),
   CONSTRAINT uc__user_username UNIQUE (username),
   CONSTRAINT uc__user_email UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS _role (
   role_id INTEGER NOT NULL,
   "name" VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk__role PRIMARY KEY (role_id),
   CONSTRAINT uc__role__name UNIQUE ("name")
);

CREATE TABLE IF NOT EXISTS _user_roles (
  roles_role_id INTEGER NOT NULL,
   users_user_id INTEGER NOT NULL,
   CONSTRAINT _user_roles_pk PRIMARY KEY (role_id, user_id),
   CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_role_id) REFERENCES _role (role_id),
   CONSTRAINT fk_userol_on_user FOREIGN KEY (users_user_id) REFERENCES _user (user_id)
);

CREATE TABLE IF NOT EXISTS _token (
  token_id INTEGER NOT NULL,
   token VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE,
   expires_at TIMESTAMP WITHOUT TIME ZONE,
   validated_at TIMESTAMP WITHOUT TIME ZONE,
   user_id INTEGER NOT NULL,
   CONSTRAINT pk__token PRIMARY KEY (token_id),
   CONSTRAINT FK__TOKEN_ON_USERID FOREIGN KEY (user_id) REFERENCES _user (user_id)
);


CREATE TABLE IF NOT EXISTS _activity (
  activity_id INTEGER NOT NULL,
   "name" VARCHAR(255),
   info VARCHAR(255),
   type VARCHAR(255),
   category VARCHAR(255),
   picture BYTEA,
   rating DOUBLE_PRECISION NOT NULL,
   downloads INTEGER NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   creator_id INTEGER,
   CONSTRAINT pk__activity PRIMARY KEY (activity_id),
   CONSTRAINT uc__activity_name UNIQUE ("name"),
   CONSTRAINT FK__ACTIVITY_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES _user (user_id)
);

CREATE TABLE IF NOT EXISTS _copied_activity (
  copied_activity_id INTEGER NOT NULL,
   "name" VARCHAR(255),
   info VARCHAR(255),
   type VARCHAR(255),
   category VARCHAR(255),
   picture BYTEA,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   creator_id INTEGER,
   original_activity_id INTEGER,
   CONSTRAINT pk__copied_activity PRIMARY KEY (copied_activity_id),
   CONSTRAINT FK__COPIED_ACTIVITY_ON_ORIGINALACTIVITYID FOREIGN KEY (original_activity_id) REFERENCES _activity (activity_id)
)


CREATE TABLE IF NOT EXISTS _achievement (
  achievement_id INTEGER NOT NULL,
   "name" VARCHAR(255),
   info VARCHAR(255),
   type VARCHAR(255),
   picture BYTEA,
   type_checkmark VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   activity_id INTEGER,
   CONSTRAINT pk__achievement PRIMARY KEY (achievement_id),
   CONSTRAINT uc__achievement_created_at UNIQUE (created_at),
   CONSTRAINT uc__achievement_updated_at UNIQUE (updated_at),
   CONSTRAINT FK__ACHIEVEMENT_ON_ACTIVITYID FOREIGN KEY (activity_id) REFERENCES _activity (activity_id)
);


CREATE TABLE IF NOT EXISTS _session (
  session_id INTEGER NOT NULL,
   "name" VARCHAR(255),
   info VARCHAR(255),
   notes TEXT[],
   duration time WITHOUT TIME ZONE,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   activity_id INTEGER,
   CONSTRAINT pk__session PRIMARY KEY (session_id),
   CONSTRAINT FK__SESSION_ON_ACTIVITYID FOREIGN KEY (activity_id) REFERENCES _activity (activity_id)
);


CREATE TABLE IF NOT EXISTS _comment (
  comment_id INTEGER NOT NULL,
   info VARCHAR(255),
   likes INTEGER NOT NULL,
   created_by INTEGER NOT NULL,
   posted_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   activity_id INTEGER,
   CONSTRAINT pk__comment PRIMARY KEY (comment_id),
   CONSTRAINT FK__COMMENT_ON_ACTIVITYID FOREIGN KEY (activity_id) REFERENCES _activity (activity_id)
);
select a1_0.creator,a1_0.activity_id,a1_0.category,a1_0.created_at,a1_0.creator,a1_0.creator_id,a1_0.downloads,a1_0.info,a1_0.is_original,a1_0.is_private,a1_0.name,
oa1_0.activity_id,oa1_0.category,oa1_0.created_at,oa1_0.creator,oa1_0.creator_id,oa1_0.downloads,oa1_0.info,oa1_0.is_original,oa1_0.is_private,oa1_0.name,oa1_0.original_activity_activity_id,oa1_0.picture,p1_0.profile_id,
u1_0.user_id,u1_0.account_locked,u1_0.created_at,u1_0.email,u1_0.enabled,u1_0.first_name,u1_0.last_name,u1_0.password,
p2_0.profile_id,p2_0.user_user_id,p2_0.username,u1_0.updated_at,u1_0.username,p1_0.username,
oa1_0.rating,oa1_0.type,oa1_0.updated_at,a1_0.picture,
p3_0.profile_id,
u3_0.user_id,u3_0.account_locked,u3_0.created_at,u3_0.email,u3_0.enabled,u3_0.first_name,u3_0.last_name,u3_0.password,
p4_0.profile_id,p4_0.user_user_id,p4_0.username,
u3_0.updated_at,u3_0.username,
p3_0.username,
a1_0.rating,a1_0.type,a1_0.updated_at
from _activity a1_0
left join _activity oa1_0 on oa1_0.activity_id=a1_0.original_activity_activity_id
left join _profile p1_0 on p1_0.profile_id=oa1_0.user_id
left join _user u1_0 on u1_0.user_id=p1_0.user_user_id
left join _profile p2_0 on p2_0.profile_id=u1_0.profile_profile_id
left join _profile p3_0 on p3_0.profile_id=a1_0.user_id
left join _user u3_0 on u3_0.user_id=p3_0.user_user_id
left join _profile p4_0 on p4_0.profile_id=u3_0.profile_profile_id
where a1_0.creator=?