// 게시물 목록 출력 오류 사례1:
// => DBMS 결과를 가져오기 전에 클라이언트로 출력할 경우
var http = require('http');
var mysql = require('mysql');
var url = require('url');
var express = require('express');
var app = express();
//1) DB 커넥션 준비 
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'java80',
  password : '1111',
  database : 'java80db'
});

connection.connect();

// 2) HTTP 서버 준비
app.get('/check.do', function (request, response) {
	response.setHeader("Access-Control-Allow-Origin", "*");
	var urlInfo = url.parse(request.url, true);
	
	response.writeHead(200, {
		'Content-Type' : 'text/html;charset=UTF-8' 
	});
	
	var urlInfo = url.parse(request.url, true);
	var email = urlInfo.query.email;
	var check;
		// 게시물 목록 가져오기
	connection.query(
	  'select * from MEMBERS where EMAIL=? ',[email],
	  function(err, rows, fields) { // 서버에서 결과를 받았을 때 호출되는 함수
		  if (err) throw err;
		  check = rows[0];
		  console.log(check);
		  if( check == null ) {
			  console.log('1111');
			  response.end('1');
		  } else {
			  console.log('no');
			  response.end('2');
		  }
	});
		
});

// 3) HTTP 서버 가동
app.listen(8989);
console.log("서버 실행 중...");

