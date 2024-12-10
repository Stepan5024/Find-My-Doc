-- Check if the database exists before creating it
DO $$ 
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'aidoc_auth_db') THEN
      CREATE DATABASE aidoc_auth_db;
   END IF;
END
$$;

DO $$ 
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'aidoc_db') THEN
      CREATE DATABASE aidoc_db;
   END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE aidoc_auth_db TO aidoc_user;
GRANT ALL PRIVILEGES ON DATABASE aidoc_db TO aidoc_user;

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS public;

-- Create roles table if not exists
CREATE TABLE IF NOT EXISTS roles (
   role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   role_name VARCHAR(50) NOT NULL UNIQUE,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create users table if not exists
CREATE TABLE IF NOT EXISTS users (
   user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   email VARCHAR(255) NOT NULL UNIQUE,
   password_hash VARCHAR(255) NOT NULL,
   first_name VARCHAR(100),
   last_name VARCHAR(100),
   middle_name VARCHAR(100),
   phone_number VARCHAR(20),
   status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create user_roles table if not exists
CREATE TABLE IF NOT EXISTS user_roles (
   user_id UUID REFERENCES users(user_id) ON DELETE CASCADE,
   role_id UUID REFERENCES roles(role_id) ON DELETE CASCADE,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (user_id, role_id)
);

-- Create trigger function if not exists
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

-- Create triggers if not exists
DO $$ 
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'update_users_updated_at') THEN
      CREATE TRIGGER update_users_updated_at
      BEFORE UPDATE ON users
      FOR EACH ROW
      EXECUTE FUNCTION update_updated_at_column();
   END IF;
END
$$;

DO $$ 
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'update_roles_updated_at') THEN
      CREATE TRIGGER update_roles_updated_at
      BEFORE UPDATE ON roles
      FOR EACH ROW
      EXECUTE FUNCTION update_updated_at_column();
   END IF;
END
$$;