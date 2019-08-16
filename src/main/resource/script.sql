CREATE TABLE event (
        uuid IDENTITY PRIMARY KEY,
        id VARCHAR(50),
        state VARCHAR(50),
        timestamp INT,
        type VARCHAR(50),
        host VARCHAR(50)
);

CREATE TABLE eventlog (
        uuid IDENTITY PRIMARY KEY,
        id VARCHAR(50),
        duration INT,
        type VARCHAR(50),
        host VARCHAR(50),
        alert BOOLEAN
);