<!-- Sử dụng file này để cung cấp hướng dẫn tùy chỉnh cho Copilot trong workspace. -->
- [ ] Xác minh rằng file copilot-instructions.md đã được tạo trong thư mục .github.

- [ ] Làm rõ yêu cầu dự án
	<!-- Hỏi loại dự án, ngôn ngữ, framework nếu chưa được chỉ định. Bỏ qua nếu đã có. -->

- [ ] Tạo khung dự án
	<!--
	Đảm bảo đã hoàn thành bước trước.
	Gọi công cụ thiết lập dự án với tham số projectType.
	Chạy lệnh scaffold để tạo file và thư mục dự án.
	Sử dụng '.' làm thư mục làm việc.
	Nếu không có projectType phù hợp, tìm kiếm tài liệu bằng các công cụ có sẵn.
	Nếu không, tạo cấu trúc dự án thủ công bằng các công cụ tạo file.
	-->

- [ ] Tùy chỉnh dự án
	<!--
	Xác minh đã hoàn thành các bước trước và đã đánh dấu hoàn thành.
	Lập kế hoạch chỉnh sửa codebase theo yêu cầu người dùng.
	Áp dụng chỉnh sửa bằng công cụ phù hợp và tham chiếu người dùng cung cấp.
	Bỏ qua bước này với dự án "Hello World".
	-->

- [ ] Cài đặt extension cần thiết
	<!-- Chỉ cài extension được liệt kê bởi get_project_setup_info. Bỏ qua nếu không có và đánh dấu hoàn thành. -->

- [ ] Biên dịch dự án
	<!--
	Xác minh đã hoàn thành các bước trước.
	Cài đặt các dependency còn thiếu.
	Chạy chẩn đoán và xử lý lỗi.
	Kiểm tra file markdown trong thư mục dự án để lấy hướng dẫn liên quan.
	-->

- [ ] Tạo và chạy task
	<!--
	Xác minh đã hoàn thành các bước trước.
	Kiểm tra https://code.visualstudio.com/docs/debugtest/tasks để xác định dự án có cần task không. Nếu có, sử dụng create_and_run_task để tạo và chạy task dựa trên package.json, README.md và cấu trúc dự án.
	Bỏ qua nếu không cần.
	 -->

- [ ] Khởi chạy dự án
	<!--
	Xác minh đã hoàn thành các bước trước.
	Hỏi người dùng về chế độ debug, chỉ khởi chạy nếu xác nhận.
	 -->

- [ ] Đảm bảo tài liệu hoàn chỉnh
	<!--
	Xác minh đã hoàn thành các bước trước.
	Xác minh README.md và copilot-instructions.md trong .github đã tồn tại và chứa thông tin dự án hiện tại.
	Dọn sạch file copilot-instructions.md trong .github bằng cách xóa tất cả các comment HTML.
	 -->

## Hướng dẫn thực hiện
THEO DÕI TIẾN ĐỘ:
- Nếu có công cụ quản lý checklist, sử dụng để theo dõi tiến độ.
- Sau mỗi bước, đánh dấu hoàn thành và thêm tóm tắt.
- Đọc trạng thái checklist trước khi bắt đầu bước mới.

QUY TẮC GIAO TIẾP:
- Tránh giải thích dài dòng hoặc in toàn bộ output lệnh.
- Nếu bỏ qua bước, chỉ nêu ngắn gọn (vd: "Không cần extension").
- Không giải thích cấu trúc dự án nếu không được hỏi.
- Giữ giải thích ngắn gọn, tập trung.

QUY TẮC PHÁT TRIỂN:
- Sử dụng '.' làm thư mục gốc trừ khi người dùng chỉ định khác.
- Không thêm media hoặc link ngoài nếu không được yêu cầu.
- Chỉ dùng placeholder khi cần và phải ghi chú thay thế.
- Chỉ dùng VS Code API cho dự án extension VS Code.
- Khi dự án đã tạo, đã mở sẵn trong VS Code—không đề xuất lệnh mở lại.
- Nếu có quy tắc bổ sung trong project setup info, phải tuân thủ.

QUY TẮC TẠO THƯ MỤC:
- Luôn dùng thư mục hiện tại làm gốc dự án.
- Nếu chạy lệnh terminal, luôn dùng '.' làm thư mục làm việc.
- Không tạo thư mục mới trừ khi người dùng yêu cầu hoặc tạo .vscode cho tasks.json.
- Nếu lệnh scaffold báo tên thư mục không đúng, báo người dùng tạo lại thư mục đúng tên rồi mở lại trong vscode.

QUY TẮC CÀI EXTENSION:
- Chỉ cài extension được chỉ định bởi get_project_setup_info. KHÔNG CÀI extension khác.

QUY TẮC NỘI DUNG DỰ ÁN:
- Nếu chưa có chi tiết dự án, mặc định tạo dự án "Hello World".
- Không thêm link, file, folder hoặc tích hợp không cần thiết.
- Không tạo ảnh, video hoặc media khác nếu không được yêu cầu.
- Nếu cần asset media làm placeholder, phải ghi chú thay thế bằng asset thật sau.
- Đảm bảo các thành phần sinh ra đều phục vụ workflow người dùng.
- Nếu tính năng chưa xác nhận, hỏi lại người dùng trước khi thêm.
- Nếu làm extension VS Code, dùng VS Code API để tìm tài liệu và mẫu liên quan.

QUY TẮC HOÀN THÀNH NHIỆM VỤ:
- Nhiệm vụ hoàn thành khi:
  - Dự án scaffold và biên dịch thành công, không lỗi
  - File copilot-instructions.md trong .github đã tồn tại
  - File README.md đã tồn tại và cập nhật
  - Người dùng nhận hướng dẫn rõ ràng để debug/khởi chạy dự án

Trước khi bắt đầu nhiệm vụ mới, cập nhật tiến độ trong checklist.
- Work through each checklist item systematically.
- Keep communication concise and focused.
- Follow development best practices.
