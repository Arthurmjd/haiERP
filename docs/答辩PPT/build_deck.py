# -*- coding: utf-8 -*-
"""海岚 ERP 答辩 PPT 生成脚本（蓝白主题，业务流为主线）。

运行：python build_deck.py
输出：海岚ERP-答辩汇报.pptx
依赖：python-pptx
"""
import os
from pptx import Presentation
from pptx.util import Pt
from pptx.dml.color import RGBColor
from pptx.enum.text import PP_ALIGN, MSO_ANCHOR
from pptx.enum.shapes import MSO_SHAPE
from pptx.oxml.ns import qn

OUT_DIR = os.path.dirname(os.path.abspath(__file__))
OUT_FILE = os.path.join(OUT_DIR, "海岚ERP-答辩汇报.pptx")

# ---------- 配色：蓝白 ----------
P900 = RGBColor(0x0E, 0x2E, 0x5C)   # 深蓝（封面/强调）
P700 = RGBColor(0x1B, 0x4F, 0x9C)   # 主蓝
P500 = RGBColor(0x2E, 0x7B, 0xD6)   # 亮蓝
P200 = RGBColor(0xB9, 0xD1, 0xF2)   # 浅蓝（分隔/描边）
P100 = RGBColor(0xE8, 0xF1, 0xFC)   # 极浅蓝（卡片底）
P050 = RGBColor(0xF4, 0xF8, 0xFE)   # 背景块
WHITE = RGBColor(0xFF, 0xFF, 0xFF)
TEXT = RGBColor(0x1A, 0x24, 0x33)   # 正文
MUTED = RGBColor(0x5B, 0x6B, 0x7F)  # 次要
LINE = RGBColor(0xD8, 0xE4, 0xF3)

FONT = "微软雅黑"
W, H = 960, 540


def set_font(run, size, bold=False, color=TEXT, font=FONT):
    run.font.size = Pt(size)
    run.font.bold = bold
    run.font.color.rgb = color
    run.font.name = font
    rPr = run._r.get_or_add_rPr()
    ea = rPr.find(qn("a:ea"))
    if ea is None:
        ea = rPr.makeelement(qn("a:ea"), {})
        rPr.append(ea)
    ea.set("typeface", font)


def text(slide, x, y, w, h, s, size=14, bold=False, color=TEXT,
         align=PP_ALIGN.LEFT, anchor=MSO_ANCHOR.TOP, spacing=1.25,
         margin=0):
    box = slide.shapes.add_textbox(Pt(x), Pt(y), Pt(w), Pt(h))
    tf = box.text_frame
    tf.word_wrap = True
    tf.vertical_anchor = anchor
    tf.margin_left = Pt(margin)
    tf.margin_right = Pt(margin)
    tf.margin_top = Pt(0)
    tf.margin_bottom = Pt(0)
    lines = s.split("\n")
    for i, ln in enumerate(lines):
        p = tf.paragraphs[0] if i == 0 else tf.add_paragraph()
        p.alignment = align
        p.line_spacing = spacing
        p.space_after = Pt(0)
        r = p.add_run()
        r.text = ln
        set_font(r, size, bold, color)
    return box


def rect(slide, x, y, w, h, fill, line=None, line_w=0.75, radius=None,
         shape=MSO_SHAPE.RECTANGLE):
    sp = slide.shapes.add_shape(shape, Pt(x), Pt(y), Pt(w), Pt(h))
    if fill is None:
        sp.fill.background()
    else:
        sp.fill.solid()
        sp.fill.fore_color.rgb = fill
    if line is None:
        sp.line.fill.background()
    else:
        sp.line.color.rgb = line
        sp.line.width = Pt(line_w)
    sp.shadow.inherit = False
    if radius is not None and shape == MSO_SHAPE.ROUNDED_RECTANGLE:
        try:
            sp.adjustments[0] = radius
        except Exception:
            pass
    if sp.has_text_frame:
        sp.text_frame.word_wrap = True
    return sp


def rrect(slide, x, y, w, h, fill, line=None, radius=0.10, line_w=0.75):
    return rect(slide, x, y, w, h, fill, line, line_w, radius, MSO_SHAPE.ROUNDED_RECTANGLE)


def shape_text(sp, s, size=12, bold=False, color=WHITE,
               align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, spacing=1.15):
    tf = sp.text_frame
    tf.word_wrap = True
    tf.vertical_anchor = anchor
    tf.margin_left = Pt(4)
    tf.margin_right = Pt(4)
    tf.margin_top = Pt(2)
    tf.margin_bottom = Pt(2)
    lines = s.split("\n")
    for i, ln in enumerate(lines):
        p = tf.paragraphs[0] if i == 0 else tf.add_paragraph()
        p.alignment = align
        p.line_spacing = spacing
        p.space_after = Pt(0)
        r = p.add_run()
        r.text = ln
        set_font(r, size, bold, color)


def blank(prs):
    s = prs.slides.add_slide(prs.slide_layouts[6])
    bg = s.background
    bg.fill.solid()
    bg.fill.fore_color.rgb = WHITE
    return s


def header(slide, kicker, title, page=None):
    text(slide, 48, 30, 600, 16, kicker, 10.5, True, P500, spacing=1.0)
    text(slide, 48, 48, 800, 30, title, 23, True, P900, spacing=1.0)
    rect(slide, 48, 84, 44, 3, P500)
    rect(slide, 92, 85.4, W - 140, 0.8, LINE)
    footer(slide, page)


def footer(slide, page=None):
    text(slide, 48, 512, 420, 14, "海岚 ERP · 服装制造企业资源计划", 8, False, MUTED, spacing=1.0)
    if page:
        text(slide, W - 78, 512, 30, 14, str(page), 9, True, P500, PP_ALIGN.RIGHT, spacing=1.0)


def note(slide, s):
    slide.notes_slide.notes_text_frame.text = s


def arrow(slide, x, y, w=18, h=14, color=P200):
    sp = slide.shapes.add_shape(MSO_SHAPE.RIGHT_ARROW, Pt(x), Pt(y), Pt(w), Pt(h))
    sp.fill.solid()
    sp.fill.fore_color.rgb = color
    sp.line.fill.background()
    sp.shadow.inherit = False
    return sp


# =====================================================================
prs = Presentation()
prs.slide_width = Pt(W)
prs.slide_height = Pt(H)

# ---------------------------------------------------------------- 1 封面
s = blank(prs)
rect(s, 0, 0, W, 8, P700)
rect(s, 0, 8, W, 242, P050)
rect(s, 0, 250, W, 3, P700)

text(s, 60, 74, 520, 18, "课程答辩 · 实训项目", 12, True, P500, spacing=1.0)
text(s, 60, 100, 620, 60, "海岚 ERP", 52, True, P900, spacing=1.0)
text(s, 60, 168, 660, 30, "服装制造企业资源计划 · 从报价到成本的一条完整链路", 17, False, P700, spacing=1.2)
text(s, 60, 204, 660, 20, "Vue 3 + TypeScript  /  Java 21 + Spring Boot 3.5  /  MySQL 8 + Redis", 11.5, False, MUTED, spacing=1.0)

# 右侧三个数字
nums = [("19", "步业务主流程"), ("8", "个岗位角色"), ("60", "项数据自检")]
for i, (n, lab) in enumerate(nums):
    x = 640 + i * 100
    text(s, x, 92, 90, 34, n, 30, True, P500, PP_ALIGN.CENTER, spacing=1.0)
    text(s, x, 130, 90, 16, lab, 9, False, MUTED, PP_ALIGN.CENTER, spacing=1.0)

# 底部信息条
info = [
    ("答辩人", "（填写姓名）"),
    ("团队", "7 人 · 前后端分离"),
    ("日期", "2026 年 10 月"),
]
for i, (k, v) in enumerate(info):
    x = 60 + i * 220
    text(s, x, 300, 60, 14, k, 9, True, MUTED, spacing=1.0)
    text(s, x, 318, 200, 18, v, 12, False, TEXT, spacing=1.0)

# 三条结论
bullets = [
    "一条订单跑通 19 步：报价 → 订单 → 采购 → 生产 → 出货 → 应收 → 成本",
    "单据只走动作、库存只认流水、金额只由服务端计算",
    "60 项 SQL 自检保证「账能对上」，8 个岗位按钮级权限可现场对比",
]
for i, b in enumerate(bullets):
    y = 368 + i * 34
    rect(s, 60, y + 7, 6, 6, P500)
    text(s, 78, y, 820, 18, b, 12.5, False, TEXT, spacing=1.0)

note(s, "开场 20 秒：各位老师好，我们做的是服装制造企业的 ERP，覆盖从客户报价、销售订单、采购原料、"
        "生产工单、领料报工、完工入库、出货签收，一直到应收收款和标准成本结算的整条链路。"
        "今天我不按功能菜单讲，按「业务怎么转、系统怎么接、数据怎么对」三条线讲。")

# ---------------------------------------------------------------- 2 项目定位
s = blank(prs)
header(s, "PROJECT OVERVIEW", "项目定位：一条链、九个域、三件事", 2)

text(s, 48, 96, 430, 20, "我们做的不是通用 ERP，而是服装代工厂的「订单到成本」那一段。", 13, True, P700, spacing=1.3)
text(s, 48, 124, 430, 92,
     "上游接客户报价与订单，中间管物料、工单与库存，下游接出货、应收与成本。"
     "九个业务域：主数据、销售、采购、生产、库存、出货、成本、人事、财务。",
     11.5, False, TEXT, spacing=1.55)

text(s, 48, 226, 200, 16, "解决三个具体问题", 11, True, P500, spacing=1.0)
problems = [
    ("订单与物料脱节", "接了单不知道缺多少料 → 净缺料视图按 BOM 算到物料"),
    ("库存账实不符", "有台账没流水 → 全部出入库记流水，结存由流水累加"),
    ("成本算不清", "报价靠拍脑袋 → 标准成本试算并冻结，可被下一轮报价引用"),
]
for i, (t1, t2) in enumerate(problems):
    y = 250 + i * 60
    rrect(s, 48, y, 430, 50, P100, radius=0.14)
    text(s, 60, y + 8, 400, 14, t1, 11, True, P900, spacing=1.0)
    text(s, 60, y + 26, 400, 16, t2, 9.5, False, MUTED, spacing=1.0)

# 右侧规模数字
text(s, 520, 96, 200, 16, "一组实数（可现场验证）", 10.5, True, P500, spacing=1.0)
stats = [
    ("325", "后端 Java 文件"), ("43", "REST 控制器"), ("260", "方法级接口"),
    ("228", "生效权限注解"), ("66", "数据库表"), ("60", "SQL 自检项"),
]
for i, (n, lab) in enumerate(stats):
    r, c = i // 3, i % 3
    x = 520 + c * 140
    y = 122 + r * 92
    rrect(s, x, y, 128, 80, WHITE, P200, radius=0.12)
    text(s, x, y + 14, 128, 30, n, 24, True, P700, PP_ALIGN.CENTER, spacing=1.0)
    text(s, x, y + 50, 128, 14, lab, 9, False, MUTED, PP_ALIGN.CENTER, spacing=1.0)

text(s, 520, 306, 400, 44, "另有 9 个视图、186 项菜单（ERP 132 项：1 目录 + 32 页面 + 99 按钮）、"
                           "8 个岗位、7 个部门与 7 个岗位定义。", 9.5, False, MUTED, spacing=1.4)

# 底部一句话
rrect(s, 48, 436, 864, 50, P050, radius=0.12)
rect(s, 48, 436, 3, 50, P500)
text(s, 66, 448, 830, 26,
     "一句话：我们只做服装代工这一段，但这一段的状态机、动作幂等与库存账实一致，是按能上线的标准做的。",
     10, True, P900, spacing=1.2)
note(s, "这一页给规模和定位。数字都实测过，老师追问可以当场 find/wc 验证。"
        "重点强调：九个域不是九个模块菜单，是一条链路上的九个环节。")

# ---------------------------------------------------------------- 3 业务主流程总览（核心）
s = blank(prs)
header(s, "CORE · BUSINESS FLOW", "业务主流程：一条订单跑完 19 步", 3)

stages = [
    ("01", "接单与备料", ["报价单成交 → 转订单", "订单确认冻结色码/价/期", "净缺料 = 需求 − 现存 − 在途"],
     "产出：订单、缺料清单"),
    ("02", "采购与到货", ["采购单 0→1→2→3", "下单即计入在途", "分批收货写入库流水"],
     "产出：入库流水、应付"),
    ("03", "生产执行", ["按订单款式建工单（冻结）", "齐套后领料、按工序报工", "完工按 SKU 入库"],
     "产出：工单、计件、成品"),
    ("04", "交付与结算", ["分批发货 → 签收", "发货即生成应收", "收款核销、账龄"],
     "产出：出货单、应收"),
    ("05", "核算与闭环", ["标准成本试算 → 确认", "成本快照可复算", "生成下一轮报价"],
     "产出：结算单、新报价"),
]

card_w, step = 160, 180
x0 = 48
for i, (no, name, lines, out) in enumerate(stages):
    x = x0 + i * step
    # 卡片
    rrect(s, x, 108, card_w, 236, WHITE, LINE, radius=0.08)
    # 顶部色带
    band = rrect(s, x, 108, card_w, 40, P700, radius=0.08)
    shape_text(band, no + "  " + name, 11, True, WHITE)
    rect(s, x + 0.6, 132, card_w - 1.2, 16, P700)  # 压平圆角下缘
    # 内容
    for j, ln in enumerate(lines):
        y = 156 + j * 44
        rect(s, x + 11, y + 5, 4, 4, P500)
        text(s, x + 21, y, card_w - 30, 40, ln, 9, False, TEXT, spacing=1.3)
    # 产出
    rect(s, x + 11, 300, card_w - 22, 0.8, LINE)
    text(s, x + 11, 310, card_w - 22, 24, out, 8, True, P500, spacing=1.2)
    # 箭头
    if i < 4:
        arrow(s, x + card_w + 4, 220, 12, 13, P200)

# 底部说明
rrect(s, 48, 366, 864, 54, P050, radius=0.10)
text(s, 66, 376, 830, 18, "贯穿全流程的三条纪律", 10, True, P700, spacing=1.0)
text(s, 66, 394, 830, 20,
     "① 状态只走动作端点（没有「直接改状态」的接口）   ② 库存只认流水，结存由流水累加   ③ 金额只由服务端算，前端不参与结算",
     9.5, False, TEXT, spacing=1.2)

# 底部时间轴
rect(s, 48, 440, 864, 1, P200)
marks = ["客户下单", "物料到位", "成品入库", "客户签收", "成本确认"]
for i, m in enumerate(marks):
    x = 48 + i * 172.8 + 86
    rect(s, x, 437, 2, 7, P500)
    text(s, x - 50, 450, 100, 14, m, 8.5, False, MUTED, PP_ALIGN.CENTER, spacing=1.0)

note(s, "这是全场最重要的一页，讲 90 秒。五个阶段不是五个模块，是一条链路的五个环节："
        "接单与备料决定「能不能做」，采购与到货决定「料有没有」，生产执行决定「做出来没有」，"
        "交付与结算决定「钱回来没有」，核算与闭环决定「下一单报多少」。"
        "底部三条纪律是整套系统的设计前提，后面每一页都在讲它们怎么落地。")

# ---------------------------------------------------------------- 4 阶段一二
s = blank(prs)
header(s, "STAGE 01—02", "接单与备料：从一张报价单到料进仓库", 4)

steps = [
    ("报价单", "0 待确认\n1 已接受\n3 已转单", P500),
    ("销售订单", "0 草稿\n1 已确认\n2 生产中", P500),
    ("净缺料", "BOM 需求\n− 已领 − 现存\n− 在途", P700),
    ("采购单", "0 草稿\n1 已下单\n2 部分到货/3 已到货", P500),
    ("收货入库", "写库存流水\n结存累加\n形成应付", P700),
]
bw, bh = 152, 84
for i, (t1, t2, col) in enumerate(steps):
    x = 48 + i * (bw + 26)
    box = rrect(s, x, 108, bw, bh, WHITE, LINE, radius=0.10)
    rect(s, x, 108, 4, bh, col)
    text(s, x + 14, 116, bw - 20, 18, t1, 12, True, P900, spacing=1.0)
    text(s, x + 14, 140, bw - 20, 46, t2, 8.5, False, MUTED, spacing=1.35)
    if i < 4:
        arrow(s, x + bw + 4, 146, 16, 12, P200)

# 规则卡
rules = [
    ("报价只能转一次", "转单靠「条件更新」：只有已接受的报价能改成已转单，并发下只有一个请求成功；\n"
                  "重复转单返回 2006，整张订单回滚 —— 防止重复生产、重复记账。"),
    ("草稿不计在途", "采购单确认（或审批通过）那一刻才计入在途；收货后已收数量不再算在途，\n"
                 "所以净缺料 = 需求 − 已领 − 现存 − 在途，不会因为「下了单还没到」而重复采购。"),
    ("收货即入账", "收货写库存流水（只增不改）、结存同步累加、按收货金额挂应付；\n"
                "若下单时已付款，收货不再重复挂应付 —— 避免同一批料两次计账。"),
]
for i, (t, d) in enumerate(rules):
    y = 216 + i * 84
    rrect(s, 48, y, 864, 74, P050, radius=0.10)
    rect(s, 48, y, 3, 74, P500)
    text(s, 66, y + 10, 200, 16, t, 10.5, True, P700, spacing=1.0)
    text(s, 66, y + 30, 820, 40, d, 9.5, False, TEXT, spacing=1.35)

note(s, "接单段的两个关键点：一是报价转订单只能一次（防止一张报价下两次单），"
        "二是「草稿不计在途」，否则下了单还没到货就会重复采购。"
        "收货这一段强调「流水只增不改」，这是后面库存能对账的基础。")

# ---------------------------------------------------------------- 5 阶段三
s = blank(prs)
header(s, "STAGE 03", "生产执行：工单冻结，领料只认现存", 5)

# 左侧流程
flow = [("建工单", "冻结色码/ BOM / 工价"), ("领料", "扣库存 + 领料流水"), ("报工", "工序 × SKU 记合格/不良"), ("完工", "按 SKU 入成品库")]
for i, (t1, t2) in enumerate(flow):
    y = 108 + i * 78
    rrect(s, 48, y, 200, 62, P100, radius=0.12)
    text(s, 62, y + 10, 120, 18, t1, 12, True, P900, spacing=1.0)
    text(s, 62, y + 32, 180, 16, t2, 8.5, False, MUTED, spacing=1.0)
    if i < 3:
        sp = s.shapes.add_shape(MSO_SHAPE.DOWN_ARROW, Pt(140), Pt(y + 64), Pt(12), Pt(12))
        sp.fill.solid(); sp.fill.fore_color.rgb = P200
        sp.line.fill.background(); sp.shadow.inherit = False

# 右侧三个约束
text(s, 288, 100, 400, 16, "报工的四道约束（都在服务端校验）", 10.5, True, P500, spacing=1.0)
cons = [
    ("不超过计划量", "同一 SKU 同一工序，累计合格数 ≤ 工单冻结的计划量"),
    ("不超过前道工序", "本工序累计合格数 ≤ 前一道工序的累计合格数（不能凭空多出来）"),
    ("不超工资期", "报工日期所属月份工资单已确认 → 拒绝补录，要先作废工资单"),
    ("外发不计件", "委外工序只记成本，不生成员工计件；内部报工才按「合格数 × 工价快照」"),
]
for i, (t, d) in enumerate(cons):
    y = 122 + i * 60
    rrect(s, 288, y, 620, 52, WHITE, LINE, radius=0.12)
    rect(s, 288, y, 3, 52, P700)
    text(s, 304, y + 8, 170, 16, t, 10, True, P900, spacing=1.0)
    text(s, 304, y + 26, 590, 20, d, 8.8, False, MUTED, spacing=1.2)

# 底部两条
rrect(s, 48, 400, 864, 86, P050, radius=0.10)
text(s, 66, 412, 830, 16, "两个容易被忽略的边界", 9.5, True, P700, spacing=1.0)
text(s, 66, 434, 830, 44,
     "· 领料只认现存库存：采购在途的量只参与缺料计算，不能拿来领料，否则账上会出现负数\n"
     "· 完工入库不超过末道工序累计合格数：没做完的工序不能提前入库，防止「工单说完工、库存却没有」",
     9, False, TEXT, spacing=1.35)

note(s, "生产段的核心是「冻结」：工单建单时把色码计划量、BOM 用量、工序工价抄一份到工单里，"
        "之后改主数据不影响在产工单，成本也能复算。"
        "报工的三道约束是老师最容易追问的地方，答「服务端三重校验：计划量、前道工序、工资期」。")

# ---------------------------------------------------------------- 6 阶段四五
s = blank(prs)
header(s, "STAGE 04—05", "交付与结算：发货即开票，成本是快照", 6)

steps2 = [
    ("出货单", "按未发量选批次\n0 待发货 → 1 已发货 → 2 已签收"),
    ("应收", "金额 = 出货量 × 订单单价\n到期日 = 发货日 + 客户账期"),
    ("收款", "登记 + 确认 → 写资金流水\n应收余额递减，账龄分档"),
    ("标准成本", "材料 + 人工 + 费用\n确认后冻结，不可重算覆盖"),
    ("新报价", "引用已确认成本\n形成下一轮报价依据"),
]
bw2 = 160
for i, (t1, t2) in enumerate(steps2):
    x = 48 + i * (bw2 + 22)
    rrect(s, x, 108, bw2, 92, WHITE, LINE, radius=0.10)
    text(s, x + 14, 118, bw2 - 24, 18, t1, 11.5, True, P900, spacing=1.0)
    text(s, x + 14, 142, bw2 - 24, 50, t2, 8.5, False, MUTED, spacing=1.3)
    if i < 4:
        arrow(s, x + bw2 + 3, 150, 14, 12, P200)

# 三个对比
comp = [
    ("发齐 ≠ 完成", "发齐看累计发货量是否达到订购量；完成看是否所有出货单都签收。两个状态、两个动作。"),
    ("分批发货", "按 SKU + 批次记录，同一订单可以多次发货；未发齐不进入准交率分母，逾期未发齐单独列。"),
    ("标准成本 ≠ 实际成本", "用冻结 BOM × 物料参考价 + 计划合格量 × 冻结工价算；退货不冲回已确认成本。"),
]
for i, (t, d) in enumerate(comp):
    x = 48 + i * 292
    rrect(s, x, 224, 280, 96, P100, radius=0.10)
    text(s, x + 16, 236, 250, 18, t, 11, True, P700, spacing=1.0)
    text(s, x + 16, 260, 250, 52, d, 9, False, TEXT, spacing=1.35)

# 财务闭环条
rrect(s, 48, 340, 864, 76, WHITE, P200, radius=0.10)
text(s, 66, 350, 300, 16, "资金闭环（财务页 8 个标签）", 10, True, P700, spacing=1.0)
chips = ["总览", "账户", "资金流水", "月度现金流", "应收", "应付", "账龄汇总", "收付款"]
for i, c in enumerate(chips):
    x = 66 + i * 104
    ch = rrect(s, x, 374, 96, 28, P050, LINE, radius=0.25)
    shape_text(ch, c, 9, False, P700)

text(s, 48, 432, 864, 40,
     "发货是一个事务：校验期间 → 校验状态与未发量 → 扣库存 + 写出库流水 → 改出货状态 → 改订单状态 → 生成应收。\n"
     "任何一步失败全部回滚，所以不会出现「货发了、应收没生成」。", 9, False, TEXT, spacing=1.4)

note(s, "交付段最容易被问的是「发齐」和「完成」的区别，以及成本口径。"
        "强调：应收是发货那一刻自动生成的，不是手工开的票；成本是标准成本快照，改价不影响历史。"
        "最后那句「一个事务」是答辩加分点——说明为什么不会出现货发了没记账。")

# ---------------------------------------------------------------- 7 三条纪律
s = blank(prs)
header(s, "DESIGN PRINCIPLES", "三条贯穿全系统的纪律", 7)

princ = [
    ("01", "状态只走动作",
     "做法：不给「直接改状态」的接口，\n每个动作的 SQL 自带前置状态\n\n"
     "效果：并发下只有一个请求能改成功，\n其余按失败回滚\n\n"
     "现场可查：ErpOrderService.confirm\n（UPDATE … WHERE status=0）\n\n"
     "被追问「能直接改库吗」：能改，但\n改库等于绕过动作，自检第 28 项会 FAIL"),
    ("02", "动作幂等",
     "做法：请求键 + 参数摘要 + 状态位，\n统一落 biz_action_request 表\n\n"
     "效果：同键重复提交只入账一次；\n同键换参数报 2007；换键正常执行\n\n"
     "现场可查：ErpStockService.begin\nErpStockMapper.reserveAction\n\n"
     "被追问「键谁生成」：前端每次动作\n生成 UUID，网络重试复用同一个"),
    ("03", "账实可对账",
     "做法：结存由流水累加，按「物料 + 批次 +\n仓库」四元组双向比对\n\n"
     "效果：幽灵出入库跑一次自检就暴露，\n不用人工翻账\n\n"
     "现场可查：sql/self_check.sql 第 3 项\n（库存双向对账）\n\n"
     "被追问「怎么发现的」：演示库曾有 5 张\n已签收出货单没有出库流水，就是对出来的"),
]
for i, (no, t, d) in enumerate(princ):
    x = 48 + i * 292
    rrect(s, x, 104, 280, 300, WHITE, LINE, radius=0.08)
    rect(s, x, 104, 280, 46, P700)
    text(s, x + 18, 114, 40, 26, no, 16, True, P200, spacing=1.0)
    text(s, x + 60, 116, 200, 24, t, 13, True, WHITE, spacing=1.0)
    text(s, x + 18, 160, 246, 238, d, 9, False, TEXT, spacing=1.35)

rrect(s, 48, 424, 864, 58, P050, radius=0.10)
text(s, 66, 434, 830, 16, "为什么先讲这三条", 9.5, True, P700, spacing=1.0)
text(s, 66, 452, 830, 24,
     "因为业务链路会变（加节点、改规则），但这三条是地基：状态不乱、动作不重、账能对上。"
     "后面的技术架构与权限设计都是为它们服务的。", 9, False, TEXT, spacing=1.2)

note(s, "这一页把三个技术点翻译成业务语言，避免变成技术罗列。"
        "如果老师问「并发怎么处理」，就从第 2 条展开：条件更新 + 请求键表 + 行锁。")

# ---------------------------------------------------------------- 8 架构
s = blank(prs)
header(s, "ARCHITECTURE", "技术架构：分层清楚，业务独立成模块", 8)

# 三层
layers = [
    ("前端", "Vue 3 + TypeScript + Vite + Element Plus", "28 个页面（10 个手写 + 配置驱动）· 26 条路由 · 按钮级权限", P500),
    ("后端", "Java 21 + Spring Boot 3.5 + MyBatis + Spring Security/JWT", "43 个控制器 · 260 个接口 · 228 处权限注解 · 51 个事务方法", P700),
    ("数据", "MySQL 8（66 张表 / 9 个视图）+ Redis（登录态、AI 限流）", "库存流水只增不改 · 结存由流水累加 · 视图统一口径", P900),
]
for i, (name, tech, desc, col) in enumerate(layers):
    y = 104 + i * 96
    rrect(s, 48, y, 560, 84, WHITE, LINE, radius=0.08)
    rect(s, 48, y, 62, 84, col)
    text(s, 48, y + 30, 62, 20, name, 12, True, WHITE, PP_ALIGN.CENTER, spacing=1.0)
    text(s, 126, y + 12, 460, 16, tech, 10, True, P900, spacing=1.0)
    text(s, 126, y + 36, 460, 36, desc, 9, False, MUTED, spacing=1.3)
    if i < 2:
        sp = s.shapes.add_shape(MSO_SHAPE.DOWN_ARROW, Pt(320), Pt(y + 86), Pt(14), Pt(14))
        sp.fill.solid(); sp.fill.fore_color.rgb = P200
        sp.line.fill.background(); sp.shadow.inherit = False

# 右侧模块
text(s, 636, 104, 300, 16, "后端 6 个 Maven 模块", 10.5, True, P500, spacing=1.0)
mods = [
    ("ruoyi-common", "公共库：统一响应、异常、工具"),
    ("ruoyi-framework", "安全骨架：JWT、权限 Bean"),
    ("ruoyi-quartz", "定时任务（默认关闭）"),
    ("myfactoryai-system", "用户/角色/菜单/部门 + 单据冻结"),
    ("myfactoryai-manufacturing", "业务域：service / mapper / domain"),
    ("myfactoryai-admin", "启动类 + 控制器 + AI 配置"),
]
for i, (m, d) in enumerate(mods):
    y = 124 + i * 44
    rrect(s, 636, y, 276, 38, P050, radius=0.14)
    text(s, 648, y + 5, 250, 14, m, 8.8, True, P700, spacing=1.0)
    text(s, 648, y + 20, 250, 14, d, 7.8, False, MUTED, spacing=1.0)

text(s, 48, 392, 864, 16, "一次请求怎么走（以订单列表为例）", 10, True, P700, spacing=1.0)
chain = ["浏览器", "JWT 过滤", "@PreAuthorize", "Controller", "Service（校验/事务）", "Mapper（注解 SQL）", "MySQL"]
for i, c in enumerate(chain):
    x = 48 + i * 124
    ch = rrect(s, x, 414, 112, 30, P100, None, radius=0.18)
    shape_text(ch, c, 8.5, False, P900)
    if i < 6:
        arrow(s, x + 113, 422, 9, 14, P200)

note(s, "架构这页不讲细节，讲两件事：一是分层职责（Controller 只做鉴权和参数边界，业务规则全在 Service），"
        "二是业务独立成模块（manufacturing），换前端不影响业务。")
# ---------------------------------------------------------------- 9 验证
s = blank(prs)
header(s, "VERIFICATION", "怎么证明它对：三层验证", 9)

nums2 = [("60", "项 SQL 自检", "库存双向对账、单据链路、权限完整度"), ("32", "个单元测试", "6 个测试类，含权限护栏反射扫描"),
         ("8", "个账号验收", "按岗位逐个登录，检查菜单与 403 边界")]
for i, (n, t, d) in enumerate(nums2):
    x = 48 + i * 292
    rrect(s, x, 104, 280, 96, P050, radius=0.10)
    text(s, x + 18, 116, 70, 34, n, 26, True, P700, spacing=1.0)
    text(s, x + 92, 122, 170, 16, t, 10.5, True, P900, spacing=1.0)
    text(s, x + 92, 144, 170, 40, d, 8.5, False, MUTED, spacing=1.3)

text(s, 48, 216, 400, 16, "自检里最能说明问题的一项", 10, True, P500, spacing=1.0)
rrect(s, 48, 238, 420, 130, WHITE, P200, radius=0.08)
text(s, 66, 250, 384, 108,
     "库存双向对账：把 st_inventory 的结存与 st_stock_txn 的流水，按\n"
     "（物料类型 + 物料 + 批次 + 仓库）四元组分别求和比较。\n\n"
     "只查一个方向会漏掉「有流水却没有结存行」；双向比对才能发现\n"
     "幽灵出入库。这一项跑不过，说明业务动作有绕过流水的地方。",
     9, False, TEXT, spacing=1.4)

text(s, 496, 216, 200, 16, "三条已知不足（主动讲）", 10, True, P500, spacing=1.0)
gaps = [
    ("没有工作流引擎", "审批只有通过/驳回，没有多级会签与条件分支"),
    ("报工没有复核", "报工直接生成计件，缺少班长或质检确认环节"),
    ("没有 CI", "32 个用例与 60 项自检是本地手工跑，未接入流水线"),
]
for i, (t, d) in enumerate(gaps):
    y = 238 + i * 44
    rrect(s, 496, y, 416, 38, P050, radius=0.12)
    text(s, 510, y + 5, 150, 14, t, 9, True, P700, spacing=1.0)
    text(s, 510, y + 21, 390, 14, d, 8, False, MUTED, spacing=1.0)

text(s, 48, 384, 864, 40,
     "补充：前端 npm run build 含 vue-tsc 类型检查，类型不过不出包；后端 mvn test 通过；\n"
     "演示库的 4 个自检脚本可重复执行，每次改完数据都能跑一遍确认账还对得上。",
     9, False, TEXT, spacing=1.35)

rrect(s, 48, 436, 864, 50, P050, radius=0.12)
rect(s, 48, 436, 3, 50, P500)
text(s, 66, 448, 830, 26,
     "结论：三层验证构成「改完还能对账」的最小保障；目前缺的是自动化流水线和报工复核环节。",
     10, True, P900, spacing=1.2)

note(s, "验证这页的作用是把「我们真的测过」讲清楚。挑库存双向对账讲深一点，"
        "因为它能同时证明「流水驱动」的设计和「自检不是走过场」。不足要主动说，别等被问。")

# ---------------------------------------------------------------- 10 结束
s = blank(prs)
rect(s, 0, 0, W, 8, P700)
rect(s, 0, 8, W, 232, P050)
rect(s, 0, 240, W, 3, P700)

text(s, 60, 70, 700, 20, "THANK YOU", 12, True, P500, spacing=1.0)
text(s, 60, 96, 760, 48, "谢谢，请老师提问", 34, True, P900, spacing=1.0)
text(s, 60, 158, 760, 44, "现场可演示：报价转订单 · 分批发货 · 财务页两种角色对比 · 业务问答", 12, False, P700, spacing=1.3)

tips = [
    ("演示入口", "http://127.0.0.1:5173　账号 admin / sales / finance / boss　密码 Demo@2026!"),
    ("两条对比", "同一个财务页：finance 能登记收付款，boss 只能查看"),
    ("如果要看代码", "ErpOrderService（状态机）· ErpStockMapper（防超卖）· api/catalog.ts（配置驱动）"),
]
for i, (k, v) in enumerate(tips):
    y = 272 + i * 60
    text(s, 60, y, 90, 14, k, 9, True, P500, spacing=1.0)
    text(s, 60, y + 18, 560, 20, v, 10, False, TEXT, spacing=1.0)
    rect(s, 60, y + 44, 560, 0.8, LINE)

# 右侧材料索引
rrect(s, 648, 268, 264, 190, WHITE, P200, radius=0.08)
text(s, 666, 280, 230, 16, "答辩材料在哪", 9.5, True, P700, spacing=1.0)
files = ["docs/答辩手册/00-总讲稿", "docs/答辩手册/01-后端篇",
         "docs/答辩手册/02-前端篇", "docs/答辩手册/03-问答速查"]
for i, f in enumerate(files):
    y = 306 + i * 28
    rect(s, 666, y + 5, 4, 4, P500)
    text(s, 678, y, 220, 16, f, 8.5, False, MUTED, spacing=1.0)
text(s, 666, 420, 230, 28, "每份都带口播稿与代码位置", 8, False, MUTED, spacing=1.0)

text(s, 60, 490, 800, 16, "海岚 ERP · 服装制造企业资源计划 · 2026.10", 8.5, False, MUTED, spacing=1.0)

note(s, "收尾 20 秒：项目就到这，三条不足我们也清楚。老师想先看哪一块，我可以直接演示或翻代码。")

prs.save(OUT_FILE)
print("saved:", OUT_FILE, os.path.getsize(OUT_FILE), "bytes, slides:", len(prs.slides.__iter__.__self__._sldIdLst))
