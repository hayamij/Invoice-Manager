==========================================
ae có note gì thì ghi vào đây nhé, với phần câu hỏi thì để (?) đầu câu, t sẽ giải đáp dần dần, ví dụ:
(?) cái phần bị bôi đen từ dòng 80->100 ở persinstence.InvoiceDAO là gì vậy?
-> nó là để debug test kết nối và test dữ liệu
(?) cái query.sql để làm gì?
-> đối với người dùng sql server ext. trên vscode, nó để chạy test database mà không cần mở SSMS

còn note chưa hoàn thành thì để dấu (-), cái nào hoàn thành rồi thì (+), ví dụ:
(-) thiết kế giao diện fxml
(-) chức năng thêm
(+) dữ liệu nghiệp vụ quan trọng

==========================================
(?) pom.xml để làm gì vậy?
-> file cấu hình của maven, nó chứa thông tin project với cách build project, nên là ae setup môi trường nhớ dùng build tool maven nhé

(+) các entity cần thiết
(+) kết nối cơ sở dữ liệu
(-) giao diện fxml (InvoiceUI, giao diện chính là danh sách hóa đơn)
(-) chức năng thêm (addInvoice)
(-) chức năng sửa (editInvoice)
(-) chức năng xóa (deleteInvoice)
(-) chức năng tìm kiếm (sẽ tích hợp searchbar vào giao diện danh sách)
(-) ...

(?) ae muốn giao diện danh sách có sẵn các ô nhập thông tin ở bên trên luôn không? hay phải bấm nút thêm sẽ pop-up form khác để nhập
->
