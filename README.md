2020神坊資訊面試作業 
===

[TOC]

---

需求項目
---
框架：
* Spring boot 2
* Hibernate
* Spring data jpa
* Mockito or jmockit

專案類型：
* Maven

資料庫：
* H2

情境：
* 提供多隻restful API，格式為json，達到以下幾件事：
    1. 新增員工資料 : 資料內容請參考「員工資料表欄位」
    2. 更新員工資料 : 資料內容請參考「員工資料表欄位」
    3. 刪除員工資料 : 參數為員工編號
    4. 新增部門資料 : 資料內容請參考「部門資料表欄位」
    5. 更新部門資料 : 資料內容請參考「部門資料表欄位」
    6. 刪除部門資料 : 參數為部門編號
    7. 查詢員工資料：「動態條件」為姓名、員工編號、年齡、部門名稱(欄位皆為選填)，若填寫多個欄位條件，各條件為and
  回傳欄位為：所有欄位(包含員工及部門資料)
  並需要有分頁功能，每頁最多顯示10筆
  
單元測試：
* 請以Mockito撰寫單元測試各method，若有覺得不必寫的也請用註解說明

資料表：
* 員工資料表欄位：
* 姓名
* 員工編號
* 部門ID
* 性別
* 電話
* 地址
* 年齡
* 建立時間
* 最後一次修改時間

部門資料表欄位：
* 部門ID
* 部門名稱

---


資料庫設計
---
* 員工資料表[Employee]

|欄位代號        |欄位名稱        |欄位大小   |必填      |預設值
|--------------|---------------|----------|---------|----|
|eId           |員工編號        |          |●
|eName         |員工姓名        |50        |●
|departmentId  |部門ID         |4         |●
|gender        |性別           |1         |●
|phone         |電話           |20        |
|address       |地址           |200       |
|age           |年齡           |3         |
|createTime    |建立時間        |20        |●
|lastModifyTime|最後一次修改時間 |20        |●
|isLeaveType   |離職狀態        |1         |●         |N

* 部門資料表[Department]

|欄位代號   |欄位名稱 |欄位大小|必填      |預設值
|----------|-------|-------|---------|----|
|did       |部門ID  |2      |●
|dName     |部門名稱 |50     |●
|isDelType |刪除狀態 |1      |●        |N

---

API設計
---
* 員工API[EmployeeController]

|Http狀態|URI                |說明
|-------|-------------------|----------|
|POST   |/addEmployee       |新增員工資料
|DELETE |/delEmployee/{eid} |刪除員工資料
|POST   |/employees         |查詢員工資料
|PUT    |/updEmployee/{eid} |更新員工資料

* 部門API[DepartmentController]

|Http狀態|URI                  |說明
|-------|---------------------|----------|
|POST   |/addDepartment       |新增部門資料
|DELETE |/delDepartment/{did} |刪除部門資料
|GET    |/department          |查詢部門資料
|PUT    |/updDepartment/{did} |更新部門資料

---

ResponseCode定義
---

|code  |說明
|------|-------------------|
|00000 |執行成功
|00010 |操作失敗
|10020 |JSON格式錯誤
|10030 |部門ID重複
|10040 |無此部門ID
|10050 |部門撤銷
|10060 |無此員工編號
|10070 |員工已離職


---

URL
---

* URL : http://127.0.0.1:8080/DemoProject/
* swagger-ui : http://127.0.0.1:8080/DemoProject/swagger-ui.html#/

---

