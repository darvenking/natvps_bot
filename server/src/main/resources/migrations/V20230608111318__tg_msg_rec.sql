DROP TABLE IF EXISTS "public"."site_type";
CREATE TABLE "public"."site_type"
(
    "id"        int8         NOT NULL,
    "name"      varchar(100) NOT NULL,
    "status"    int2 DEFAULT 0,
    "create_at" timestamp(6),
    CONSTRAINT "site_type_pkey" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."web_site";
CREATE TABLE "public"."web_site"
(
    "id"        int8         NOT NULL,
    "name"      varchar(100) NOT NULL,
    "url"       varchar(100) NOT NULL,
    "type"      int2,
    "status"    int2 DEFAULT 0,
    "create_at" timestamp(6),
    CONSTRAINT "web_site_pkey" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."message_record";
CREATE TABLE "public"."message_record"
(
    "id"                int8         NOT NULL,
    "tg_msg_id"         int8         NOT NULL,
    "tg_user_id"        int8         NOT NULL,
    "tg_user_name"      varchar(20)  NOT NULL,
    "tg_user_nick_name" varchar(100) NOT NULL,
    "content"           varchar      NOT NULL,
    "create_at"         timestamp(6),
    CONSTRAINT "message_record_pkey" PRIMARY KEY ("id")
);
