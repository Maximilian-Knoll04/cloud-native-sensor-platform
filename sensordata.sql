CREATE TABLE sensordata (
                            id BIGSERIAL PRIMARY KEY,
                            user_id BIGINT NOT NULL,
                            timestamp TIMESTAMPTZ NOT NULL,
                            temperature DOUBLE PRECISION NOT NULL,

                            CONSTRAINT uq_sensordata_user_time UNIQUE (user_id, timestamp)
);
