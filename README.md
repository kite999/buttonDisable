# buttonDisable
## 機能
簡易的な排他ロック、ロック解除を行います。
具体的には下記。
- 機能に左右されず共通的に使用できます。
- ロックされていない編集画面に遷移した時、編集画面をロックします。
- 他人がロックされている編集画面に遷移した時、入力域をdisableにし、確定ボタンもdisableにします。
- 他人がロックされている編集画面に遷移した時、誰が編集中かを明示します。
- 自分がロックしている編集画面から別画面に遷移した時、historyback時、ブラウザを閉じた場合にロックを解除します。
- 自分がロックしている状態でブラウザのプロセスがクラッシュした場合はロック解除されません。
- 編集画面への遷移ボタンを対象がロックされているか、されていないかを明示的に表示します。
## 未実装
- セッションを実装していないのでユーザーIDは固定しています。変更ください。使用箇所：LockController,LockButtonTagProcessor
## テーブル
様々な機能で共通的に使用する為、ロック専用のテーブルを使用します。
テーブル名 lock
id オートナンバ			
target 画面id＋一意なパラメータ ユニークインデックス
user_id ロックをかけているユーザID			
lock_time ロックをかけた時間 インデックス
あとは任意の共通カラム		

```
CREATE TABLE lock
(id    bigserial    PRIMARY KEY NOT NULL,
target   TEXT       UNIQUE NOT NULL,
user_id   int       NOT NULL,
lock_time   timestamp       NOT NULL
);
```

## ソース解説
### サンプルソース
下記は例の為のリスト、編集画面を表示する用のただのサンプルです。

/src/main/java/com/example/buttonDisable/item
/src/main/java/com/example/buttonDisable/shain
/src/main/resources/templates/item
/src/main/resources/templates/shain
### ロック機能
ロックテーブルを読み込んだり、islockや書き込みWEBAPIを提供します。

/src/main/java/com/example/buttonDisable/lock
### Thymeleafタグ
遷移先のアイテムがロックかかっているかどうかを判断してボタンの内容を変化させるThymeleaf用のDialectタグを実装しています。
#### Dialect
Dialectでタグの定義をし、Processorでタグの生成処理などを行います。
/src/main/java/com/example/buttonDisable/thymeleaf/common/SampleDialect.java

Dialectを作成し、processors.addすることにより下記のようにタグを自作します。
タグを自作することによってDBに接続し、ロックの有無を確認しボタン生成といった制御しています。

例ではsample:lockButtonといったタグを作成しています。
```
<span sample:lockButton target="|${gamenId}_shainA|" query="/shain/henshu?shainId=shainA"></span>
```
#### Processor
doProcessをオーバーライドし、処理を実装します。
ここでは、lockテーブルを参照、ボタンの生成などを行っています。
### Javascript
#### 遷移ボタン用共通javascript
/src/main/resources/templates/common/lockButtonJs.html<br>
sample:lockButtonを使用した時にボタンで遷移できるように下記javascriptを呼び出します。

呼び出し方法は<br>
/src/main/resources/templates/shain/list.htmlを参考にしてください。
#### ロック、アンロック共通javascript
/src/main/resources/templates/common/lockAjaxJs.html<br>
編集画面では下記を呼び出します。
下記を呼び出すと表示時にロックがかかっているかどうかを参照し、ロックがかかっていると入力域、サブミットボタンのロック、ロックメッセージの表示を行います。

かかっていなければロックを行います。

更にアンロード時にロックを開放します。

呼び出し方法は<br>
/src/main/resources/templates/shain/henshu.htmlを参考にしてください。