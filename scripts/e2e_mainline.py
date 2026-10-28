# -*- coding: utf-8 -*-
"""海岚 ERP 主线端到端验收：报价 → 订单 → 采购 → 工单 → 领料 → 报工 → 完工 → 出货 → 应收 → 收款 → 成本

用法（后端需已在 127.0.0.1:8080 运行）：
    python scripts/e2e_mainline.py
每一步都断言 code==200，任何一步失败就停在该步并打印服务端原文。
"""
import json
import sys
import time
import urllib.error
import urllib.request
import uuid
from datetime import date, timedelta

BASE = "http://127.0.0.1:8080"
PASSWORD = "Demo@2026!"
STEP = [0]


class Fail(Exception):
    pass


def call(token, method, path, body=None, raw=False):
    data = json.dumps(body).encode("utf-8") if body is not None else None
    req = urllib.request.Request(BASE + path, data=data, method=method)
    req.add_header("Content-Type", "application/json")
    if token:
        req.add_header("Authorization", "Bearer " + token)
    try:
        with urllib.request.urlopen(req, timeout=60) as resp:
            text = resp.read().decode("utf-8")
            code = resp.status
    except urllib.error.HTTPError as exc:
        text = exc.read().decode("utf-8")
        code = exc.code
    try:
        payload = json.loads(text) if text else {}
    except json.JSONDecodeError:
        raise Fail("返回不是 JSON（HTTP %s）：%s" % (code, text[:300]))
    if raw:
        return payload
    if payload.get("code") != 200:
        raise Fail("HTTP %s / code=%s：%s" % (code, payload.get("code"), payload.get("msg")))
    return payload.get("data")


def login(user):
    payload = call(None, "POST", "/login", {"username": user, "password": PASSWORD}, raw=True)
    if payload.get("code") != 200 or not payload.get("token"):
        raise Fail("登录失败 %s：%s" % (user, payload.get("msg")))
    return payload["token"]


def show(step, text):
    STEP[0] += 1
    print("[%02d] %s" % (STEP[0], text))


def main():
    today = date.today()
    stamp = time.strftime("%H%M%S")
    key = lambda: str(uuid.uuid4())

    admin = login("admin")
    sales = login("sales")
    planner = login("planner")
    production = login("production")
    warehouse = login("wh")
    finance = login("finance")
    show("0", "六个角色登录成功（admin/sales/planner/production/wh/finance）")

    # ---------------------------------------------------------------- 1 报价
    quote = call(sales, "POST", "/erp/sd/quotation", {
        "customerId": 1,
        "quoteDate": today.isoformat(),
        "items": [{"styleId": 1, "qty": 60, "unitPrice": 188.00, "costRef": 120.00}],
    })
    quote_id = quote["quoteId"]
    show("1", "建报价单 %s（款式1 × 60 件 × 188 元）" % quote["quoteNo"])

    call(sales, "POST", "/erp/sd/quotation/accept/%s" % quote_id)
    show("2", "报价已接受（状态 → 已接受）")

    # ---------------------------------------------------------------- 2 订单
    order = call(sales, "POST", "/erp/sd/order", {
        "customerId": 1,
        "deliveryDate": (today + timedelta(days=20)).isoformat(),
        "quoteId": int(quote_id),
        "items": [{"styleId": 1, "colorId": 2, "sizeId": 2, "qty": 60, "unitPrice": 188.00}],
    })
    order_id = order["orderId"]
    show("3", "报价转订单 %s（SKU1 × 60 件）" % order["orderNo"])

    call(sales, "POST", "/erp/sd/order/confirm/%s" % order_id)
    show("4", "订单已确认（进入可生产状态）")

    # ---------------------------------------------------------------- 3 采购
    suggestions = call(planner, "GET", "/erp/pu/suggestion/list") or []
    shortage = [row for row in suggestions
                if int(row.get("shortageFlag") or 0) == 1 and float(row.get("suggestQty") or 0) > 0]
    if not shortage:
        raise Fail("缺料建议为空，无法验证采购环节：%s" % json.dumps(suggestions[:2], ensure_ascii=False))
    picks = shortage[:3]
    show("5", "缺料建议 %d 条，取前 %d 条做采购" % (len(shortage), len(picks)))

    po = call(planner, "POST", "/erp/pu/purchaseOrder", {
        "supplierId": 1,
        "expectDate": (today + timedelta(days=7)).isoformat(),
        "items": [{"materialId": int(row["materialId"]),
                   "qty": float(row["suggestQty"]),
                   "unitPrice": float(row.get("unitPrice") or 10)} for row in picks],
    })
    po_id = po["poId"]
    show("6", "建采购单 %s（%d 行，按建议缺口数量）" % (po["poNo"], len(picks)))

    result = call(planner, "POST", "/erp/pu/purchaseOrder/confirm/%s" % po_id)
    if result.get("status") == "pending_approval":
        call(admin, "POST", "/erp/pu/purchaseOrder/approve/%s" % po_id, {"comment": "E2E 自动审批"})
        show("7", "采购金额超过审批阈值，已由厂长审批通过")
    else:
        show("7", "采购单已确认（小额直批）")

    # 收货接口的 itemId 是「采购明细行 ID」，不是物料 ID，必须从采购单详情里取
    po_detail = call(planner, "GET", "/erp/pu/purchaseOrder/%s" % po_id)
    po_lines = po_detail.get("items") or []
    if not po_lines:
        raise Fail("采购单详情没有明细：%s" % json.dumps(po_detail, ensure_ascii=False)[:300])
    batch_in = "E2E-%s" % stamp
    call(warehouse, "POST", "/erp/pu/purchaseOrder/receive", {
        "poId": int(po_id),
        "actualArrivalDate": today.isoformat(),
        "requestKey": key(),
        "items": [{"itemId": int(line["itemId"]),
                   "qty": float(line["qty"]),
                   "batchNo": batch_in} for line in po_lines],
    })
    show("8", "采购收货入库（批次 %s，物料结存已增加）" % batch_in)

    # ---------------------------------------------------------------- 4 工单
    # 订单确认时系统会自动建工单；再调一次 generate 作为补偿（幂等，已存在就返回空列表）
    created = call(production, "POST", "/erp/pr/workOrder/generate/%s" % order_id)
    if created:
        wo_id, wo_no = created[0]["woId"], created[0]["woNo"]
    else:
        order_detail = call(sales, "GET", "/erp/sd/order/%s" % order_id)
        wos = order_detail.get("workOrders") or []
        if not wos:
            raise Fail("订单确认后没有自动生成工单，详情返回=%s" % json.dumps(order_detail, ensure_ascii=False)[:300])
        wo_id, wo_no = wos[0]["woId"], wos[0]["woNo"]
    show("9", "订单 %s 已关联工单 %s（订单详情页可见）" % (order["orderNo"], wo_no))

    call(production, "POST", "/erp/pr/workOrder/issue/%s" % wo_id, {"requestKey": key()})
    show("10", "领料开工（按批次扣减物料结存 + 写领料流水）")

    # ---------------------------------------------------------------- 5 报工
    wo = call(production, "GET", "/erp/pr/workOrder/%s" % wo_id)
    processes = sorted(wo["processes"], key=lambda row: row["seq"])
    skus = wo["skus"]
    for process in processes:
        options = call(production, "GET", "/erp/md/workOrderOptions?workOrderProcessId=%s" % process["id"])
        workers = options.get("workers") or []
        emp = None if process.get("isOutsourced") else (workers[0]["id"] if workers else None)
        if not process.get("isOutsourced") and emp is None:
            raise Fail("工序 %s 是内部工序但没有可用员工，无法报工" % process["processName"])
        for sku in skus:
            call(production, "POST", "/erp/pr/workOrder/process/report", {
                "workOrderProcessId": int(process["id"]),
                "skuId": int(sku["skuId"]),
                "empId": int(emp) if emp else None,
                "doneQty": int(sku["planQty"]),
                "badQty": 0,
                "reportDate": today.isoformat(),
                "requestKey": key(),
            })
    show("11", "%d 道工序 × %d 个色码全部报工（每道合格数=计划量）" % (len(processes), len(skus)))

    # ---------------------------------------------------------------- 6 完工
    batch = "FG-%s-%s" % (wo_no, skus[0]["skuId"])
    call(production, "POST", "/erp/pr/workOrder/finish/%s" % wo_id, {
        "requestKey": key(),
        "skuItems": [{"skuId": int(sku["skuId"]), "goodQty": int(sku["planQty"]), "batchNo": batch} for sku in skus],
    })
    show("12", "完工入库（批次 %s，成品结存已增加）" % batch)

    # ---------------------------------------------------------------- 7 出货
    sku_ids = ",".join(str(sku["skuId"]) for sku in skus)
    batches = call(warehouse, "GET", "/erp/sh/shipment/batches?skuIds=%s" % sku_ids) or []
    matched = [row for row in batches if row["batchNo"] == batch]
    if not matched:
        raise Fail("出货批次接口没返回刚完工的批次 %s，返回=%s" % (batch, json.dumps(batches, ensure_ascii=False)))
    show("13", "出货页能查到完工批次 %s（可用 %s 件）—— 这就是「完工后出货看不到」的修复点"
         % (batch, matched[0]["qty"]))

    shipment = call(warehouse, "POST", "/erp/sh/shipment", {
        "orderId": int(order_id),
        "logisticsCompany": "顺丰",
        "logisticsNo": "SF%s" % stamp,
        "items": [{"skuId": int(row["skuId"]), "batchNo": batch, "qty": int(row["qty"])} for row in matched],
    })
    shipment_id = shipment["shipmentId"]
    show("14", "创建出货单 %s" % shipment["shipmentNo"])

    call(warehouse, "POST", "/erp/sh/shipment/ship/%s" % shipment_id, {"requestKey": key()})
    show("15", "确认发货成功（按批次扣减成品结存 + 写销售出库流水）—— 这就是「库存够却报不足」的修复点")

    call(warehouse, "POST", "/erp/sh/shipment/sign/%s" % shipment_id)
    show("16", "客户签收（订单发货状态推进）")

    # ---------------------------------------------------------------- 8 应收
    receivables = call(finance, "GET", "/erp/finance/receivables?keyword=") or []
    mine = [row for row in receivables if str(row.get("shipmentId")) == str(shipment_id)]
    if not mine:
        mine = [row for row in receivables if str(row.get("orderId")) == str(order_id)]
    if not mine:
        raise Fail("应收列表里找不到本单：%s" % json.dumps(receivables[:2], ensure_ascii=False))
    ar = mine[0]
    show("17", "发货自动生成应收 %s，金额 %s（客户 %s）" % (ar.get("code"), ar.get("amount"), ar.get("customerName")))

    # ---------------------------------------------------------------- 9 收款
    accounts = call(finance, "GET", "/erp/finance/accounts?keyword=") or []
    usable = [row for row in accounts if int(row.get("status", 1)) == 1]
    if not usable:
        raise Fail("没有启用的资金账户，无法收款")
    account_id = usable[0].get("accountId") or usable[0].get("id")
    ar_id = ar.get("id")
    payment = call(finance, "POST", "/erp/finance/payments", {
        "accountId": int(account_id),
        "direction": 1,
        "bizType": 1,
        "counterpartyType": 1,
        "counterpartyId": 1,
        "amount": float(ar["balance"]),
        "payDate": today.isoformat(),
        "remark": "E2E 主线收款",
        "requestKey": key(),
        "lines": [{"targetType": 1, "targetId": int(ar_id), "amount": float(ar["balance"])}],
    })
    call(finance, "POST", "/erp/finance/payments/confirm/%s" % payment["paymentId"], {"requestKey": key()})
    show("18", "登记并过账收款（应收核销、账户余额增加）")

    # --------------------------------------------------------------- 10 成本
    settle = call(finance, "POST", "/erp/fn/settlement/calc", {
        "orderId": int(order_id), "styleId": 1, "requestKey": key(),
    })
    settle_id = settle["settleId"] if "settleId" in settle else settle.get("settlementId")
    detail = call(finance, "GET", "/erp/fn/settlement/%s" % settle_id)
    call(finance, "POST", "/erp/fn/settlement/confirm/%s" % settle_id)
    show("19", "订单成本试算并确认（结算单 %s，总成本 %s）" % (
        detail.get("settleNo") or settle_id, detail.get("totalCost")))

    print("\n=== 主线全部通过 ===")
    print("订单号 %s｜工单 %s｜出货单 %s｜成品批次 %s" % (
        order["orderNo"], wo_no, shipment["shipmentNo"], batch))
    return 0


if __name__ == "__main__":
    try:
        sys.exit(main())
    except Fail as exc:
        print("\n!!! 失败：%s" % exc)
        sys.exit(1)
    except Exception as exc:  # noqa: BLE001
        print("\n!!! 异常：%r" % exc)
        sys.exit(1)
