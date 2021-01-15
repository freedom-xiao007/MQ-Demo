# 消息队列 Demo
***
## 简介
&ensp;&ensp;&ensp;&ensp;实现消息队列 Demo 程序

## 程序运行说明
&ensp;&ensp;&ensp;&ensp;直接运行Example工程下的ExampleApplication即可

## 设计思路

## 程序说明
- core ：消息队列功能API实现
- example ： 使用消息队列API，实现生产和消费

### Core工程
实现内存消息队列
实现Producer API
实现Consumer API

### Example工程
简单的测试用例，看Core是否能进行使用

## 迭代说明
### 第一版：内存 Queue
- [x] 1、基于内存Queue实现生产和消费API（已经完成）
  - [x] 1）创建内存Queue，作为底层消息存储
  - [x] 2）定义Topic，支持多个Topic
  - [x] 3）定义Producer，支持Send消息
  - [x] 4）定义Consumer，支持Poll消
  
&ensp;&ensp;&ensp;&ensp;目前使用HTTP作为通信方式