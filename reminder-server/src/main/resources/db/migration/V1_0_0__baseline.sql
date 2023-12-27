--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;






--
-- Name: reminder; Type: TABLE; Schema: public; Owner: reminder
--

CREATE TABLE public.reminder (
    id bigint NOT NULL,
    date date,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    message character varying(1024) NOT NULL,
    one_off boolean NOT NULL,
    "time" time without time zone NOT NULL,
    token character varying(255) NOT NULL
);


--
-- Name: reminder_seq; Type: SEQUENCE; Schema: public; Owner: reminder
--

CREATE SEQUENCE public.reminder_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: reminder_seq; Type: SEQUENCE SET; Schema: public; Owner: reminder
--

SELECT pg_catalog.setval('public.reminder_seq', 1, false);


--
-- Name: reminder reminder_pkey; Type: CONSTRAINT; Schema: public; Owner: reminder
--

ALTER TABLE ONLY public.reminder
    ADD CONSTRAINT reminder_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

