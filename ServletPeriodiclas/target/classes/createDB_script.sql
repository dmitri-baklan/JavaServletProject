
CREATE TABLE IF NOT EXISTS public.users_periodicals
(
    user_id bigint NOT NULL,
    periodical_id bigint NOT NULL,
    CONSTRAINT unique_subscription UNIQUE (user_id, periodical_id)
)

    TABLESPACE pg_default;

ALTER TABLE public.users_periodicals
    OWNER to postgres;
-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    u_id bigint NOT NULL,
    u_email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_surname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_is_active boolean NOT NULL,
    u_balance bigint,
    u_subscriptions bigint,
    CONSTRAINT users_pkey PRIMARY KEY (u_id),
    CONSTRAINT email UNIQUE (u_email)
)

    TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

-- Table: public.replenishments

-- DROP TABLE public.replenishments;

CREATE TABLE IF NOT EXISTS public.replenishments
(
    r_id bigint NOT NULL,
    r_sum bigint NOT NULL,
    r_user_id bigint NOT NULL,
    "r_time" timestamp without time zone NOT NULL,
    CONSTRAINT replenishments_pkey PRIMARY KEY (r_id),
    CONSTRAINT owner_id FOREIGN KEY (r_user_id)
        REFERENCES public.users (u_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE public.replenishments
    OWNER to postgres;

-- Table: public.periodicals

-- DROP TABLE public.periodicals;

CREATE TABLE IF NOT EXISTS public.periodicals
(
    p_id bigint NOT NULL,
    p_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    p_subject character varying(255) COLLATE pg_catalog."default" NOT NULL,
    p_price bigint NOT NULL,
    p_subscribers bigint NOT NULL,
    CONSTRAINT periodicals_pkey PRIMARY KEY (p_id),
    CONSTRAINT name UNIQUE (p_name)
)

    TABLESPACE pg_default;

ALTER TABLE public.periodicals
    OWNER to postgres;


