# 列表行操作按钮与草稿编辑 Implementation Plan

> **状态：已完成（归档）。** 本计划描述的行操作按钮与草稿编辑已全部落地：销售订单/采购单在草稿态可编辑（`PUT /erp/order/{id}`、`PUT /erp/purchaseOrder/{id}`），列表页按权限显示「详情 / 编辑」，通用档案页合并出统一操作列。下文保留当时的任务分解与验收记录，作为实施过程留档；其中的代码行数、接口数量等数字均为 2026-10-20 基线，不代表当前状态（当前为 43 个控制器 / 260 个方法级接口 / 66 张表）。

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 为销售订单、采购订单与通用档案页统一行操作按钮，并让销售订单/采购单在草稿状态可编辑。

**Architecture:** 后端新增两个 `PUT` 更新接口，复用新建校验并在事务内“锁行→校验状态→更新表头→替换明细”。前端复用现有新建表单实现编辑模式，列表页增加按权限显示的“详情 / 编辑”按钮，档案页合并出统一操作列。

**Tech Stack:** Spring Boot 3 / MyBatis 注解 Mapper / MySQL；Vue 3 + TypeScript + Element Plus + Vue Router；JUnit 5 + Mockito。

---

### Task 1: 销售订单草稿更新（后端）

**Files:**
- Modify: `backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/mapper/ErpOrderMapper.java`
- Modify: `backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/service/ErpOrderService.java`
- Modify: `backend/myfactoryai-admin/src/main/java/net/factoryopen/myfactoryai/web/controller/erp/ErpOrderController.java`
- Test: `backend/myfactoryai-admin/src/test/java/net/factoryopen/myfactoryai/web/controller/erp/ErpDraftUpdateTest.java`

- [ ] **Step 1: 写失败测试**

创建 `backend/myfactoryai-admin/src/test/java/net/factoryopen/myfactoryai/web/controller/erp/ErpDraftUpdateTest.java`：

```java
package net.factoryopen.myfactoryai.web.controller.erp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.OrderDraftRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpOrderMapper;
import net.factoryopen.myfactoryai.erp.service.ErpOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ErpDraftUpdateTest {
    private OrderDraftRequest orderRequest() {
        return new OrderDraftRequest(1L, LocalDate.now().plusDays(3),
                List.of(new OrderDraftRequest.Line(1L, 2L, 3L, 2, new BigDecimal("10.00"))));
    }

    @Test
    void orderUpdateRejectsConfirmedDocument() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 1, "orderNo", "SO2026000007"));

        ServiceException error = assertThrows(ServiceException.class, () -> service.update(7L, orderRequest()));

        assertEquals("仅草稿订单可编辑", error.getMessage());
        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
        verify(mapper, never()).deleteLines(anyLong());
    }

    @Test
    void orderUpdateRejectsInvalidLine() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 0, "orderNo", "SO2026000007"));
        when(mapper.activeCustomer(1L)).thenReturn(1);
        OrderDraftRequest invalid = new OrderDraftRequest(1L, LocalDate.now().plusDays(3),
                List.of(new OrderDraftRequest.Line(1L, 2L, 3L, 0, new BigDecimal("10.00"))));

        assertThrows(ServiceException.class, () -> service.update(7L, invalid));

        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
    }

    @Test
    void orderUpdateReplacesLinesForDraft() {
        ErpOrderMapper mapper = mock(ErpOrderMapper.class);
        ErpOrderService service = new ErpOrderService(mapper);
        when(mapper.lockDraft(7L)).thenReturn(Map.of("status", 0, "orderNo", "SO2026000007"));
        when(mapper.activeCustomer(1L)).thenReturn(1);
        when(mapper.activeSku(1L, 2L, 3L)).thenReturn(10L);
        when(mapper.update(eq(7L), eq(1L), any(LocalDate.class), eq(new BigDecimal("20.00")), eq("tester")))
                .thenReturn(1);

        try (MockedStatic<SecurityUtils> security = mockStatic(SecurityUtils.class)) {
            security.when(SecurityUtils::getUsername).thenReturn("tester");

            Map<String, String> result = service.update(7L, orderRequest());

            assertEquals("SO2026000007", result.get("orderNo"));
            verify(mapper).deleteLines(7L);
            verify(mapper).insertLine(7L, 1L, 2L, 3L, 2, new BigDecimal("10.00"),
                    new BigDecimal("20.00"), "tester");
        }
    }
}
```

- [ ] **Step 2: 运行测试确认编译失败**

Run (from `backend/`):

```bash
mvn -pl myfactoryai-admin -am -Dtest=ErpDraftUpdateTest -DfailIfNoTests=false test
```

Expected: 编译失败，提示 `ErpOrderService.update`、`ErpOrderMapper.lockDraft/update/deleteLines` 不存在。

- [ ] **Step 3: 扩展 ErpOrderMapper**

在 `ErpOrderMapper.java` 的 import 区补充：

```java
import java.time.LocalDate;

import org.apache.ibatis.annotations.Delete;
```

在接口末尾（`confirm` 之后）添加：

```java
    @Select("""
        SELECT status, order_no AS orderNo
        FROM sd_order WHERE order_id = #{orderId} AND del_flag = '0' FOR UPDATE
        """)
    Map<String, Object> lockDraft(@Param("orderId") Long orderId);

    @Update("""
        UPDATE sd_order SET customer_id = #{customerId}, delivery_date = #{deliveryDate},
          total_amount = #{totalAmount}, update_by = #{updatedBy}, update_time = NOW()
        WHERE order_id = #{orderId} AND status = 0 AND del_flag = '0'
        """)
    int update(@Param("orderId") Long orderId, @Param("customerId") Long customerId,
               @Param("deliveryDate") LocalDate deliveryDate,
               @Param("totalAmount") BigDecimal totalAmount,
               @Param("updatedBy") String updatedBy);

    @Delete("DELETE FROM sd_order_item WHERE order_id = #{orderId}")
    int deleteLines(@Param("orderId") Long orderId);
```

- [ ] **Step 4: 重构 ErpOrderService 并添加 update**

把 `create` 中的校验逻辑提取为 `validate`，并把 `create` 改为使用返回值。`ErpOrderService.java` 中替换 `create` 方法及末尾 record：

```java
    @Transactional
    public Map<String, String> create(OrderDraftRequest request) {
        ValidatedDraft draft = validate(request);
        OrderHeader header = new OrderHeader();
        header.setOrderNo("SO-TMP-" + UUID.randomUUID());
        header.setCustomerId(request.customerId());
        header.setDeliveryDate(request.deliveryDate());
        header.setTotalAmount(draft.total());
        header.setCreatedBy(SecurityUtils.getUsername());
        mapper.insert(header);
        String orderNo = "SO" + LocalDate.now().getYear() + String.format("%06d", header.getOrderId());
        mapper.setOrderNo(header.getOrderId(), orderNo);
        for (ValidatedLine validated : draft.lines()) {
            OrderDraftRequest.Line line = validated.line();
            mapper.insertLine(header.getOrderId(), line.styleId(), line.colorId(), line.sizeId(),
                    line.qty(), line.unitPrice(), validated.amount(), header.getCreatedBy());
        }
        return Map.of("orderId", header.getOrderId().toString(), "orderNo", orderNo);
    }

    @Transactional
    public Map<String, String> update(Long orderId, OrderDraftRequest request) {
        if (orderId == null || orderId <= 0) throw new ServiceException("订单编号不合法", 1001);
        Map<String, Object> locked = mapper.lockDraft(orderId);
        if (locked == null) throw new ServiceException("订单不存在", 1001);
        if (((Number) locked.get("status")).intValue() != 0) {
            throw new ServiceException("仅草稿订单可编辑", 2001);
        }
        ValidatedDraft draft = validate(request);
        String username = SecurityUtils.getUsername();
        if (mapper.update(orderId, request.customerId(), request.deliveryDate(),
                draft.total(), username) != 1) {
            throw new ServiceException("订单状态已变化，请刷新后重试", 2001);
        }
        mapper.deleteLines(orderId);
        for (ValidatedLine validated : draft.lines()) {
            OrderDraftRequest.Line line = validated.line();
            mapper.insertLine(orderId, line.styleId(), line.colorId(), line.sizeId(),
                    line.qty(), line.unitPrice(), validated.amount(), username);
        }
        return Map.of("orderId", orderId.toString(),
                "orderNo", String.valueOf(locked.get("orderNo")));
    }

    private ValidatedDraft validate(OrderDraftRequest request) {
        if (request == null || request.customerId() == null || request.deliveryDate() == null
                || request.items() == null || request.items().isEmpty()
                || request.items().size() > 100 || request.deliveryDate().isBefore(LocalDate.now())) {
            throw new ServiceException("请填写客户、有效交期和 1 至 100 条色码明细", 1001);
        }
        if (mapper.activeCustomer(request.customerId()) != 1) {
            throw new ServiceException("客户不存在或已停用", 1001);
        }
        Set<String> seen = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        List<ValidatedLine> lines = new ArrayList<>();
        for (OrderDraftRequest.Line line : request.items()) {
            if (line == null || line.styleId() == null || line.colorId() == null
                    || line.sizeId() == null || line.qty() == null || line.qty() <= 0
                    || line.unitPrice() == null || line.unitPrice().signum() <= 0
                    || line.unitPrice().scale() > 2) {
                throw new ServiceException("色码、数量或单价不合法", 1001);
            }
            String key = line.styleId() + ":" + line.colorId() + ":" + line.sizeId();
            if (!seen.add(key)) throw new ServiceException("订单色码明细重复", 1002);
            if (mapper.activeSku(line.styleId(), line.colorId(), line.sizeId()) == null) {
                throw new ServiceException("色码 SKU 不存在或已停用", 1001);
            }
            BigDecimal amount = line.unitPrice().multiply(BigDecimal.valueOf(line.qty()));
            total = total.add(amount);
            lines.add(new ValidatedLine(line, amount));
        }
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0) {
            throw new ServiceException("订单金额超出上限", 1001);
        }
        return new ValidatedDraft(lines, total);
    }

    private record ValidatedLine(OrderDraftRequest.Line line, BigDecimal amount) {}

    private record ValidatedDraft(List<ValidatedLine> lines, BigDecimal total) {}
```

删除原 `create` 中重复的校验代码与旧 `ValidatedLine` record；`confirm` 保持不变。

- [ ] **Step 5: 控制器暴露 PUT 接口**

在 `ErpOrderController.java` import 区添加 `import org.springframework.web.bind.annotation.PutMapping;`，在 `create` 方法后添加：

```java
    @PreAuthorize("@ss.hasPermi('sd:order:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id, @RequestBody OrderDraftRequest request) {
        return AjaxResult.success(service.update(id, request));
    }
```

- [ ] **Step 6: 运行测试确认通过**

Run (from `backend/`):

```bash
mvn -pl myfactoryai-admin -am -Dtest=ErpDraftUpdateTest -DfailIfNoTests=false test
```

Expected: `Tests run: 3, Failures: 0, Errors: 0`，`BUILD SUCCESS`。

- [ ] **Step 7: 提交**

```bash
git add backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/mapper/ErpOrderMapper.java \
  backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/service/ErpOrderService.java \
  backend/myfactoryai-admin/src/main/java/net/factoryopen/myfactoryai/web/controller/erp/ErpOrderController.java \
  backend/myfactoryai-admin/src/test/java/net/factoryopen/myfactoryai/web/controller/erp/ErpDraftUpdateTest.java
git commit -m "feat: allow editing draft sales orders"
```

---

### Task 2: 采购单草稿更新（后端）

**Files:**
- Modify: `backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/mapper/ErpPurchaseMapper.java`
- Modify: `backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/service/ErpPurchaseService.java`
- Modify: `backend/myfactoryai-admin/src/main/java/net/factoryopen/myfactoryai/web/controller/erp/ErpPurchaseController.java`
- Test: 在 `ErpDraftUpdateTest.java` 追加

- [ ] **Step 1: 追加失败测试**

在 `ErpDraftUpdateTest.java` 的 import 区补充：

```java
import net.factoryopen.myfactoryai.erp.domain.PurchaseDraftRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpPurchaseMapper;
import net.factoryopen.myfactoryai.erp.service.ErpPurchaseService;
import net.factoryopen.myfactoryai.erp.service.ErpStockService;
```

在类中添加：

```java
    private PurchaseDraftRequest purchaseRequest() {
        return new PurchaseDraftRequest(5L, LocalDate.now().plusDays(2),
                List.of(new PurchaseDraftRequest.Line(9L, new BigDecimal("1.500"), new BigDecimal("4.00"))));
    }

    @Test
    void purchaseUpdateRejectsConfirmedDocument() {
        ErpPurchaseMapper mapper = mock(ErpPurchaseMapper.class);
        ErpPurchaseService service = new ErpPurchaseService(mapper, mock(ErpStockService.class));
        when(mapper.lockDraft(3L)).thenReturn(Map.of("status", 3, "poNo", "PO2026000003"));

        ServiceException error = assertThrows(ServiceException.class, () -> service.update(3L, purchaseRequest()));

        assertEquals("仅草稿采购单可编辑", error.getMessage());
        verify(mapper, never()).update(anyLong(), anyLong(), any(LocalDate.class), any(BigDecimal.class), anyString());
        verify(mapper, never()).deleteLines(anyLong());
    }

    @Test
    void purchaseUpdateReplacesLinesForDraft() {
        ErpPurchaseMapper mapper = mock(ErpPurchaseMapper.class);
        ErpPurchaseService service = new ErpPurchaseService(mapper, mock(ErpStockService.class));
        when(mapper.lockDraft(3L)).thenReturn(Map.of("status", 0, "poNo", "PO2026000003"));
        when(mapper.activeSupplier(5L)).thenReturn(1);
        when(mapper.activeMaterial(9L)).thenReturn(1);
        when(mapper.update(eq(3L), eq(5L), any(LocalDate.class), eq(new BigDecimal("6.00")), eq("tester")))
                .thenReturn(1);

        try (MockedStatic<SecurityUtils> security = mockStatic(SecurityUtils.class)) {
            security.when(SecurityUtils::getUsername).thenReturn("tester");

            Map<String, String> result = service.update(3L, purchaseRequest());

            assertEquals("PO2026000003", result.get("poNo"));
            verify(mapper).deleteLines(3L);
            verify(mapper).insertLine(3L, 9L, new BigDecimal("1.500"), new BigDecimal("4.00"),
                    new BigDecimal("6.00"), "tester");
        }
    }
```

- [ ] **Step 2: 运行测试确认失败**

```bash
mvn -pl myfactoryai-admin -am -Dtest=ErpDraftUpdateTest -DfailIfNoTests=false test
```

Expected: 编译失败，提示 `ErpPurchaseService.update` 与 Mapper 新方法不存在。

- [ ] **Step 3: 扩展 ErpPurchaseMapper**

在 `ErpPurchaseMapper.java` import 区添加 `import org.apache.ibatis.annotations.Delete;`，在接口末尾添加：

```java
    @Select("""
        SELECT status, po_no AS poNo
        FROM pu_purchase_order WHERE po_id = #{poId} AND del_flag = '0' FOR UPDATE
        """)
    Map<String, Object> lockDraft(@Param("poId") Long poId);

    @Update("""
        UPDATE pu_purchase_order SET supplier_id = #{supplierId}, expect_date = #{expectDate},
          total_amount = #{totalAmount}, update_by = #{updatedBy}, update_time = NOW()
        WHERE po_id = #{poId} AND status = 0 AND del_flag = '0'
        """)
    int update(@Param("poId") Long poId, @Param("supplierId") Long supplierId,
               @Param("expectDate") LocalDate expectDate,
               @Param("totalAmount") BigDecimal totalAmount,
               @Param("updatedBy") String updatedBy);

    @Delete("DELETE FROM pu_purchase_order_item WHERE po_id = #{poId}")
    int deleteLines(@Param("poId") Long poId);
```

- [ ] **Step 4: 重构 ErpPurchaseService 并添加 update**

把 `create` 的校验提取为 `validate`，`create` 改为消费返回值：

```java
    @Transactional
    public Map<String,String> create(PurchaseDraftRequest request) {
        ValidatedDraft draft = validate(request);
        PurchaseHeader header = new PurchaseHeader();
        header.setPoNo("PO-TMP-" + UUID.randomUUID());
        header.setSupplierId(request.supplierId());
        header.setExpectDate(request.expectDate());
        header.setTotalAmount(draft.total());
        header.setCreatedBy(SecurityUtils.getUsername());
        mapper.insert(header);
        String no = "PO" + LocalDate.now().getYear() + String.format("%06d",header.getPoId());
        mapper.setPoNo(header.getPoId(),no);
        for (PurchaseDraftRequest.Line line : draft.lines()) {
            mapper.insertLine(header.getPoId(),line.materialId(),line.qty(),line.unitPrice(),
                    line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP),header.getCreatedBy());
        }
        return Map.of("poId",header.getPoId().toString(),"poNo",no);
    }

    @Transactional
    public Map<String,String> update(Long poId, PurchaseDraftRequest request) {
        if (poId == null || poId <= 0) throw new ServiceException("采购单编号不合法",1001);
        Map<String,Object> locked = mapper.lockDraft(poId);
        if (locked == null) throw new ServiceException("采购单不存在",1001);
        if (((Number) locked.get("status")).intValue() != 0) {
            throw new ServiceException("仅草稿采购单可编辑",2001);
        }
        ValidatedDraft draft = validate(request);
        String username = SecurityUtils.getUsername();
        if (mapper.update(poId,request.supplierId(),request.expectDate(),draft.total(),username) != 1) {
            throw new ServiceException("采购单状态已变化，请刷新后重试",2001);
        }
        mapper.deleteLines(poId);
        for (PurchaseDraftRequest.Line line : draft.lines()) {
            mapper.insertLine(poId,line.materialId(),line.qty(),line.unitPrice(),
                    line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP),username);
        }
        return Map.of("poId",poId.toString(),"poNo",String.valueOf(locked.get("poNo")));
    }

    private ValidatedDraft validate(PurchaseDraftRequest request) {
        if (request == null || request.supplierId() == null || request.expectDate() == null
                || request.expectDate().isBefore(LocalDate.now()) || request.items() == null
                || request.items().isEmpty() || request.items().size() > 100) {
            throw new ServiceException("请填写供应商、有效到货日期和 1 至 100 条物料",1001);
        }
        if (mapper.activeSupplier(request.supplierId()) != 1) throw new ServiceException("供应商不存在或已停用",1001);
        Set<Long> seen = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseDraftRequest.Line line : request.items()) {
            if (line == null || line.materialId() == null || line.qty() == null || line.unitPrice() == null
                    || line.qty().signum() <= 0 || line.qty().scale() > 3 || line.qty().compareTo(new BigDecimal("999999999.999")) > 0
                    || line.unitPrice().signum() <= 0 || line.unitPrice().scale() > 2
                    || line.unitPrice().compareTo(new BigDecimal("99999999.99")) > 0) {
                throw new ServiceException("物料数量或单价不合法",1001);
            }
            if (!seen.add(line.materialId())) throw new ServiceException("采购物料重复",1002);
            if (mapper.activeMaterial(line.materialId()) != 1) throw new ServiceException("物料不存在或已停用",1001);
            total = total.add(line.qty().multiply(line.unitPrice()).setScale(2,RoundingMode.HALF_UP));
        }
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0) throw new ServiceException("采购金额超出上限",1001);
        return new ValidatedDraft(request.items(), total);
    }

    private record ValidatedDraft(List<PurchaseDraftRequest.Line> lines, BigDecimal total) {}
```

删除原 `create` 中的内联校验代码。

- [ ] **Step 5: 控制器暴露 PUT 接口**

在 `ErpPurchaseController.java` import 区添加 `import org.springframework.web.bind.annotation.PutMapping;`，在 `create` 后添加：

```java
    @PreAuthorize("@ss.hasPermi('pu:purchaseOrder:edit')")
    @PutMapping("/{id}")
    public AjaxResult update(@PathVariable Long id, @RequestBody PurchaseDraftRequest request) {
        return AjaxResult.success(service.update(id, request));
    }
```

- [ ] **Step 6: 运行测试**

```bash
mvn -pl myfactoryai-admin -am -Dtest=ErpDraftUpdateTest -DfailIfNoTests=false test
```

Expected: `Tests run: 5, Failures: 0, Errors: 0`。

- [ ] **Step 7: 提交**

```bash
git add backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/mapper/ErpPurchaseMapper.java \
  backend/myfactoryai-manufacturing/src/main/java/net/factoryopen/myfactoryai/erp/service/ErpPurchaseService.java \
  backend/myfactoryai-admin/src/main/java/net/factoryopen/myfactoryai/web/controller/erp/ErpPurchaseController.java \
  backend/myfactoryai-admin/src/test/java/net/factoryopen/myfactoryai/web/controller/erp/ErpDraftUpdateTest.java
git commit -m "feat: allow editing draft purchase orders"
```

---

### Task 3: 权限种子

**Files:**
- Modify: `sql/erp-permissions.sql`

- [ ] **Step 1: 新增菜单权限**

在 `sql/erp-permissions.sql` 的 `VALUES` 列表末尾，把最后一行 `(90078, ...)` 改为带逗号，并追加：

```sql
  (90079, '编辑订单', 90002, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:edit', '#', 'admin', NOW(), ''),
  (90080, '编辑采购', 90008, 5, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:edit', '#', 'admin', NOW(), '')
```

- [ ] **Step 2: 更新角色授权**

将 supervisor 的区间：

```sql
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90000 AND 90078
```

改为：

```sql
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90000 AND 90080
```

在销售角色菜单列表（`r.role_key IN ('sales', 'salesAssistant')` 的 `IN (...)`）末尾追加 `,90079`；在 `planner` 的菜单列表末尾追加 `,90080`。

- [ ] **Step 3: 提交**

```bash
git add sql/erp-permissions.sql
git commit -m "feat: seed edit permissions for orders and purchases"
```

---

### Task 4: 前端 API 与演示模式

**Files:**
- Modify: `frontend/src/api/erp.ts`
- Modify: `frontend/src/api/demo.ts`

- [ ] **Step 1: erp.ts 增加更新方法**

在 `createOrder` 之后添加：

```ts
  updateOrder: (id: string, draft: OrderDraft) => isDemoMode() ? demoApi.updateOrder(id, draft) : payload<{ orderId: string; orderNo: string }>(http.put(`/erp/sd/order/${encodeURIComponent(id)}`, draft)),
```

在 `createPurchase` 之后添加：

```ts
  updatePurchase: (id: string, draft: PurchaseDraft) => isDemoMode() ? demoApi.updatePurchase(id, draft) : payload<{ poId: string; poNo: string }>(http.put(`/erp/pu/purchaseOrder/${encodeURIComponent(id)}`, draft)),
```

- [ ] **Step 2: demo.ts 增加更新实现**

在 `createPurchase` 之后添加：

```ts
  updatePurchase: async (id: string, draft: PurchaseDraft) => {
    const purchases = readPurchases()
    const purchase = purchases.find(item => item.poId === id)
    if (!purchase || purchase.status !== 0) throw new Error('仅草稿采购单可编辑')
    const supplier = purchaseOptions.suppliers.find(item => item.id === draft.supplierId)
    if (!supplier || !draft.items.length) throw new Error('供应商或采购明细无效')
    const items = draft.items.map((item, index) => {
      const material = purchaseOptions.materials.find(value => value.id === item.materialId)
      if (!material) throw new Error('物料不存在')
      return { itemId: `${id}-${index}`, materialId: material.id, materialCode: material.code!, materialName: material.name, unit: material.unit!, qty: item.qty, unitPrice: item.unitPrice, amount: Math.round(item.qty * item.unitPrice * 100) / 100, receivedQty: 0, remainingQty: item.qty }
    })
    purchase.supplierId = supplier.id
    purchase.supplierName = supplier.name
    purchase.expectDate = draft.expectDate
    purchase.items = items
    purchase.totalAmount = items.reduce((sum, item) => sum + item.amount, 0)
    purchase.lineCount = items.length
    savePurchases(purchases)
    return { poId: purchase.poId, poNo: purchase.poNo }
  },
```

在 `createOrder` 之后添加：

```ts
  updateOrder: async (id: string, draft: OrderDraft) => {
    const orders = readOrders()
    const order = orders.find(item => item.orderId === id)
    if (!order || order.status !== 0) throw new Error('仅草稿订单可编辑')
    const customer = options.customers.find(item => item.id === draft.customerId)
    if (!customer || !draft.items.length) throw new Error('客户或订单明细无效')
    const items = draft.items.map((item, index) => {
      const sku = options.skus.find(candidate => candidate.styleId === item.styleId && candidate.colorId === item.colorId && candidate.sizeId === item.sizeId)
      if (!sku) throw new Error('SKU 不存在')
      return line(sku.id, item.qty, item.unitPrice, 0, `${Date.now()}-${index}`)
    })
    order.customerId = customer.id
    order.customerName = customer.name
    order.deliveryDate = draft.deliveryDate
    order.items = items
    order.totalAmount = items.reduce((sum, item) => sum + item.amount, 0)
    order.lineCount = items.length
    order.orderQty = items.reduce((sum, item) => sum + item.qty, 0)
    saveOrders(orders)
    return { orderId: order.orderId, orderNo: order.orderNo }
  },
```

- [ ] **Step 3: 提交**

```bash
git add frontend/src/api/erp.ts frontend/src/api/demo.ts
git commit -m "feat: add draft update API calls for orders and purchases"
```

---

### Task 5: 路由与销售订单编辑表单

**Files:**
- Modify: `frontend/src/router.ts`
- Modify: `frontend/src/views/OrderFormView.vue`

- [ ] **Step 1: 新增路由**

在 `frontend/src/router.ts` 的 `/orders/:id` 路由之前插入：

```ts
    { path: '/orders/:id/edit', name: 'edit-order', component: () => import('./views/OrderFormView.vue'), meta: { permission: 'sd:order:edit' } },
```

在 `/purchases/:id` 路由之前插入：

```ts
    { path: '/purchases/:id/edit', name: 'edit-purchase', component: () => import('./views/PurchaseFormView.vue'), meta: { permission: 'pu:purchaseOrder:edit' } },
```

- [ ] **Step 2: OrderFormView 支持编辑模式**

把 `OrderFormView.vue` 的 `<script setup>` 替换为：

```vue
<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Delete, Plus } from '@element-plus/icons-vue'
import { erpApi, type OrderDraft, type OrderOptions } from '../api/erp'

type Line = { skuId: string; qty: number; unitPrice: number }
const route = useRoute()
const router = useRouter()
const editing = computed(() => Boolean(route.params.id))
const orderNo = ref('')
const options = ref<OrderOptions>({ customers: [], styles: [], colors: [], sizes: [], skus: [] })
const form = reactive<{ customerId: string; deliveryDate: string; lines: Line[] }>({ customerId: '', deliveryDate: '', lines: [{ skuId: '', qty: 1, unitPrice: 0 }] })
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const total = computed(() => form.lines.reduce((sum, line) => sum + Number(line.qty || 0) * Number(line.unitPrice || 0), 0))
const cancelPath = computed(() => editing.value ? `/orders/${route.params.id}` : '/orders')
const nameOf = (items: { id: string; name: string }[], id?: string) => items.find(item => item.id === id)?.name || '—'
function skuName(id: string) { const sku = options.value.skus.find(item => item.id === id); return sku ? `${sku.code} · ${nameOf(options.value.styles, sku.styleId)} / ${nameOf(options.value.colors, sku.colorId)} / ${nameOf(options.value.sizes, sku.sizeId)}` : '' }
async function load() {
  loading.value = true
  try {
    options.value = await erpApi.options()
    if (!editing.value) return
    const order = await erpApi.order(String(route.params.id))
    if (order.status !== 0) {
      ElMessage.warning('已确认单据不可编辑')
      await router.replace(`/orders/${order.orderId}`)
      return
    }
    orderNo.value = order.orderNo
    form.customerId = order.customerId
    form.deliveryDate = order.deliveryDate
    const items = order.items || []
    form.lines = items.map(item => ({ skuId: item.skuId, qty: item.qty, unitPrice: item.unitPrice }))
    if (!form.lines.length) form.lines = [{ skuId: '', qty: 1, unitPrice: 0 }]
  }
  catch (e) { error.value = (e as Error).message; ElMessage.error(error.value) }
  finally { loading.value = false }
}
async function submit() {
  if (!form.customerId || !form.deliveryDate || !form.lines.length) return ElMessage.warning('请选择客户、交期和订单明细')
  const now = new Date()
  const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  if (form.deliveryDate < today) return ElMessage.warning('交期不能早于今天')
  if (form.lines.some(line => !line.skuId || !Number.isInteger(line.qty) || line.qty <= 0 || line.unitPrice <= 0 || Math.round(line.unitPrice * 100) !== line.unitPrice * 100)) return ElMessage.warning('请填写有效 SKU、数量和单价')
  if (new Set(form.lines.map(line => line.skuId)).size !== form.lines.length) return ElMessage.warning('同一 SKU 只能添加一次')
  saving.value = true
  try {
    const draft: OrderDraft = { customerId: form.customerId, deliveryDate: form.deliveryDate, items: form.lines.map(line => { const sku = options.value.skus.find(item => item.id === line.skuId)!; return { styleId: sku.styleId!, colorId: sku.colorId!, sizeId: sku.sizeId!, qty: line.qty, unitPrice: line.unitPrice } }) }
    if (editing.value) {
      await erpApi.updateOrder(String(route.params.id), draft)
      ElMessage.success(`订单 ${orderNo.value} 已更新`)
      await router.replace(`/orders/${route.params.id}`)
    } else {
      const result = await erpApi.createOrder(draft)
      ElMessage.success(`订单 ${result.orderNo} 已创建`)
      await router.replace(`/orders/${result.orderId}`)
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>
```

模板中替换两处：

```vue
  <div class="page-head"><div><h1>{{ editing ? '编辑销售订单' : '新建销售订单' }}</h1><p>{{ editing ? `单号 ${orderNo}` : '填写客户、交期及产品色码明细' }}</p></div><el-button @click="router.push(cancelPath)">返回列表</el-button></div>
```

```vue
    </section><div class="form-actions"><el-button @click="router.push(cancelPath)">取消</el-button><el-button type="primary" :loading="saving" @click="submit">{{ editing ? '保存修改' : '保存草稿' }}</el-button></div>
```

- [ ] **Step 3: 类型检查**

```bash
cd frontend && npm run build
```

Expected: 构建成功，无 TypeScript 错误。

- [ ] **Step 4: 提交**

```bash
git add frontend/src/router.ts frontend/src/views/OrderFormView.vue
git commit -m "feat: support editing draft sales orders in the form"
```

---

### Task 6: 采购单编辑表单

**Files:**
- Modify: `frontend/src/views/PurchaseFormView.vue`

- [ ] **Step 1: 支持编辑模式**

把 `<script setup>` 替换为：

```vue
<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Delete, Plus } from '@element-plus/icons-vue'
import { erpApi, type PurchaseDraft, type PurchaseOptions } from '../api/erp'

const route = useRoute()
const router = useRouter()
const editing = computed(() => Boolean(route.params.id))
const poNo = ref('')
const options = ref<PurchaseOptions>({ suppliers: [], materials: [] })
const form = reactive({ supplierId: '', expectDate: '', items: [{ materialId: '', qty: 1, unitPrice: 0 }] })
const loading = ref(true)
const saving = ref(false)
const error = ref('')
const total = computed(() => form.items.reduce((sum, item) => sum + item.qty * item.unitPrice, 0))
const cancelPath = computed(() => editing.value ? `/purchases/${route.params.id}` : '/purchases')
function selectMaterial(id: string, index: number) { form.items[index].unitPrice = Number(options.value.materials.find(item => item.id === id)?.refPrice || 0) }
async function load() {
  loading.value = true; error.value = ''
  try {
    options.value = await erpApi.purchaseOptions()
    if (!editing.value) return
    const purchase = await erpApi.purchase(String(route.params.id))
    if (purchase.status !== 0) {
      ElMessage.warning('已确认单据不可编辑')
      await router.replace(`/purchases/${purchase.poId}`)
      return
    }
    poNo.value = purchase.poNo
    form.supplierId = purchase.supplierId
    form.expectDate = purchase.expectDate
    const items = purchase.items || []
    form.items = items.map(item => ({ materialId: item.materialId, qty: item.qty, unitPrice: item.unitPrice }))
    if (!form.items.length) form.items = [{ materialId: '', qty: 1, unitPrice: 0 }]
  } catch (e) { error.value = (e as Error).message } finally { loading.value = false }
}
async function submit() {
  const today = new Date().toISOString().slice(0, 10)
  if (!form.supplierId || !form.expectDate || form.expectDate < today) return ElMessage.warning('请选择供应商和有效到货日期')
  if (form.items.some(item => !item.materialId || !Number.isFinite(item.qty) || item.qty <= 0 || item.unitPrice <= 0 || Math.round(item.qty * 1000) !== item.qty * 1000)) return ElMessage.warning('物料、数量或单价不合法')
  if (new Set(form.items.map(item => item.materialId)).size !== form.items.length) return ElMessage.warning('物料不能重复')
  saving.value = true
  try {
    const draft: PurchaseDraft = { supplierId: form.supplierId, expectDate: form.expectDate, items: form.items }
    if (editing.value) {
      await erpApi.updatePurchase(String(route.params.id), draft)
      ElMessage.success(`采购单 ${poNo.value} 已更新`)
      await router.replace(`/purchases/${route.params.id}`)
    } else {
      const created = await erpApi.createPurchase(draft)
      ElMessage.success('采购草稿已保存')
      await router.replace(`/purchases/${created.poId}`)
    }
  } catch (e) { ElMessage.error((e as Error).message) }
  finally { saving.value = false }
}
onMounted(load)
</script>
```

模板替换两处：

```vue
  <div class="page-head"><div><h1>{{ editing ? '编辑采购单' : '新建采购单' }}</h1><p>{{ editing ? `单号 ${poNo}` : '选择供应商和采购物料' }}</p></div><el-button @click="router.push(cancelPath)">返回列表</el-button></div>
```

```vue
      <div class="line-total">采购合计　¥{{ total.toFixed(2) }}</div></section><div class="form-actions"><el-button @click="router.push(cancelPath)">取消</el-button><el-button type="primary" :loading="saving" @click="submit">{{ editing ? '保存修改' : '保存草稿' }}</el-button></div>
```

- [ ] **Step 2: 类型检查并提交**

```bash
cd frontend && npm run build
git add frontend/src/views/PurchaseFormView.vue
git commit -m "feat: support editing draft purchase orders in the form"
```

Expected: 构建成功。

---

### Task 7: 订单 / 采购列表操作列

**Files:**
- Modify: `frontend/src/views/OrdersView.vue`
- Modify: `frontend/src/views/PurchasesView.vue`

- [ ] **Step 1: 销售订单增加编辑按钮**

把 `OrdersView.vue` 的操作列替换为：

```vue
<el-table-column label="操作" width="150"><template #default="scope"><el-button v-if="user.can('sd:order:query')" text type="primary" @click.stop="router.push(`/orders/${scope.row.orderId}`)">详情</el-button><el-button v-if="scope.row.status === 0 && user.can('sd:order:edit')" text type="primary" @click.stop="router.push(`/orders/${scope.row.orderId}/edit`)">编辑</el-button></template></el-table-column>
```

- [ ] **Step 2: 采购列表增加操作列**

在 `PurchasesView.vue` 的“状态”列之后添加：

```vue
<el-table-column label="操作" width="150"><template #default="scope"><el-button v-if="user.can('pu:purchaseOrder:query')" text type="primary" @click.stop="router.push(`/purchases/${scope.row.poId}`)">详情</el-button><el-button v-if="scope.row.status === 0 && user.can('pu:purchaseOrder:edit')" text type="primary" @click.stop="router.push(`/purchases/${scope.row.poId}/edit`)">编辑</el-button></template></el-table-column>
```

- [ ] **Step 3: 类型检查并提交**

```bash
cd frontend && npm run build
git add frontend/src/views/OrdersView.vue frontend/src/views/PurchasesView.vue
git commit -m "feat: add row actions to order and purchase lists"
```

Expected: 构建成功。

---

### Task 8: 档案页统一操作列

**Files:**
- Modify: `frontend/src/views/CatalogView.vue`

- [ ] **Step 1: 增加操作判断**

在 `const addPermission = ...` 之后添加：

```ts
const detailPaths: Record<string, string> = {
  'production/workOrders': '/production/workOrders',
  'shipping/shipments': '/shipping/shipments',
  'shipping/returns': '/shipping/returns',
  'cost/settlements': '/cost/settlements',
  'sales/quotations': '/sales/quotations',
}
const detailPermissions: Record<string, string> = {
  'production/workOrders': 'pr:workOrder:query',
  'shipping/shipments': 'sh:shipment:query',
  'shipping/returns': 'sh:return:query',
  'cost/settlements': 'fn:settlement:query',
  'sales/quotations': 'sd:quotation:query',
}
const detailPath = computed(() => detailPaths[`${group.value}/${key.value}`] || '')
const showDetail = computed(() => Boolean(detailPath.value) && user.can(detailPermissions[`${group.value}/${key.value}`] || ''))
const showMasterEdit = computed(() => editableCatalog.value && user.can(editPermission.value))
const showStylePlan = computed(() => group.value === 'master' && key.value === 'styles')
const showSalaryConfirm = computed(() => group.value === 'hr' && key.value === 'salary' && user.can('hr:salarySlip:confirm'))
const hasRowActions = computed(() => showMasterEdit.value || showDetail.value || showStylePlan.value || showSalaryConfirm.value)
```

把 `openRow` 替换为：

```ts
function openRow(row: Record<string, unknown>) {
  if (showDetail.value) { openDetail(row); return }
  if (showMasterEdit.value) openMaster(row)
}
function openDetail(row: Record<string, unknown>) { router.push(`${detailPath.value}/${row.id}`) }
```

- [ ] **Step 2: 合并模板操作列**

删除原来的两段特殊列：

- `v-if="group === 'master' && key === 'styles'"` 的“BOM / 工序”列；
- `v-if="group === 'hr' && key === 'salary' && ..."` 的“确认”列。

在 `spec.columns` 的 `v-for` 列之后添加：

```vue
<el-table-column v-if="hasRowActions" label="操作" width="180"><template #default="scope"><el-button v-if="showMasterEdit" text type="primary" @click.stop="openMaster(scope.row)">编辑</el-button><el-button v-if="showDetail" text type="primary" @click.stop="openDetail(scope.row)">详情</el-button><el-button v-if="showStylePlan" text type="primary" @click.stop="router.push(`/master/styles/${scope.row.id}`)">BOM / 工序</el-button><el-button v-if="showSalaryConfirm && scope.row.status === 0" text type="primary" :loading="salarySaving" @click.stop="confirmSalary(String(scope.row.id))">确认</el-button></template></el-table-column>
```

- [ ] **Step 3: 类型检查并提交**

```bash
cd frontend && npm run build
git add frontend/src/views/CatalogView.vue
git commit -m "feat: unify catalog row action buttons"
```

Expected: 构建成功。

---

### Task 9: 全量验证

**Files:** 无新增

- [ ] **Step 1: 后端测试**

```bash
cd backend && mvn -pl myfactoryai-admin -am -Dtest=ErpDraftUpdateTest -DfailIfNoTests=false test
```

Expected: 5 个测试全部通过。

- [ ] **Step 2: 前端构建**

```bash
cd frontend && npm run build
```

Expected: 退出码 0。

- [ ] **Step 3: 演示模式手工验证**

启动前端：

```bash
cd frontend && npm run dev
```

浏览器打开 `http://localhost:5173/preview`（自动进入演示模式）：

1. 选择“销售”角色：销售订单列表草稿行出现“编辑”，已确认行只有“详情”；
2. 点击“编辑”：表单回填客户、交期与明细，修改数量后“保存修改”，跳回详情且金额更新；
3. 切换到“计划员”角色：采购订单列表草稿行出现“编辑”，编辑保存后详情更新；
4. 档案页：款式显示“编辑 / BOM / 工序”；报价、工单、出货、退货、结算显示“详情”；工资单显示“确认”；流水/报表无操作列。

- [ ] **Step 4: 提交验证结论**

若上述均通过，无需额外提交；如有修复，按修复内容单独提交。
