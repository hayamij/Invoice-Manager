(!)**JavaFX TableView PropertyValueFactory cần getter trong InvoiceViewItem :**
- Nếu muốn hiển thị dữ liệu lên TableView, các trường trong item (ví dụ: InvoiceViewItem) nên có getter public theo chuẩn JavaBeans (ví dụ: getId(), getCustomer(), ...).
- Nếu không dùng getter, các trường phải là public và tuyệt đối không có bất kỳ getter nào cho thuộc tính đó.
- Nếu có getter (dù là private hoặc sai kiểu), JavaFX sẽ không truy cập trực tiếp trường và sẽ báo lỗi "Cannot read from unreadable property ...".