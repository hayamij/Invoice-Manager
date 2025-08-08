-- Create users table for account management (SQL Server compatible)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='users' AND xtype='U')
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

-- Create index for faster username lookup
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_users_username' AND object_id = OBJECT_ID('users'))
CREATE INDEX idx_users_username ON users(username);

-- Insert default admin account
IF NOT EXISTS (SELECT * FROM users WHERE username = 'fuongtuan')
INSERT INTO users (username, password) VALUES ('fuongtuan', 'toilabanhmochi');

-- Optional: Add more demo accounts
IF NOT EXISTS (SELECT * FROM users WHERE username = 'admin')
INSERT INTO users (username, password) VALUES ('admin', 'admin123');

IF NOT EXISTS (SELECT * FROM users WHERE username = 'demo')
INSERT INTO users (username, password) VALUES ('demo', 'demo123');