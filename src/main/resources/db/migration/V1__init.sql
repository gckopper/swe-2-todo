CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    external_calendar_service_token VARCHAR(255)
);

CREATE TYPE task_status AS ENUM ('todo', 'doing', 'done');

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status task_status NOT NULL,
    owner_user_id UUID NOT NULL,
    assigned_user_id UUID NULL,
    expected_completion_date TIMESTAMP,
    external_calendar_event_id VARCHAR(255),
    CONSTRAINT fk_tasks_owner_user FOREIGN KEY (owner_user_id) REFERENCES users(id),
    CONSTRAINT fk_tasks_assigned_user FOREIGN KEY (assigned_user_id) REFERENCES users(id)
);
