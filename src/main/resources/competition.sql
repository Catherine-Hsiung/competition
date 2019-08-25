create table if not exists competor(
 id int not null auto_increment,
 `name` varchar(50) not null,
 sex int not null comment "1，男性，2，女性",
 age int,
 role int not null comment "参赛人员角色,1，超级管员，2，特邀嘉宾 3，普通观众",
 phone varchar(20) not null,
 email varchar(50),
primary key(id)
);

create table if not exists turn(
 id int not null auto_increment comment "主键",
 label varchar(20) not null  comment "轮次名称",
 `index` int not null  comment "第几轮",
 question_id int   comment "题目id",
 current_question  int   comment "该轮当前进行到第几题",
 turn_flag boolean default false comment "标记当前轮次是否完结",
  primary key( id)
);
create table if not exists qbank (
     id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
     title VARCHAR(100) unique NOT NULL COMMENT '题目',
     answer_a VARCHAR(100) NOT NULL COMMENT '答案1',
     answer_b VARCHAR(100) NOT NULL COMMENT '答案2',
     answer_c VARCHAR(100) NOT NULL COMMENT '答案3',
     answer_d VARCHAR(100) NOT NULL COMMENT '答案4',
     right_answer VARCHAR(100) NOT NULL COMMENT '正确答案',
     question_effictive boolean default true COMMENT '统计结果时，题是否有效',
     turn_id INT COMMENT '轮次id',
    PRIMARY KEY ( id)
);
create table if not exists result(
 id int not null auto_increment comment "主键",
 result VARCHAR(100) not null comment "答题结果",
 starttmime bigint not null comment "题目开始时间",
 taketime bigint not null comment "答题花费时间",
 competor_id int not null comment "参赛人员信息表id",
 qbank_id int not null comment "题库id",
 turn_index int not null comment "竞赛轮次index",
 score int default 0 comment "竞赛分数",
primary key(id)
);

