1010admin
=========

# overview

1010の簡易管理ツール

* spark java を使った、Webアプリケーション
* ビルドツールにはgradleを使用している

# 動かし方

下記setupに基づいて、gradle環境を整えたのち、gradleコマンドにてコンパイルと実行が可能

* gradle fatJar

spark-java における、いわゆるfatJar形式のjarファイルを作成する。

* gradle run

fatJarを指定して、gradeプロセスにて簡易実行。

    localhost:4567/admin
    
にてjetty web server が起動する。

# setup

ubuntu 16.04での環境設定

## java setup

    $ sudo add-apt-repository ppa:webupd8team/java
    $ sudo apt-get update
    $ sudo apt-get install oracle-java8-installer

## gradle setup

    $ curl -s api.sdkman.io | bash
    $ sdk install gradle

## git install

    $ sudo apt install git

## screen install

    $ sudo apt install git

## emacs setting

gradle mode and groovy mode

.emacs.d 配下にinit.elを生成し、下記を追加

    (require 'package)
    (add-to-list 'package-archives '("melpa" . "http://melpa.milkbox.net/packages/") t)
    (add-to-list 'package-archives '("marmalade" . "http://marmalade-repo.org/packages/"))
    (package-initialize)

emacs で以下のコマンド

    M-x list-package gradle-mode install
    M-x list-package goovy-mode install

ただし、groovy-mode は、emacs24ではうまく動かないので、直接編集

.emacs.d/elpa/groovy-mode....../groovy-mode.pl の72行目あたりで
`(require 'cl)`
を一行追加

あと、下記をinit.elに追加しておく

    (require 'gradle-mode)
    (gradle-mode 1)

----

# gradle commands

* gradle fatJar to create jar file

* gradle run to exec java -jar ... and run service.
