FROM minio/minio

# default credentials are minioadmin:minioadmin
EXPOSE 9000 9001

CMD ["minio", "server", "/data", "--console-address", ":9001"]