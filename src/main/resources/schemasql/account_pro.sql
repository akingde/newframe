/*=========================================================================*/
/* 资金方账户统计存储过程                                             */
/*=========================================================================*/
DROP PROCEDURE IF EXISTS pro_account_funding;
CREATE PROCEDURE `pro_account_funding`(in in_uid bigint)
BEGIN
	SET @uid = in_uid;
	-- 保证金
	SET @deposit= 0;
	-- 待收金额
	SET @repay= 0;
	-- 本月应收
	SET @monthRepay= 0;

	-- 逾期金额合计
	SET @overdueAmount= 0;
		-- 逾期笔数
	SET @overdueCount= 0;
	-- 逾期订单投资总额
	SET @overdueOrderAmount= 0;
	-- 逾期率
	SET @overdueRate= 0;

	SET @existUid= NULL;

	SELECT sum(deposit) INTO @deposit FROM order_funder t WHERE t.funder_id=@uid;
	SELECT sum(order_amount) INTO @repay FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=1 AND t.withhold IN(1,3);
	SELECT sum(order_amount) INTO @monthRepay FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=1 AND t.withhold IN(1,3) AND t.pay_time < unix_timestamp(date_add(curdate() - day(curdate()) + 1, interval 1 month));

	SELECT sum(order_amount) INTO @overdueAmount FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=1 AND t.order_status=3;
	SELECT count(*) INTO @overdueCount FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=1 AND t.order_status=3;
	SELECT sum(t.invest_amount) INTO @overdueOrderAmount FROM account_funding_overdue_asset t WHERE t.uid=@uid;

	SET @deposit = IFNULL(@deposit,0);
	SET @repay = IFNULL(@repay,0);
	SET @monthRepay = IFNULL(@monthRepay,0);
	SET @overdueAmount = IFNULL(@overdueAmount,0);
	SET @overdueCount = IFNULL(@overdueCount,0);
	SET @overdueOrderAmount = IFNULL(@overdueOrderAmount,0);

	IF(@overdueOrderAmount>0) THEN
     SET @overdueRate = @overdueAmount/@overdueOrderAmount;
  END IF;
  -- SELECT @deposit;
	-- SELECT @repay;
	-- SELECT @monthRepay;
	-- SELECT @overdueAmount;
	-- SELECT @overdueCount;
	-- SELECT @overdueOrderAmount;
	-- SELECT @overdueRate;
	SELECT t.uid INTO @existUid FROM account_funding t WHERE t.uid=@uid;
	IF(@existUid IS NULL) THEN
		INSERT INTO account_funding
    (uid,useable_amount, total_assets, frozen_assets, cash_deposit, due_amount, account_status, ctime, utime, month_payable_amount, overdue_amount, overdue_count, overdue_rate) VALUES
		(@uid,(select IFNULL((select useable_amount from account where uid=@uid),0)),
		 (select IFNULL((select total_assets from account where uid=@uid),0)),
		 (select IFNULL((select frozen_assets from account where uid=@uid),0)),
		@deposit, @repay, 1, unix_timestamp(), null, @monthRepay, @overdueAmount, @overdueCount, @overdueRate);
	ELSE
		UPDATE account_funding
		SET
		utime = unix_timestamp(),
		useable_amount = (select IFNULL((select useable_amount from account where uid=@uid),0)),
		total_assets = (select IFNULL((select total_assets from account where uid=@uid),0)),
		frozen_assets = (select IFNULL((select frozen_assets from account where uid=@uid),0)),
		cash_deposit = @deposit, due_amount = @repay, month_payable_amount = @monthRepay, overdue_amount = @overdueAmount, overdue_count = @overdueCount, overdue_rate = @overdueRate
		WHERE uid = @uid;
  END IF;
 END;
/*=========================================================================*/





/*=========================================================================*/
/* 出租方账户统计存储过程                                             */
/*=========================================================================*/
DROP PROCEDURE IF EXISTS pro_account_lessor;
CREATE PROCEDURE `pro_account_lessor`(in in_uid bigint)
  BEGIN
    SET @uid = in_uid;
    -- 待收金额
    SET @repay= 0;
    -- 本月应收
    SET @monthRepay= 0;

    -- 逾期金额合计
    SET @overdueAmount= 0;
    -- 逾期笔数
    SET @overdueCount= 0;
    -- 逾期订单投资总额
    SET @overdueOrderAmount= 0;
    -- 逾期率
    SET @overdueRate= 0;

    SET @matterRentAmount= 0;
    SET @matterRentPayed= 0;
    SET @matterRentUnpayed= 0;

    SET @existUid= NULL;

    SELECT sum(order_amount) INTO @repay FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.withhold IN(1,3);
    SELECT sum(order_amount) INTO @monthRepay FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.withhold IN(1,3) AND t.pay_time < unix_timestamp(date_add(curdate() - day(curdate()) + 1, interval 1 month));

    SELECT sum(order_amount) INTO @overdueAmount FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.order_status=3;
    SELECT count(*) INTO @overdueCount FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.order_status=3;

    SELECT sum(order_amount) INTO @matterRentPayed FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.withhold IN(2,4);
    SELECT sum(order_amount) INTO @matterRentUnpayed FROM account_renter_repay t WHERE t.uid=@uid AND t.order_type=2 AND t.withhold NOT IN(2,4);

    SET @repay = IFNULL(@repay,0);
    SET @monthRepay = IFNULL(@monthRepay,0);
    SET @overdueAmount = IFNULL(@overdueAmount,0);
    SET @overdueCount = IFNULL(@overdueCount,0);
    SET @overdueOrderAmount = IFNULL(@overdueOrderAmount,0);
    SET @matterRentPayed = IFNULL(@matterRentPayed,0);
    SET @matterRentUnpayed = IFNULL(@matterRentUnpayed,0);
    SET @matterRentAmount = @matterRentPayed+@matterRentUnpayed;
    IF(@overdueOrderAmount>0) THEN
      SET @overdueRate = @overdueAmount/@overdueOrderAmount;
    END IF;
    -- SELECT @repay;
    -- SELECT @monthRepay;
    -- SELECT @overdueAmount;
    -- SELECT @overdueCount;
    -- SELECT @overdueOrderAmount;
    -- SELECT @overdueRate;
    SELECT t.uid INTO @existUid FROM account_lessor t WHERE t.uid=@uid;
    IF(@existUid IS NULL) THEN
      INSERT INTO account_lessor
          (uid,useable_amount, total_assets, frozen_assets, pay_amount, account_status, ctime, utime,
           month_payable_amount, overdue_amount, overdue_count, overdue_rate,
           matter_rent_amount,matter_rent_payed,matter_rent_unpayed)
           VALUES (@uid,(select IFNULL((select useable_amount from account where uid=@uid),0)),
           (select IFNULL((select total_assets from account where uid=@uid),0)),
           (select IFNULL((select frozen_assets from account where uid=@uid),0)),
           @repay, 1, unix_timestamp(), null, @monthRepay, @overdueAmount, @overdueCount, @overdueRate,
           @matterRentAmount,@matterRentPayed,@matterRentUnpayed);
    ELSE
      UPDATE account_lessor
      SET
          utime = unix_timestamp(),
          useable_amount = (select IFNULL((select useable_amount from account where uid=@uid),0)),
          total_assets = (select IFNULL((select total_assets from account where uid=@uid),0)),
          frozen_assets = (select IFNULL((select frozen_assets from account where uid=@uid),0)),
          pay_amount = @repay, month_payable_amount = @monthRepay, overdue_amount = @overdueAmount, overdue_count = @overdueCount, overdue_rate = @overdueRate,
          matter_rent_amount = @matterRentAmount,matter_rent_payed =@matterRentPayed,matter_rent_unpayed =@matterRentUnpayed
      WHERE uid = @uid;
    END IF;
  END;
/*=========================================================================*/






/*=========================================================================*/
/* 供应商账户统计存储过程                                             */
/*=========================================================================*/
DROP PROCEDURE IF EXISTS pro_account_supplier;
CREATE PROCEDURE `pro_account_supplier`(in in_uid bigint)
  BEGIN
    SET @uid = in_uid;
    -- 累计营收
    SET @sellAmount= 0;
    -- 累计销售数量
    SET @sellCount= 0;
    -- 待发货数量
    SET @deliverCount= 0;

    SET @existUid= NULL;

    SELECT sum(t.total_account) INTO @sellAmount FROM order_supplier t WHERE t.supplier_id=@uid AND t.order_status>=3;
    SELECT count(*) INTO @sellCount FROM order_supplier t WHERE t.supplier_id=@uid AND t.order_status>=3;
    SELECT count(*) INTO @deliverCount FROM order_supplier t WHERE t.supplier_id=@uid AND t.order_status=3;

    SET @sellAmount = IFNULL(@sellAmount,0);
    SET @sellCount = IFNULL(@sellCount,0);
    SET @deliverCount = IFNULL(@deliverCount,0);

    SELECT t.uid INTO @existUid FROM account_supplier t WHERE t.uid=@uid;
    IF(@existUid IS NULL) THEN
      INSERT INTO account_supplier (uid, useable_amount, total_asset, frozen_asset, total_earning, sale_number, deliver_number, ctime)
      VALUES (@uid,
      (select IFNULL((select useable_amount from account where uid=@uid),0)),
      (select IFNULL((select total_assets from account where uid=@uid),0)),
      (select IFNULL((select frozen_assets from account where uid=@uid),0)),
      @sellAmount,@sellCount,@deliverCount,
      unix_timestamp());
    ELSE
      UPDATE account_supplier
      SET
        utime = unix_timestamp(),
        useable_amount = (select IFNULL((select useable_amount from account where uid=@uid),0)),
        total_asset = (select IFNULL((select total_assets from account where uid=@uid),0)),
        frozen_asset = (select IFNULL((select frozen_assets from account where uid=@uid),0)),
        total_earning = @sellAmount,sale_number = @sellCount, deliver_number = @deliverCount
      WHERE uid = @uid;
    END IF;
  END;
/*=========================================================================*/