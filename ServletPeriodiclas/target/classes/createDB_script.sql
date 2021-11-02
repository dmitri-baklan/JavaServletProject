
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
    id bigint NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    is_active boolean NOT NULL,
    balance bigint,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email)
)

    TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

-- Table: public.replenishments

-- DROP TABLE public.replenishments;

CREATE TABLE IF NOT EXISTS public.replenishments
(
    id bigint NOT NULL,
    sum bigint NOT NULL,
    user_id bigint NOT NULL,
    "time" timestamp without time zone NOT NULL,
    CONSTRAINT replenishments_pkey PRIMARY KEY (id),
    CONSTRAINT owner_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
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
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    subject character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price bigint NOT NULL,
    subscribers bigint NOT NULL,
    CONSTRAINT periodicals_pkey PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
)

    TABLESPACE pg_default;

ALTER TABLE public.periodicals
    OWNER to postgres;

-- Table: public.accounts

-- DROP TABLE public.accounts;

CREATE TABLE IF NOT EXISTS public.accounts
(
    id bigint NOT NULL,
    balance bigint NOT NULL,
    user_id bigint,
    CONSTRAINT accounts_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.accounts
    OWNER to postgres;

