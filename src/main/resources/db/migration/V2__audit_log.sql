CREATE TABLE IF NOT EXISTS audit_log (
    id           BIGSERIAL PRIMARY KEY,
    entity_type  VARCHAR(100)  NOT NULL,
    entity_id    BIGINT        NOT NULL,
    action       VARCHAR(50)   NOT NULL,
    old_value    TEXT,
    new_value    TEXT,
    performed_by VARCHAR(150),
    performed_at TIMESTAMP     NOT NULL
                 DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_entity
    ON audit_log(entity_type, entity_id);

CREATE INDEX idx_audit_performed
    ON audit_log(performed_at);

CREATE INDEX idx_audit_action
    ON audit_log(action);