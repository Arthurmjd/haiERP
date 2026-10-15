-- 删除所有业务数据

delete from ord_contract;
delete from ord_contract_item;
delete from ord_so;
delete from ord_so_item;
delete from ord_so_quickly;
delete from ord_complain;

delete from del_osplan;
delete from del_osplan_receiving;
delete from del_mps;
delete from del_plan_item;
delete from del_gd;
delete from del_gd_item;
delete from pln_mrp_mr;
delete from pln_mrp_mp;
delete from pln_mrp_wo;

delete from pln_wo;
delete from pln_wo_material;
delete from pln_wo_sop;
delete from pln_wo_video;
delete from pln_wo_drawing;
delete from pln_wo_routing;
delete from pln_wo_routingkiparts;
delete from pln_wo_routingtool;
delete from pln_wo_routingsop;
delete from pln_wo_routingdrawing;
delete from pln_wo_routingvideo;
delete from pln_ml;
delete from pln_ml_material;
delete from pln_ml_sop;
delete from pln_ml_video;
delete from pln_ml_drawing;
delete from exe_process_moves;
delete from pln_ml_routingkiparts;
delete from pln_ml_routingtool;
delete from pln_ml_routingsop;
delete from pln_ml_routingdrawing;
delete from pln_ml_routingvideo;

delete from exe_process_moves;
delete from exe_process_work;

delete from sup_pr;
delete from sup_pritem;
delete from sup_purchasing_plan;
delete from sup_po;
delete from sup_po_item;
delete from sup_po_item_return;

delete from snc_stock_request;
delete from snc_stock_request_item;
delete from snc_gr;
delete from snc_gr_item;
delete from snc_inventory_io;
delete from snc_inventory_io;

delete from qua_exception;
delete from qua_inspect_request;
delete from qua_inspect_request_item;

delete from eng_er;
delete from eng_exception;



-- 删除所有工艺数据

delete from eng_material;
delete from eng_material_inventory;
delete from eng_material_parameters;
delete from eng_material_production;
delete from eng_material_purchasing;
delete from eng_material_replacable;
delete from eng_material_sales;

delete from eng_product_bom;
delete from eng_pruduct_sop;
delete from eng_pruduct_video;
delete from eng_product_drawing;
delete from eng_product_ecn;
delete from eng_product_routing;
delete from eng_product_routingdrawing;
delete from eng_product_routingecn;
delete from eng_product_routingkiparts;
delete from eng_product_routingtool;
delete from eng_pruduct_routingsop;
delete from eng_pruduct_routingvideo;



-- 删除基础数据

delete from cha_customer;
delete from cha_customer_address;
delete from cha_customer_bank;
delete from cha_customer_contact;

delete from sup_supplier;
delete from sup_supplier_address;
delete from sup_supplier_bank;
delete from sup_supplier_contact;
delete from sup_supplier_quota;

delete from res_ware_house;
delete from res_ware_position;

delete from res_line;
delete from res_machine;
delete from res_machine_type;
delete from res_tool;

delete from res_labor;
delete from res_workgroup;
delete from res_workgroup_machine;
delete from res_workgroup_process;
delete from res_workgroup_tool;

delete from res_shift;
delete from res_shiftsys;


