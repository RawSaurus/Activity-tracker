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
