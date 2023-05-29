FROM postgres

ENV POSTGRES_USER psql_user
ENV POSTGRES_PASSWORD psql_password
ENV POSTGRES_DB postgres

COPY initdb.d /docker-entrypoint-initdb.d
USER root

EXPOSE 5432
CMD ["postgres"]
