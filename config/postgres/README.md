# Database Initialization Scripts

This directory contains PostgreSQL initialization scripts that are executed when the database container is first created.

## Script Order

1. `01_init.sql` - Creates necessary databases
2. `02_create_auth_schema.sql` - Creates schema and tables for auth-server
3. `03_create_gateway_schema.sql` - Creates schema and tables for api-gateway
4. `02_data.sql` - Initializes basic data (roles, admin user, etc.)

## Usage

These scripts are automatically executed in alphabetical order when the PostgreSQL container starts for the first time.