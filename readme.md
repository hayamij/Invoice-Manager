
# Invoice Manager

Ứng dụng JavaFX quản lý hóa đơn thuê phòng khách sạn. Hỗ trợ hóa đơn theo giờ và theo ngày, lưu dữ liệu trên SQL Server và cung cấp các thao tác CRUD, tìm kiếm, thống kê.

## Tính năng chính

- Thêm, sửa, xóa hóa đơn
- Tìm kiếm hóa đơn theo từ khóa
- Hiển thị danh sách hóa đơn
- Thống kê số lượng theo loại thuê phòng
- Tính trung bình thành tiền theo tháng

## Công nghệ

- Java 11
- JavaFX 13 (controls, fxml)
- Maven
- Microsoft SQL Server (JDBC)

## Yêu cầu môi trường

- JDK 11
- Maven 3.x
- Microsoft SQL Server (hoặc SQL Server Express)

## Cài đặt cơ sở dữ liệu

1. Tạo database tên `invoice` trên SQL Server.
2. Chạy script tạo bảng và dữ liệu mẫu tại thư mục `database/`.
3. Cập nhật thông tin kết nối trong lớp `databaseKey` cho phù hợp với máy của bạn.

## Chạy ứng dụng

```bash
mvn clean javafx:run
```

Maven sẽ chạy ứng dụng với `mainClass` là `presentation.App`.

## Cấu trúc thư mục

```
src/main/java
	business/      # nghiệp vụ và xử lý thống kê
	persistence/   # truy cập dữ liệu SQL Server
	presentation/  # JavaFX UI, controller, view model
src/main/resources/presentation
	*.fxml         # các giao diện JavaFX
database/
	database(dont touch).sql  # tạo bảng + dữ liệu mẫu
	query.sql                # truy vấn mẫu
```

## Ghi chú

- File cấu hình DB đang được hard-code trong lớp `databaseKey`. Nên thay bằng biến môi trường hoặc file cấu hình nếu triển khai thực tế.
- Bộ test thủ công được lưu trong thư mục `testcase/`.

