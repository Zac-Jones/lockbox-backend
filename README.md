# 🔐 LockBox - Password & 2FA Manager Backend

LockBox is a secure, all-in-one password and two-factor authentication (2FA) manager application. This repository contains the backend REST API built with Spring Boot that provides secure credential storage, 2FA management, and user authentication.

## ✨ Features

- **🔐 Secure Password Management**: Store and manage passwords for various websites and applications
- **🛡️ Two-Factor Authentication**: Manage 2FA secrets and generate time-based one-time passwords (TOTP)
- **👤 User Management**: User registration, authentication, and profile management
- **🏢 Company/Service Organization**: Organize credentials by company or service
- **📊 System Logging**: Comprehensive audit logging for security monitoring
- **🔥 Firebase Integration**: Uses Firebase Authentication and Firestore for secure data storage
- **🔒 JWT Authentication**: Secure token-based authentication

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 21
- **Database**: Google Cloud Firestore
- **Authentication**: Firebase Authentication
- **Security**: Spring Security
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok for code generation
  - Firebase Admin SDK
  - Google Cloud Firestore
  - Commons Codec for encryption utilities
  - Dotenv for environment variable management

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Firebase project with Firestore enabled
- Firebase service account credentials

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd lockbox-backend/lockbox
   ```

2. **Set up environment variables**
   Create a `.env` file in the root directory:
   ```env
   FIREBASE_CREDENTIALS=<base64-encoded-firebase-credentials-json>
   ```

3. **Install dependencies**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Using Maven Wrapper (Windows)

```cmd
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

## 📡 API Endpoints

### Authentication
- `POST /api/register` - Register a new user
- `POST /api/login` - User login

### Company Management
- `POST /api/add-company` - Add a new company/service
- `GET /api/companies` - Get user's companies
- `DELETE /api/companies/{companyId}` - Delete a company

### Password Management
- `POST /api/{companyId}/add-credential` - Add password for a company
- `GET /api/{companyId}/credentials` - Get passwords for a company
- `POST /api/update-credential` - Update a password
- `DELETE /api/credentials/{credentialId}` - Delete a password

### 2FA Management
- `POST /api/{companyId}/add-2fa` - Add 2FA for a company
- `GET /api/{companyId}/2fas` - Get 2FA entries for a company
- `DELETE /api/2fas/{twoFactorAuthId}` - Delete a 2FA entry

## 🔒 Security Features

- **Firebase Authentication**: Secure token-based authentication
- **Authorization Headers**: All endpoints require valid Bearer tokens
- **User Isolation**: Users can only access their own data
- **CORS Configuration**: Configured for frontend integration
- **Audit Logging**: Comprehensive system logging for security monitoring
- **IP Address Tracking**: Monitor login attempts from different locations

## 🗄️ Data Models

### User
- User ID, email, name
- Known IP addresses for security
- Backup codes for 2FA recovery

### Company
- Company/service information
- User association
- Favorite marking

### Credential
- Username and password storage
- Company association
- Secure storage in Firestore

### TwoFactorAuth
- 2FA secret storage
- Company association
- Name for identification

### SystemLog
- Comprehensive audit logging
- User actions and security events
- Timestamp and IP tracking

## 🔧 Configuration

### Firebase Setup
1. Create a Firebase project
2. Enable Firestore database
3. Generate service account credentials
4. Encode credentials as base64 and set in environment variables

### CORS Configuration
The application is configured to accept requests from `http://localhost:3000` by default. Modify `SecurityConfig.java` to change allowed origins.

## 🧪 Testing

Run tests using Maven:
```bash
./mvnw test
```

## 📝 Development Notes

- Uses Lombok for reducing boilerplate code
- Repository pattern with Firestore integration
- Service layer for business logic separation
- Comprehensive error handling
- Environment-based configuration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request
