-- Add role column to users if not exists
-- Seed default admin user for testing

INSERT INTO users (
    username,
    email,
    password,
    role,
    is_active
) VALUES (
    'admin',
    'admin@company.com',
    -- password is 'admin123' (hashed)
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMIN',
    true
) ON CONFLICT (username) DO NOTHING;

INSERT INTO users (
    username,
    email,
    password,
    role,
    is_active
) VALUES (
    'manager',
    'manager@company.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'MANAGER',
    true
) ON CONFLICT (username) DO NOTHING;

INSERT INTO users (
    username,
    email,
    password,
    role,
    is_active
) VALUES (
    'viewer',
    'viewer@company.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'VIEWER',
    true
) ON CONFLICT (username) DO NOTHING;