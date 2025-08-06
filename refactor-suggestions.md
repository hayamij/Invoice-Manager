# REFACTOR SUGGESTIONS - SEPARATION OF CONCERNS

## 🔴 LOẠI BỎ/REFACTOR CÁC CLASS KHÔNG CẦN THIẾT

### 1. Loại bỏ Observer Pattern hiện tại
**Lý do:** JavaFX đã có built-in Observable properties, Publisher/Subscriber hiện tại không được sử dụng

**Action:** 
- Xóa `Publisher.java`
- Xóa `Subscriber.java` 
- Refactor `InvoiceListModel.java` thành proper ViewModel

### 2. Loại bỏ SecondaryController
**Lý do:** Không có business value, chỉ là demo screen

**Action:**
- Xóa `SecondaryController.java`
- Xóa `secondary.fxml`
- Xóa navigation logic trong `PrimaryController`

## 🟡 CẢI THIỆN SEPARATION OF CONCERNS

### 3. Business Layer - Service Pattern (B-C-E Model)

#### **InvoiceService** (Business Service)
```java
// Tách logic business từ addInvoice và InvoiceListControl
public class InvoiceService {
    private InvoiceDAOGateway daoGateway;
    
    // Create, Update, Delete operations
    public boolean createInvoice(InvoiceRequest request)
    public boolean updateInvoice(String id, InvoiceRequest request)
    public boolean deleteInvoice(String id)
    public List<Invoice> searchInvoices(SearchCriteria criteria)
}
```

#### **ValidationService** (Control)
```java
// Validation logic tách biệt
public class ValidationService {
    public static ValidationResult validateInvoiceRequest(InvoiceRequest request)
    public static boolean isValidCustomerName(String name)
    public static boolean isValidRoomId(String roomId)
    public static boolean isValidPricing(double unitPrice, int quantity)
}
```

#### **ReportService** (Entity/Service)
```java
// Statistics và reporting logic
public class ReportService {
    public RevenueReport generateRevenueReport(DateRange range)
    public List<TopCustomer> getTopCustomers(int limit)
    public InvoiceSummary getInvoiceSummary()
}
```

### 4. Presentation Layer - MVC Pattern

#### **InvoiceFormModel** (Model)
```java
// Form state management với JavaFX Properties
public class InvoiceFormModel {
    private StringProperty customer = new SimpleStringProperty();
    private StringProperty roomId = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    // ... other properties with validation
    
    public ValidationResult validate()
    public void clear()
    public InvoiceRequest toRequest()
}
```

#### **InvoiceViewController** (View Controller)
```java
// Chỉ handle UI events, delegate business logic
public class InvoiceViewController {
    private InvoiceFormModel formModel;
    private InvoiceService invoiceService;
    
    // Form handling
    private void handleAddInvoice()
    private void handleUpdateInvoice()
    private void handleDeleteInvoice()
    private void updateFormDisplay()
}
```

#### **InvoiceTableController** (Controller)
```java
// Chuyên quản lý table display
public class InvoiceTableController {
    private TableView<InvoiceListItem> tableView;
    private InvoiceService invoiceService;
    
    public void refreshTable()
    public void setupColumns()
    public void handleRowSelection()
}
```

## 🟢 CẤU TRÚC MỚI ĐỀ XUẤT

### Business Layer (B-C-E)
```
business/
├── services/          # Business Services (B)
│   ├── InvoiceService.java
│   ├── ReportService.java
│   └── ValidationService.java
├── controllers/       # Business Controllers (C)
│   ├── InvoiceBusinessController.java
│   └── ReportController.java
└── entities/          # Business Entities (E)
    ├── Invoice.java (abstract)
    ├── HourlyInvoice.java
    ├── DailyInvoice.java
    └── InvoiceFactory.java
```

### Presentation Layer (M-V-C)
```
presentation/
├── models/           # View Models (M)
│   ├── InvoiceFormModel.java
│   ├── InvoiceTableModel.java
│   └── StatisticsModel.java
├── views/            # FXML Files (V)
│   ├── primary.fxml
│   └── components/
│       ├── invoice-form.fxml
│       └── invoice-table.fxml
└── controllers/      # View Controllers (C)
    ├── MainController.java
    ├── InvoiceFormController.java
    └── InvoiceTableController.java
```

## 📋 IMPLEMENTATION PLAN

### Phase 1: Cleanup
1. Remove Publisher/Subscriber/InvoiceListModel
2. Remove SecondaryController and secondary.fxml
3. Update PrimaryController imports

### Phase 2: Business Layer Refactor
1. Create InvoiceService (extract from addInvoice + InvoiceListControl)
2. Create ValidationService
3. Create ReportService (extract statistics from InvoiceListControl)

### Phase 3: Presentation Layer Refactor
1. Create InvoiceFormModel with JavaFX Properties
2. Split PrimaryController into specialized controllers
3. Implement proper MVC separation

### Phase 4: Integration
1. Update DIContainer for new services
2. Wire new controllers together
3. Test all functionality

## 🎯 EXPECTED BENEFITS

1. **Single Responsibility:** Mỗi class chỉ có 1 nhiệm vụ rõ ràng
2. **Better Testability:** Services có thể test riêng biệt
3. **Maintainability:** Dễ sửa đổi và mở rộng
4. **Reusability:** Services có thể tái sử dụng cho các UI khác
5. **Clear Architecture:** Phân tách rõ ràng 3 tầng với proper patterns
