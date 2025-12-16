# 🏪 Swalayan - Aplikasi Manajemen Toko

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-red.svg)](https://www.microsoft.com/sql-server)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Aplikasi manajemen toko swalayan modern yang dibangun dengan **Spring Boot 4.0.0**, **Thymeleaf**, dan **SQL Server**. Aplikasi ini dirancang untuk memudahkan pengelolaan inventory, transaksi, dan data pelanggan dengan antarmuka yang user-friendly. 

## 🌟 Demo

**Live Demo**: `swalayan.alkara.my.id`

### 🔐 Kredensial Demo
```
Username: [lengkapnya lihat di data.sql]
Password: pass123 (untuk semua user)
```

> ⚠️ **Catatan**: Ini adalah environment demo.  Data dapat di-reset sewaktu-waktu.

---

## ✨ Fitur Utama

- ✅ **Manajemen Produk** - CRUD produk dengan kategori dan stok
- ✅ **Transaksi Penjualan** - Point of Sale (POS) system
- ✅ **Manajemen User** - Role-based access control dengan Spring Security
- ✅ **Dashboard Analytics** - Laporan penjualan dan statistik
- ✅ **Responsive Design** - Tampilan optimal di desktop dan mobile
- ✅ **Real-time Updates** - Live data dengan Spring Boot DevTools

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 4.0.0 | Backend Framework |
| **Spring Security** | 6.4+ | Authentication & Authorization |
| **Thymeleaf** | 3.1+ | Template Engine |
| **SQL Server** | 2022 | Database |
| **Lombok** | Latest | Boilerplate Reduction |
| **Maven** | 3.6+ | Build Tool |
| **Docker** | Latest | Containerization |

---

## 📋 Prerequisites

Sebelum memulai, pastikan Anda telah menginstall: 

- ☑️ **Java 17** atau lebih tinggi ([Download](https://adoptium.net/))
- ☑️ **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- ☑️ **SQL Server 2019+** atau Docker ([Download](https://www.microsoft.com/sql-server))
- ☑️ **Git** ([Download](https://git-scm.com/downloads))

### Optional (Recommended):
- ☑️ **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop))
- ☑️ **IntelliJ IDEA** atau **VS Code** dengan Java extension

---

## 🚀 Quick Start

### 1️⃣ Clone Repository

```bash
git clone https://github.com/bagas39/Swalayan.git
cd Swalayan
```

### 2️⃣ Setup Environment Variables

```bash
# Copy template environment file
cp env.example .env

# Edit .env dengan text editor favorit Anda
nano .env  # atau:  vim .env / code .env
```

**Isi `.env` dengan konfigurasi Anda:**
```env
DB_USER=sa
DB_PASS=YourStrong@Password123

# Untuk development lokal
DB_URL=jdbc: sqlserver://localhost;databaseName=Swalayan;encrypt=true;trustServerCertificate=true;

# Untuk Docker
# DB_URL=jdbc:sqlserver://sqlserver:1433;databaseName=Swalayan;encrypt=false
```

### 3️⃣ Setup Database

#### Option A:  Menggunakan SQL Server Lokal

```sql
-- Buat database baru
CREATE DATABASE Swalayan;
GO

-- Jalankan migration scripts (jika ada di folder sql/)
USE Swalayan;
GO
-- [Import your SQL scripts here]
```

#### Option B: Menggunakan Docker

```bash
docker-compose up -d sqlserver
```

### 4️⃣ Build & Run Aplikasi

#### Menggunakan Maven Wrapper (Recommended):

```bash
# Build project
./mvnw clean install

# Run aplikasi
./mvnw spring-boot:run
```

#### Atau menggunakan Maven biasa:

```bash
mvn clean install
mvn spring-boot:run
```

### 5️⃣ Akses Aplikasi

Buka browser dan akses:
```
http://localhost:5000
```

**Default Login:**
- Username: `admin` / `user` / [sesuai data seed]
- Password: `pass123`

---

## 🐳 Docker Deployment

### Deploy dengan Docker Compose (Full Stack)

```bash
# 1. Build aplikasi terlebih dahulu
./mvnw clean package -DskipTests

# 2. Build Docker image
docker build -t springboot-app .

# 3.  Jalankan semua services (App + SQL Server)
docker-compose up -d

# 4. Check logs
docker-compose logs -f app

# 5. Stop services
docker-compose down
```

### Environment Variables untuk Docker

Edit `docker-compose.yml` dan sesuaikan:

```yaml
environment:
  - DB_USER=sa
  - DB_PASS=YourStrong@Password123
  - DB_URL=jdbc:sqlserver://sqlserver:1433;databaseName=Swalayan;encrypt=false
```

---

## 📦 Production Deployment

### Persiapan untuk Production

1. **Update `application.properties` untuk production:**

```properties
# Disable dev tools
spring.devtools.restart.enabled=false

# Production logging
logging.level.root=WARN
logging.level.com.swalayan=INFO

# Security settings
server.error.include-message=never
server.error.include-stacktrace=never
```

2. **Build production-ready JAR:**

```bash
./mvnw clean package -DskipTests -Pprod
```

3. **Set environment variables:**

```bash
export DB_USER=your_prod_user
export DB_PASS=your_prod_password
export DB_URL=jdbc: sqlserver://your-server:1433;databaseName=Swalayan;encrypt=true
```

4. **Run dengan produksi profile:**

```bash
java -jar target/projek-swalayan-0.0.1-SNAPSHOT. jar --spring.profiles.active=prod
```

### Deploy ke VPS/Server

```bash
# 1. Copy JAR ke server
scp target/*. jar user@your-server:/opt/swalayan/

# 2. SSH ke server
ssh user@your-server

# 3. Setup sebagai systemd service
sudo nano /etc/systemd/system/swalayan.service
```

**File service:**
```ini
[Unit]
Description=Swalayan Spring Boot Application
After=syslog.target network.target

[Service]
User=appuser
WorkingDirectory=/opt/swalayan
ExecStart=/usr/bin/java -jar /opt/swalayan/projek-swalayan-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

Environment="DB_USER=sa"
Environment="DB_PASS=YourPassword"
Environment="DB_URL=jdbc:sqlserver://localhost: 1433;databaseName=Swalayan"

[Install]
WantedBy=multi-user.target
```

**Enable dan start service:**
```bash
sudo systemctl daemon-reload
sudo systemctl enable swalayan
sudo systemctl start swalayan
sudo systemctl status swalayan
```

### Deploy dengan Nginx sebagai Reverse Proxy

```nginx
server {
    listen 80;
    server_name yourdomain.com;

    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

---

## 🗂️ Project Structure

```
Swalayan/
├── src/
│   ├── main/
│   │   ├── java/com/swalayan/
│   │   │   ├── config/          # Konfigurasi Spring
│   │   │   ├── controller/      # REST & Web Controllers
│   │   │   ├── model/           # Entity & DTOs
│   │   │   ├── repository/      # Data Access Layer
│   │   │   ├── service/         # Business Logic
│   │   │   └── security/        # Security Configuration
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf Templates
│   │       ├── static/          # CSS, JS, Images
│   │       └── application.properties
│   └── test/                    # Unit & Integration Tests
├── docker-compose.yml           # Docker orchestration
├── Dockerfile                   # Container definition
├── pom.xml                      # Maven dependencies
├── . env                         # Environment variables (gitignored)
└── env.example                  # Environment template
```

---

## 🔧 Configuration

### Application Properties

File:  `src/main/resources/application.properties`

```properties
# Server Configuration
server.port=5000
spring.application.name=projek-swalayan

# Database Configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Jackson JSON Configuration
spring.jackson.default-property-inclusion=non_null

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Logging
logging.level.root=INFO
logging.level.com.swalayan=DEBUG
```

---

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run dengan coverage report
./mvnw clean test jacoco:report

# Run specific test class
./mvnw test -Dtest=UserServiceTest

# Skip tests saat build
./mvnw clean package -DskipTests
```

---

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(100),
    role NVARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);
```

### Products Table
```sql
CREATE TABLE products (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    category NVARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE()
);
```

> 📝 **Catatan**:  Schema lengkap dapat dilihat di folder `sql/` atau dokumentasi terpisah.

---

## 🤝 Contributing

Kontribusi sangat diterima! Berikut cara berkontribusi:

1. Fork repository ini
2. Buat branch fitur baru (`git checkout -b feature/AmazingFeature`)
3. Commit perubahan Anda (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

### Development Guidelines

- Ikuti [Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)
- Tulis unit tests untuk fitur baru
- Update dokumentasi jika diperlukan
- Pastikan build berhasil sebelum submit PR

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Port 5000 sudah digunakan

**Error:**
```
Web server failed to start. Port 5000 was already in use.
```

**Solusi:**
```bash
# Ubah port di application.properties
server.port=8080

# Atau set via environment variable
export SERVER_PORT=8080
```

#### 2. Koneksi database gagal

**Error:**
```
Cannot create PoolableConnectionFactory
```

**Solusi:**
- Pastikan SQL Server sudah running
- Cek credentials di `.env` file
- Verify connection string: 
  ```bash
  # Test koneksi dengan sqlcmd
  sqlcmd -S localhost -U sa -P YourPassword
  ```

#### 3. Build gagal karena dependency

**Error:**
```
Failed to execute goal on project:  Could not resolve dependencies
```

**Solusi:**
```bash
# Clear Maven cache dan rebuild
./mvnw clean
rm -rf ~/. m2/repository
./mvnw clean install -U
```

#### 4. Docker container tidak start

**Solusi:**
```bash
# Check logs
docker-compose logs app

# Restart containers
docker-compose restart

# Rebuild images
docker-compose up --build
```

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Author

**bagas39**
- GitHub: [@bagas39](https://github.com/bagas39)
- Repository: [Swalayan](https://github.com/bagas39/Swalayan)

---

## 🙏 Acknowledgments

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [SQL Server Documentation](https://docs.microsoft.com/sql/)
- Terima kasih kepada semua kontributor! 

---

## 📮 Support

Jika Anda menemukan bug atau memiliki saran fitur:

- 🐛 [Buat Issue](https://github.com/bagas39/Swalayan/issues)
- 💡 [Diskusi di Discussions](https://github.com/bagas39/Swalayan/discussions)
- 📧 Atau hubungi via GitHub

---

## 🔄 Changelog

### Version 0.0.1-SNAPSHOT (Current)
- ✨ Initial release
- ✅ Basic CRUD operations
- ✅ User authentication & authorization
- ✅ Docker support
- ✅ Thymeleaf integration

---

## 🗺️ Roadmap

- [ ] REST API documentation (Swagger/OpenAPI)
- [ ] Unit test coverage > 80%
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Mobile app integration
- [ ] Multi-language support (i18n)
- [ ] Advanced reporting features
- [ ] Export to PDF/Excel

---

<div align="center">

**⭐ Jika project ini membantu, jangan lupa untuk memberikan star! **

Made with ❤️ using Spring Boot 4.0.0

</div>
