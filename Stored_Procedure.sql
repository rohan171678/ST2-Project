DROP PROCEDURE REFRESH;
DELIMITER $$
CREATE PROCEDURE REFRESH()
BEGIN
DECLARE diff int;
DECLARE done int;
DECLARE v_loan_id int;
DECLARE v_due_date date;
DECLARE v_date_out DATE;
DECLARE v_date_in DATE;
DECLARE c1 CURSOR FOR SELECT loan_id,due_date,date_out,date_in FROM BOOK_LOANS;
declare continue handler for not found set done=1;
OPEN c1;
set done = 0;
refreshLoop: LOOP
FETCH c1 into v_loan_id,v_due_date,v_date_out,v_date_in;
 IF done = 1 then leave refreshLoop; end if;

SET diff = (v_date_in - v_due_date);
IF NOT EXISTS(SELECT * FROM FINE WHERE LOAN_ID=v_LOAN_ID) THEN
IF diff > 0 THEN
	INSERT INTO FINE VALUES(v_LOAN_ID,0,FALSE);
	UPDATE FINE SET FINE_AMT=(0.25 * diff) WHERE LOAN_ID=v_loan_id;
	COMMIT;
END IF;
END IF;

IF EXISTS(SELECT * FROM FINE WHERE LOAN_ID=v_LOAN_ID) THEN
IF diff > 0 THEN
	UPDATE FINE SET FINE_AMT=(0.25 * diff) WHERE LOAN_ID=v_loan_id;
	COMMIT;
END IF;
END IF;

END LOOP refreshLoop;
close c1;
END $$
DELIMITER ;