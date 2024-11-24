
-- Вставка базовых ролей
INSERT INTO roles (role_name) VALUES
                                  ('ROLE_USER'),
                                  ('ROLE_ADMIN'),
                                  ('ROLE_DOCTOR')
    ON CONFLICT (role_name) DO NOTHING;

-- Добавление тестового админа
INSERT INTO users (
    email,
    password_hash,
    first_name,
    last_name,
    status
) VALUES (
             'admin@example.com',
             '$2a$10$xn3LI/AjqicFYZFruSwve.277PhLg2kyMwk.MoMxYpvXknLzsef3u',
             'Admin',
             'Administrator',
             'ACTIVE'
         ) ON CONFLICT (email) DO NOTHING;

-- Назначение роли админа
INSERT INTO user_roles (
    user_id,
    role_id
)
SELECT
    u.user_id,
    r.role_id
FROM users u
         CROSS JOIN roles r
WHERE u.email = 'admin@example.com'
  AND r.role_name = 'ROLE_ADMIN'
    ON CONFLICT DO NOTHING;